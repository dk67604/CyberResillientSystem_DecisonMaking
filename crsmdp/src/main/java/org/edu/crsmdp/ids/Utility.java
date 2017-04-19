package org.edu.crsmdp.ids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.edu.crsmdp.IDSHostAction;
import org.edu.crsmdp.IDSHostGraph;
import org.edu.crsmdp.IDSHostState;
import org.edu.crsmdp.host.HostAction;
import org.edu.crsmdp.host.HostState;

// TODO: Auto-generated Javadoc
/**
 * The Class Utility.
 */
public class Utility {

	/**
	 * Generate state factory.
	 *
	 * @return the map
	 */
	public static Map<Integer, IDSState> generateIDSStateFactory() {
		Map<Integer, IDSState> stateEncode = new LinkedHashMap<Integer, IDSState>();
		// "Infection" "Output" "IDSState"
		stateEncode.put(0, new IDSState("benign", "benign", "idle"));
		stateEncode.put(1, new IDSState("benign", "null", "idle"));
		stateEncode.put(2, new IDSState("benign", "malicious", "idle"));
		stateEncode.put(3, new IDSState("benign", "benign", "busy"));
		stateEncode.put(4, new IDSState("benign", "null", "busy"));
		stateEncode.put(5, new IDSState("benign", "malicious", "busy"));
		stateEncode.put(6, new IDSState("malicious", "benign", "idle"));
		stateEncode.put(7, new IDSState("malicious", "null", "idle"));
		stateEncode.put(8, new IDSState("malicious", "malicious", "idle"));
		stateEncode.put(9, new IDSState("malicious", "benign", "busy"));
		stateEncode.put(10, new IDSState("malicious", "null", "busy"));
		stateEncode.put(11, new IDSState("malicious", "malicious", "busy"));
		return stateEncode;

	}

	public static Map<Integer, HostState> generateHostStateFactory() {
		Map<Integer, HostState> stateEncode = new LinkedHashMap<Integer, HostState>();
		                                 // "Y" "W" "H"
		stateEncode.put(0, new HostState("benign", "full", "clean"));
		stateEncode.put(1, new HostState("benign", "full", "infected"));
		stateEncode.put(2, new HostState("benign", "reset", "clean"));
		stateEncode.put(3, new HostState("benign", "reset", "infected"));
		stateEncode.put(4, new HostState("malicious", "full", "clean"));
		stateEncode.put(5, new HostState("malicious", "full", "infected"));
		stateEncode.put(6, new HostState("malicious", "reset", "clean"));
		stateEncode.put(7, new HostState("malicious", "reset", "infected"));
		stateEncode.put(8, new HostState("null", "full", "clean"));
		stateEncode.put(9, new HostState("null", "full", "infected"));
		stateEncode.put(10, new HostState("null", "reset", "clean"));
		stateEncode.put(11, new HostState("null", "reset", "infected"));
		return stateEncode;
	}
	
	public static Map<Integer,IDSHostState> generateIDSHostStateFactory(){
		Map<Integer,IDSHostState> stateEncode=new LinkedHashMap<Integer, IDSHostState>();
		Map<Integer, HostState> hostStateEncode=Utility.generateHostStateFactory();
		Map<Integer, IDSState> idsstateEncode =Utility.generateIDSStateFactory();
		int i=0;
		for(Map.Entry<Integer, IDSState> e1:idsstateEncode.entrySet()){
			for(Map.Entry<Integer, HostState> e2:hostStateEncode.entrySet()){
				if(e1.getValue().getOutput().equalsIgnoreCase(e2.getValue().getOutput())){
					IDSHostState idsHostState=new IDSHostState(e1.getValue().getinfection(), e1.getValue().getIdsState(), e1.getValue().getOutput(), e2.getValue().getOperation(), e2.getValue().getHost());
					stateEncode.put(i++, idsHostState);
					
				}
			}

		}
		return stateEncode;
	}
	

public static Integer getIDSHostStateEncode(Map<Integer,IDSHostState> stateFacory,String infection,String idsState,String output,String operation,String host){
	for(Map.Entry<Integer, IDSHostState> e:stateFacory.entrySet()){
		IDSHostState v=e.getValue();
		if(v.getInfection().equalsIgnoreCase(infection)&&v.getIdsState().equalsIgnoreCase(idsState)&&
				v.getOutput().equalsIgnoreCase(output)&&v.getOperation().equalsIgnoreCase(operation)&&v.getHost().equalsIgnoreCase(host)){
			return e.getKey();
		}
	}

	return -1;
}
	
	/**
	 * Gets the encoded state.
	 *
	 * @return the encoded state
	 */
	public static List<Integer> getEncodedState() {
		Map<Integer, IDSState> stateEncode = Utility.generateIDSStateFactory();
		Set<Integer> encodedSet = stateEncode.keySet();
		List<Integer> encodedState = new ArrayList<Integer>();
		for (Integer i : encodedSet) {
			encodedState.add(i);
		}
		Collections.sort(encodedState);
		return encodedState;

	}
	
	public static Integer getIDSHostActionencode(Integer idsActionEncode,Integer hostActionEncode){
		String idsAction=IDSAction.getEnumNameFromValue(idsActionEncode);
		String hostAction=HostAction.getEnumNameFromValue(hostActionEncode);
		//System.out.println(idsAction+hostAction);
		Integer i=IDSHostAction.getIntValueFromString(idsAction+hostAction);
		return i;
	}
	

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
	/*	List<Integer> encodedState = Utility.getEncodedState();
		for (Integer i : encodedState) {
			System.out.println(i);
		}*/
		Map<Integer,IDSHostState> stateFacory=generateIDSHostStateFactory();
		System.out.println(stateFacory.size());
		for(Map.Entry<Integer, IDSHostState> e:stateFacory.entrySet()){
		System.out.println(e.getKey()+" "+e.getValue());
		}
		
		System.out.println(getIDSHostStateEncode(stateFacory,"malicious","busy","null","reset","infected"));
	}
}
