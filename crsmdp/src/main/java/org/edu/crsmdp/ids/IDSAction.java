package org.edu.crsmdp.ids;

public enum IDSAction {
 INSPECT(0),PASS(1);
	private Integer actionName;
	
	private IDSAction(Integer actionName){
		this.actionName=actionName;
	}
	
	public Integer getValue(){
		return actionName;
	}
	
	 public  Integer getIntValueFromString(String i) {
	     for (IDSAction action : IDSAction.values()) {
	         if (action.name().equalsIgnoreCase(i)) {
	             return action.getValue();
	         }
	     }
	     throw new IllegalArgumentException("the given number doesn't match any Status.");
	 }
	 public static String getEnumNameFromValue(Integer i){
		 for (IDSAction action : IDSAction.values()) {
			 if(action.getValue().intValue()==i.intValue()){
				 return action.name();
			 }
			 
			 
		 }
		  throw new IllegalArgumentException("the given number doesn't match any Status.");
	 }
	 
	 public static void main(String[] args) {
		//System.out.println(IDSAction.getIntValueFromString("pass"));
	}
}
