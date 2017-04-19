package org.edu.crsmdp;

public enum IDSHostAction {
 
	INSPECTWAIT(0),INSPECTRESET(1),PASSWAIT(2),PASSRESET(3);
	private Integer actionName;
	
	private IDSHostAction(Integer actionName){
		this.actionName=actionName;
	}
	
	public Integer getValue(){
		return actionName;
	}
	
	 public static Integer getIntValueFromString(String i) {
	     for (IDSHostAction action : IDSHostAction.values()) {
	         if (action.name().equalsIgnoreCase(i)) {
	             return action.getValue();
	         }
	     }
	     throw new IllegalArgumentException("the given number doesn't match any Status.");
	 }
	 public static String getEnumNameFromValue(Integer i){
		 for (IDSHostAction action : IDSHostAction.values()) {
			 if(action.getValue().intValue()==i.intValue()){
				 return action.name();
			 }
			 
			 
		 }
		  throw new IllegalArgumentException("the given number doesn't match any Status.");
	 }
	 
	
}
