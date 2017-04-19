package org.edu.crsmdp;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.edu.crsmdp.host.HostState;
import org.edu.crsmdp.ids.IDSState;
import org.edu.crsmdp.ids.Utility;

import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;

public class IDSHostGraph extends GraphDefinedDomain {
	Map<Integer, IDSHostState> idshostStateEncode;
	public IDSHostGraph(int numNodes){
		super(numNodes);
	}
	
	public  IDSHostGraph productTransitions(IDSGraph igdd,HostGraph hgdd,IDSHostGraph idsHostGdd){
		Map<Integer, IDSState> idsStateEncode = Utility.generateIDSStateFactory();
		Map<Integer, HostState> hostStateEncode = Utility.generateHostStateFactory();
		idshostStateEncode=Utility.generateIDSHostStateFactory();
        int i=0;
        outerLoop:
		for(Map.Entry<Integer, Map<Integer, Set<NodeTransitionProbability>>> e : igdd.helper(igdd).entrySet()){
			Map<Integer, Set<NodeTransitionProbability>> at = e.getValue();
			for(Map.Entry<Integer, Set<NodeTransitionProbability>> e2 : at.entrySet()){
				if(!e2.getValue().isEmpty()) {
					for(NodeTransitionProbability intp : e2.getValue()) {
						for(Map.Entry<Integer, Map<Integer, Set<NodeTransitionProbability>>> h : hgdd.helper(hgdd).entrySet()){
				        	Map<Integer, Set<NodeTransitionProbability>> ht = h.getValue();
							for(Map.Entry<Integer, Set<NodeTransitionProbability>> h2 : ht.entrySet()){
								if(!h2.getValue().isEmpty()) {
									for(NodeTransitionProbability hntp : h2.getValue()) {
										if(idsStateEncode.get(e.getKey()).getOutput().equalsIgnoreCase(hostStateEncode.get(h.getKey()).getOutput())){
											Integer currentStateEncode=Utility.getIDSHostStateEncode(idshostStateEncode,idsStateEncode.get(e.getKey()).getinfection() ,
													idsStateEncode.get(e.getKey()).getIdsState(), idsStateEncode.get(e.getKey()).getOutput(), 
													hostStateEncode.get(h.getKey()).getOperation(), hostStateEncode.get(h.getKey()).getHost());
											Integer nextStateEncode=Utility.getIDSHostStateEncode(idshostStateEncode,idsStateEncode.get(intp.transitionTo).getinfection() ,
													idsStateEncode.get(intp.transitionTo).getIdsState(), idsStateEncode.get(intp.transitionTo).getOutput(), 
													hostStateEncode.get(hntp.transitionTo).getOperation(), hostStateEncode.get(hntp.transitionTo).getHost());
											
										    Integer actionEncode=Utility.getIDSHostActionencode(e2.getKey(), h2.getKey());	 
										    if(currentStateEncode!=-1&&nextStateEncode!=-1){
										     idsHostGdd.setTransition(currentStateEncode, actionEncode, nextStateEncode, intp.probability*hntp.probability);
										    ++i;
										    }
										
										}
									}
								}
							}
						}
						
						
					}
					
				}

			}
			
		}
		return idsHostGdd;
	}

	
	
	public static void main(String[] args) {
		IDSGraph ids=new IDSGraph(12,0.5);
		HostGraph hgdd=new HostGraph(8);
		IDSHostGraph idsHostGdd=new IDSHostGraph(32);
		idsHostGdd=idsHostGdd.productTransitions(ids, hgdd,idsHostGdd);
		
	
		System.out.println(idsHostGdd.invalidMDPReport());
		
		/*getIDSHostActionencode(1, 1);*/
		
	}
}
