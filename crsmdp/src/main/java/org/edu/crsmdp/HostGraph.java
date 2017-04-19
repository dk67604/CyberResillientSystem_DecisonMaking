package org.edu.crsmdp;

import java.util.Map;
import java.util.Set;

import org.edu.crsmdp.host.HostAction;
import org.edu.crsmdp.host.HostState;
import org.edu.crsmdp.ids.Parameters;
import org.edu.crsmdp.ids.Utility;

import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain.NodeTransitionProbability;

public class HostGraph extends GraphDefinedDomain {

	public HostGraph(int numNodes){
		super(numNodes);
	}
	
	private Map<Integer, Map<Integer, Set<NodeTransitionProbability>>> transitionDynamics;
    
	
	public Map<Integer, Map<Integer, Set<NodeTransitionProbability>>> getTransitionDynamics() {
		return transitionDynamics;
	}

	public void setTransitionDynamics(Map<Integer, Map<Integer, Set<NodeTransitionProbability>>> transitionDynamics) {
		this.transitionDynamics = transitionDynamics;
	}

	public HostGraph generateDomainHelper(HostGraph gdd){
		Map<Integer, HostState> stateEncode=Utility.generateHostStateFactory();
//		System.out.println("Size:"+stateEncode.size());
		int i=0;
		for (Map.Entry<Integer, HostState> entryCur : stateEncode.entrySet()) {
			for (HostAction action : HostAction.values()) {
				for (Map.Entry<Integer, HostState> entryNext : stateEncode.entrySet()) {
					HostState current=entryCur.getValue();
					HostState next=entryNext.getValue();
					String currentOutput=current.getOutput();
					String nextOutput=next.getOutput();
					String currentOperation=current.getOperation();
					String nextOperation=next.getOperation();
					String currentHost=current.getHost();
					String nextHost=next.getHost();
					
					if(action.name().equalsIgnoreCase("wait")){
						if(currentOperation.equalsIgnoreCase("full")&&currentHost.equalsIgnoreCase("clean")
								&&nextOperation.equalsIgnoreCase("full")&&nextHost.equalsIgnoreCase("clean")){
							if((currentOutput.equalsIgnoreCase("null")&&nextOutput.equalsIgnoreCase("null"))||
									(currentOutput.equalsIgnoreCase("benign")&&nextOutput.equalsIgnoreCase("benign"))){
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("wait"),
										entryNext.getKey(), 1.);
								
							}
							if(currentOutput.equalsIgnoreCase("malicious")&&nextOutput.equalsIgnoreCase("malicious")){
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("wait"),
										entryNext.getKey(), 1-Parameters.mu);
								
							}
						}
						if(currentOperation.equalsIgnoreCase("full")&&currentHost.equalsIgnoreCase("clean")
								&&nextOperation.equalsIgnoreCase("full")&&nextHost.equalsIgnoreCase("infected")){
							if(currentOutput.equalsIgnoreCase("malicious")&&nextOutput.equalsIgnoreCase("malicious")){
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("wait"),
										entryNext.getKey(), Parameters.mu);
								
							}
						}
						
						if(currentOperation.equalsIgnoreCase("full")&&currentHost.equalsIgnoreCase("infected")
								&&nextOperation.equalsIgnoreCase("full")&&nextHost.equalsIgnoreCase("infected")){
							if((currentOutput.equalsIgnoreCase("null")&&nextOutput.equalsIgnoreCase("null"))||
									(currentOutput.equalsIgnoreCase("benign")&&nextOutput.equalsIgnoreCase("benign"))||
									(currentOutput.equalsIgnoreCase("malicious")&&nextOutput.equalsIgnoreCase("malicious"))){
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("wait"),
										entryNext.getKey(), 1.);
								
							}
							
							
						}
						
						if(currentOperation.equalsIgnoreCase("reset")&&currentHost.equalsIgnoreCase("clean")
								&&nextOperation.equalsIgnoreCase("full")&&nextHost.equalsIgnoreCase("clean")){
							if((currentOutput.equalsIgnoreCase("null")&&nextOutput.equalsIgnoreCase("null"))||
									(currentOutput.equalsIgnoreCase("benign")&&nextOutput.equalsIgnoreCase("benign"))||
									(currentOutput.equalsIgnoreCase("malicious")&&nextOutput.equalsIgnoreCase("malicious"))){
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("wait"),
										entryNext.getKey(), Parameters.theta);
								
							}
							
						}
						
