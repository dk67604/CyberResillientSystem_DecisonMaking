package org.edu.crsmdp;

import java.util.Map;

import org.edu.crsmdp.ids.Utility;

import burlap.domain.singleagent.graphdefined.GraphRF;

public class IDSHostRewardFunction extends GraphRF{
	
	 public IDSHostRewardFunction() {
		super();
	}

	@Override
	public double reward(int stateKey, int action, int nextState) {
		// TODO Auto-generated method stub
		Map<Integer,IDSHostState> stateEncode=Utility.generateIDSHostStateFactory();
		IDSHostState h=stateEncode.get(stateKey);
		if(h.getOperation().equalsIgnoreCase("full")&&h.getHost().equalsIgnoreCase("clean")&&
				(h.getOutput().equalsIgnoreCase("benign")||h.getOutput().equalsIgnoreCase("null")||
						h.getOutput().equalsIgnoreCase("malicious"))){
			  return 1.;
		}
		if(h.getOperation().equalsIgnoreCase("full")&&h.getHost().equalsIgnoreCase("infected")&&
				(h.getOutput().equalsIgnoreCase("benign")||h.getOutput().equalsIgnoreCase("null")||
						h.getOutput().equalsIgnoreCase("malicious"))){
			return -1.5;
		}
		if(h.getOperation().equalsIgnoreCase("reset")&&(h.getHost().equalsIgnoreCase("clean")||
				h.getHost().equalsIgnoreCase("infected"))&&(h.getOutput().equalsIgnoreCase("benign")
						||h.getOutput().equalsIgnoreCase("null")||h.getOutput().equalsIgnoreCase("malicious"))){
			return -3.0;
		}
			
	   
		System.out.println("Reward is undefined");
		return 0;
	}

}
