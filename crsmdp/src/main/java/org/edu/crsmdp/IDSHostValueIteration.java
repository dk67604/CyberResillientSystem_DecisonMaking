package org.edu.crsmdp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.edu.crsmdp.ids.Utility;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import burlap.behavior.policy.Policy;
import burlap.behavior.policy.PolicyUtils;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.domain.singleagent.graphdefined.GraphStateNode;
import burlap.domain.singleagent.graphdefined.GraphTF;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;

public class IDSHostValueIteration {
	IDSHostGraph idsHostGdd;
	SADomain domain;
	GraphTF tf;
	IDSHostRewardFunction rf;
	State initialState;
	HashableStateFactory hashableStateFactory;
	SimulatedEnvironment env;
	public IDSHostValueIteration(){
		
	}

	public IDSHostValueIteration(IDSGraph ids) {
		HostGraph hgdd = new HostGraph(12);
		IDSHostGraph idsHostGdd = new IDSHostGraph(48);
		idsHostGdd = idsHostGdd.productTransitions(ids, hgdd, idsHostGdd);

		List<Integer> terminalStates = getteminalStateEncode();
		int[] terminals = new int[terminalStates.size()];
		for (int i = 0; i < terminalStates.size(); i++) {
			terminals[i] = terminalStates.get(i);

		}

		this.tf = new GraphTF(terminals);
		this.rf = new IDSHostRewardFunction();
		this.initialState = new GraphStateNode(0);
		idsHostGdd.setTf(tf);
		idsHostGdd.setRf(rf);
		//
		this.domain = idsHostGdd.generateDomain();
		// System.out.println(this.domain.getModel().terminal(new
		// GraphStateNode(8)));
		this.hashableStateFactory = new SimpleHashableStateFactory();
		env = new SimulatedEnvironment(domain, initialState);

	}

	public double bestFirstAction() {
		double delta = 0.00001;
		double discountFactor = 0.95;
		int maxIters = 1000;
		ValueIteration planner = new ValueIteration(domain, discountFactor, hashableStateFactory, delta, maxIters);

		// planner.planFromState(initialState);
		// Policy p = new GreedyQPolicy(planner);
		Policy p = planner.planFromState(initialState);
		double svi = 0.0;
		int cnt = 0;
		Map<Integer, IDSHostState> idshostStateEncode = Utility.generateIDSHostStateFactory();
		for (Entry<Integer, IDSHostState> e : idshostStateEncode.entrySet()) {
			IDSHostState h = e.getValue();
			if (h.getOperation().equalsIgnoreCase("full") && h.getHost().equalsIgnoreCase("infected")
					&& h.getOutput().equalsIgnoreCase("malicious")) {
				String actionName = p.action(new GraphStateNode(e.getKey())).actionName();
				svi += planner.value(new GraphStateNode(e.getKey()));
				System.out.println("State name:" + e.getKey() + " " + ":" + h.getInfection() + "," + h.getIdsState()
						+ "," + h.getOutput() + "," + h.getOperation() + "," + h.getHost() + " "
						+ "Optimal Action Name:"
						+ IDSHostAction.getEnumNameFromValue(Integer.valueOf(actionName.replace("action", "")))
						+ " Value Function:" + planner.value(new GraphStateNode(e.getKey())));
				++cnt;

			}
		}
		System.out.println("Average Value:" + svi / cnt);
		return svi / cnt;

		

	}
	public  void savePolicyToFile() {
		double delta = 0.00001;
		double discountFactor = 0.95;
		int maxIters = 1000;
		ValueIteration planner = new ValueIteration(domain, discountFactor, hashableStateFactory, delta, maxIters);

		// planner.planFromState(initialState);
		// Policy p = new GreedyQPolicy(planner);
		Policy p = planner.planFromState(initialState);
		Map<Integer, IDSHostState> idshostStateEncode = Utility.generateIDSHostStateFactory();
		for(int i=0;i<48;i++){
			
			String actionName = p.action(new GraphStateNode(i)).actionName();
			IDSHostState h = idshostStateEncode.get(i);
			System.out.println("State("+i+")" + "" + ":" + h.getInfection() + "|" + h.getIdsState()
			+ "|" + h.getOutput() + "|" + h.getOperation() + "|" + h.getHost() + ", "
			+ "Optimal Action Name:"
			+ IDSHostAction.getEnumNameFromValue(Integer.valueOf(actionName.replace("action", ""))));
			
		}
		Episode ea=PolicyUtils.rollout(p, new GraphStateNode(24), domain.getModel());
		ea.write("vi");
		String actions=ea.actionString();
		String state=ea.stateSequence.toString();
		System.out.println(state);
		
		String[] actionSeq=actions.split(";");
		for(String s:actionSeq){
			System.out.println(IDSHostAction.getEnumNameFromValue(Integer.valueOf(s.trim().replace("action", "").trim())));
		}
		

		

	}

	public static XYSeriesCollection performValueIteration(){
		final XYSeries series = new XYSeries("Optimal State Value Function");
        final XYSeriesCollection dataset = new XYSeriesCollection();

		for(int i=0;i<=20;i++){
		System.out.println("Iteration "+(i+1)+" starts..............");
		double lambda=0.05*i;
		IDSGraph ids=new IDSGraph(12,lambda);
		IDSHostValueIteration vi=new IDSHostValueIteration(ids);
		double svi=vi.bestFirstAction();
		series.add(lambda, svi);
		System.out.println("Iteration "+(i+1)+" ends..............");
		
		}
		dataset.addSeries(series);	
		return dataset;
	}
	
	
	public static void main(String[] args) {
		//performValueIteration();
		IDSGraph ids=new IDSGraph(12,0.8);
		IDSHostValueIteration vi=new IDSHostValueIteration(ids);
		vi.savePolicyToFile();
		
	}

	public static List<Integer> getteminalStateEncode() {
		Map<Integer, IDSHostState> stateEncode = Utility.generateIDSHostStateFactory();
		List<Integer> terminalStates = new ArrayList<Integer>();
		for (Map.Entry<Integer, IDSHostState> e : stateEncode.entrySet()) {
			IDSHostState h = e.getValue();
			if (h.getOutput().equalsIgnoreCase("malicious") && h.getOperation().equalsIgnoreCase("reset")
					&& (h.getHost().equalsIgnoreCase("clean")||h.getHost().equalsIgnoreCase("infected"))) {
				terminalStates.add(e.getKey());
			}
		}
		return terminalStates;
	}
}