						if(currentOperation.equalsIgnoreCase("reset")&&currentHost.equalsIgnoreCase("clean")
								&&nextOperation.equalsIgnoreCase("reset")&&nextHost.equalsIgnoreCase("clean")){
							if((currentOutput.equalsIgnoreCase("null")&&nextOutput.equalsIgnoreCase("null"))||
									(currentOutput.equalsIgnoreCase("benign")&&nextOutput.equalsIgnoreCase("benign"))||
									(currentOutput.equalsIgnoreCase("malicious")&&nextOutput.equalsIgnoreCase("malicious"))){
							gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("wait"),
									entryNext.getKey(), 1-Parameters.theta);
							}

						}
						
						
						
						if(currentOperation.equalsIgnoreCase("reset")&&currentHost.equalsIgnoreCase("infected")
								&& nextOperation.equalsIgnoreCase("full")&& nextHost.equalsIgnoreCase("clean")){
							if((currentOutput.equalsIgnoreCase("null")&&nextOutput.equalsIgnoreCase("null"))||
									(currentOutput.equalsIgnoreCase("benign")&&nextOutput.equalsIgnoreCase("benign"))||
									(currentOutput.equalsIgnoreCase("malicious")&&nextOutput.equalsIgnoreCase("malicious"))){
							
							gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("wait"),
									entryNext.getKey(), Parameters.psi);
							}
						}
						
						if(currentOperation.equalsIgnoreCase("reset")&&currentHost.equalsIgnoreCase("infected")
								&&nextOperation.equalsIgnoreCase("reset")&&nextHost.equalsIgnoreCase("infected")){
							if((currentOutput.equalsIgnoreCase("null")&&nextOutput.equalsIgnoreCase("null"))||
									(currentOutput.equalsIgnoreCase("benign")&&nextOutput.equalsIgnoreCase("benign"))||
									(currentOutput.equalsIgnoreCase("malicious")&&nextOutput.equalsIgnoreCase("malicious"))){
							gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("wait"),
									entryNext.getKey(),1- Parameters.psi);
							}

						}
					}
					
					if(action.name().equalsIgnoreCase("reset")){
						if(currentOperation.equalsIgnoreCase("full")&&currentHost.equalsIgnoreCase("clean")
								&&nextOperation.equalsIgnoreCase("reset")&&nextHost.equalsIgnoreCase("clean")){
							if(currentOutput.equalsIgnoreCase("malicious")&&nextOutput.equalsIgnoreCase("malicious")){
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("reset"),
									entryNext.getKey(),1.);
								

							}
						}
						if(currentOperation.equalsIgnoreCase("full")&&currentHost.equalsIgnoreCase("infected")
								&&nextOperation.equalsIgnoreCase("reset")&&nextHost.equalsIgnoreCase("infected")){
							if(currentOutput.equalsIgnoreCase("malicious")&&nextOutput.equalsIgnoreCase("malicious")){
								gdd.setTransition(entryCur.getKey(), action.getIntValueFromString("reset"),
									entryNext.getKey(),1.);
								

							}
						}
					}
				}

			}

		}	
		
		
		return gdd;
	}
	
	public Map<Integer, Map<Integer, Set<NodeTransitionProbability>>> helper(HostGraph hgdd){
		hgdd=hgdd.generateDomainHelper(hgdd);
		this.transitionDynamics=super.transitionDynamics;
		return this.transitionDynamics;
	}
	
	public static void main(String[] args) {
		HostGraph hostGraph=new HostGraph(8);
		hostGraph=hostGraph.generateDomainHelper(hostGraph);
		System.out.println(hostGraph.isValidMDPGraph());
	}
}
