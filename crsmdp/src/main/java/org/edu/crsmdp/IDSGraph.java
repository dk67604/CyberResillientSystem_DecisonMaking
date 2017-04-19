package org.edu.crsmdp;

import java.util.Map;
import java.util.Set;

import org.edu.crsmdp.ids.IDSAction;
import org.edu.crsmdp.ids.IDSState;
import org.edu.crsmdp.ids.Parameters;
import org.edu.crsmdp.ids.Utility;

import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain.NodeTransitionProbability;

public class IDSGraph extends GraphDefinedDomain {
	private double lambda;
	public IDSGraph(int numNode,double lambda){
		super(numNode);
		this.lambda=lambda;
	}

	private Map<Integer, Map<Integer, Set<NodeTransitionProbability>>> transitionDynamics;
	
	
	public Map<Integer, Map<Integer, Set<NodeTransitionProbability>>> getTransitionDynamics() {
		return transitionDynamics;
	}

	public void setTransitionDynamics(Map<Integer, Map<Integer, Set<NodeTransitionProbability>>> transitionDynamics) {
		this.transitionDynamics = transitionDynamics;
	}

	public  IDSGraph generateDomainHelper(IDSGraph gdd) {

		
		Map<Integer, IDSState> stateEncode = Utility.generateIDSStateFactory();
//		System.out.println("Size:"+stateEncode.size());
		double prob1, prob2 = 0;
		double prob3=0.0;
		
		int i=0;
		for (Map.Entry<Integer, IDSState> entryCur : stateEncode.entrySet()) {
			for (IDSAction action : IDSAction.values()) {
				for (Map.Entry<Integer, IDSState> entryNext : stateEncode.entrySet()) {
					IDSState current = entryCur.getValue();
					IDSState next = entryNext.getValue();
					String currInfection = current.getinfection();
					String currIDSState = current.getIdsState();
					String nextOutput = next.getOutput();
					String nextIDSState = next.getIdsState();
					
					String nextInfection=next.getinfection();
					//Bernoulli Distribution with parameter lambda for infection 'benign' or 'malicious'
					if(nextInfection.equalsIgnoreCase("benign")){
						prob3=1-this.lambda;
					}
					if(nextInfection.equalsIgnoreCase("malicious")){
						prob3=this.lambda;
					}
			
					// Action Inspect
					if (action.name().equalsIgnoreCase("inspect")) {

						if (currIDSState.equalsIgnoreCase("idle") && nextIDSState.equalsIgnoreCase("idle")) {
							prob2 = Parameters.phi;
							if (currInfection.equalsIgnoreCase("benign") && nextOutput.equalsIgnoreCase("benign")) {
								prob1 = 1 - Parameters.betaFp;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("inspect"),
										entryNext.getKey(), prob1 * prob2*prob3);
								

							}
							if (currInfection.equalsIgnoreCase("benign") && nextOutput.equalsIgnoreCase("null")) {
								prob1 = Parameters.betaFp;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("inspect"),
										entryNext.getKey(), prob1 * prob2*prob3);
								

							}
							if (currInfection.equalsIgnoreCase("malicious") && nextOutput.equalsIgnoreCase("null")) {
								prob1 = 1 - Parameters.betaFn;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("inspect"),
										entryNext.getKey(), prob1 * prob2*prob3);
								

							}

							if (currInfection.equalsIgnoreCase("malicious")
									&& nextOutput.equalsIgnoreCase("malicious")) {
								prob1 = Parameters.betaFn;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("inspect"),
										entryNext.getKey(), prob1 * prob2*prob3);
								
							}
						}
						if (currIDSState.equalsIgnoreCase("idle") && nextIDSState.equalsIgnoreCase("busy")) {
							prob2 = 1 - Parameters.phi;
							if (currInfection.equalsIgnoreCase("benign") && nextOutput.equalsIgnoreCase("benign")) {
								prob1 = 1 - Parameters.betaFp;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("inspect"),
										entryNext.getKey(), prob1 * prob2*prob3);
								

							}
							if (currInfection.equalsIgnoreCase("benign") && nextOutput.equalsIgnoreCase("null")) {
								prob1 = Parameters.betaFp;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("inspect"),
										entryNext.getKey(), prob1 * prob2*prob3);
								

							}
							if (currInfection.equalsIgnoreCase("malicious") && nextOutput.equalsIgnoreCase("null")) {
								prob1 = 1 - Parameters.betaFn;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("inspect"),
										entryNext.getKey(), prob1 * prob2*prob3);
								

							}

							if (currInfection.equalsIgnoreCase("malicious")
									&& nextOutput.equalsIgnoreCase("malicious")) {
								prob1 = Parameters.betaFn;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("inspect"),
										entryNext.getKey(), prob1 * prob2*prob3);
								
							}

						}
						if (currIDSState.equalsIgnoreCase("busy") && nextIDSState.equalsIgnoreCase("idle")) {
							prob2 = Parameters.phi;
							if ((currInfection.equalsIgnoreCase("benign")
									|| currInfection.equalsIgnoreCase("malicious"))
									&& nextOutput.equalsIgnoreCase("malicious")) {
								prob1 = 1;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("inspect"),
										entryNext.getKey(), prob1 * prob2*prob3);
								
							}

						}
						if (currIDSState.equalsIgnoreCase("busy") && nextIDSState.equalsIgnoreCase("busy")) {
							prob2 = 1 - Parameters.phi;
							if ((currInfection.equalsIgnoreCase("benign")
									|| currInfection.equalsIgnoreCase("malicious"))
									&& nextOutput.equalsIgnoreCase("malicious")) {
								prob1 = 1;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("inspect"),
										entryNext.getKey(), prob1 * prob2*prob3);
								
							}

						}

					}

					// Action Pass
					if (action.name().equalsIgnoreCase("pass")) {
						if (currIDSState.equalsIgnoreCase("idle") && nextIDSState.equalsIgnoreCase("idle")) {
							prob2 = 1;
							if (currInfection.equalsIgnoreCase("benign") && nextOutput.equalsIgnoreCase("benign")) {
								prob1 = 1;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("pass"),
										entryNext.getKey(), prob1 * prob2*prob3);
								
							}
							if (currInfection.equalsIgnoreCase("malicious")
									&& nextOutput.equalsIgnoreCase("malicious")) {
								prob1 = 1;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("pass"),
										entryNext.getKey(), prob1 * prob2*prob3);
								
							}

						}
						if (currIDSState.equalsIgnoreCase("busy") && nextIDSState.equalsIgnoreCase("busy")) {
							prob2 = 1 - Parameters.phi;
							if (currInfection.equalsIgnoreCase("benign") && nextOutput.equalsIgnoreCase("benign")) {
								prob1 = 1;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("pass"),
										entryNext.getKey(), prob1 * prob2*prob3);
								
							}
							if (currInfection.equalsIgnoreCase("malicious")
									&& nextOutput.equalsIgnoreCase("malicious")) {
								prob1 = 1;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("pass"),
										entryNext.getKey(), prob1 * prob2*prob3);
								
							}
						}
						if (currIDSState.equalsIgnoreCase("busy") && nextIDSState.equalsIgnoreCase("idle")) {
							prob2 = Parameters.phi;
							if (currInfection.equalsIgnoreCase("benign") && nextOutput.equalsIgnoreCase("benign")) {
								prob1 = 1;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("pass"),
										entryNext.getKey(), prob1 * prob2*prob3);
								
							}
							if (currInfection.equalsIgnoreCase("malicious")
									&& nextOutput.equalsIgnoreCase("malicious")) {
								prob1 = 1;
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("pass"),
										entryNext.getKey(), prob1 * prob2*prob3);
								
							}

						}

					}

				}
			}
		}
		// gdd.setTransition(encodedState.get(0),
		// IDSAction.getIntValueFromString("inspect"),encodedState.get(1), 0.5);
		
		return gdd;

	}

	public Map<Integer, Map<Integer, Set<NodeTransitionProbability>>> helper(IDSGraph ids){
		ids=ids.generateDomainHelper(ids);
		this.transitionDynamics=super.transitionDynamics;
		return this.transitionDynamics;
	}
	
	public static void main(String[] args) {
		IDSGraph ids=new IDSGraph(12,0.5);
        
		ids=ids.generateDomainHelper(ids);
		for(Map.Entry<Integer, Map<Integer, Set<NodeTransitionProbability>>> e : ids.transitionDynamics.entrySet()){
			Map<Integer, Set<NodeTransitionProbability>> at = e.getValue();
			for(Map.Entry<Integer, Set<NodeTransitionProbability>> e2 : at.entrySet()){
				if(!e2.getValue().isEmpty()) {
					
					for(NodeTransitionProbability ntp : e2.getValue()) {
						System.out.println("State:"+e.getKey()+" Action:"+e2.getKey()+" Next State:"+ntp.transitionTo
								+" Transition Probability:"+ntp.probability);
					}
					
				}

			}
		}

		
	/*	GraphDefinedDomain gdd = new GraphDefinedDomain(3);
		gdd.setTransition(0, 0, 1, 1.);
		gdd.setTransition(0, 1, 2, 1.);

		gdd.setTransition(1, 0, 1, 1.);
		gdd.setTransition(1, 1, 0, 1.);

		gdd.setTransition(2, 0, 2, 1.);
		gdd.setTransition(2, 1, 0, 1.);

		SADomain domain = gdd.generateDomain();



		State s = new GraphStateNode(0);
		EnvironmentShell shell = new EnvironmentShell(domain, s);
		shell.start();*/

	}
}
