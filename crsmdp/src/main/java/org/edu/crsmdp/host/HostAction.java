package org.edu.crsmdp.host;



public enum HostAction {
	 WAIT(0),RESET(1);
		private Integer actionName;
		
		private HostAction(Integer actionName){
			this.actionName=actionName;
		}
		
		public Integer getValue(){
			return actionName;
		}
		
		 public  Integer getIntValueFromString(String i) {
		     for (HostAction action : HostAction.values()) {
		         if (action.name().equalsIgnoreCase(i)) {
		             return action.getValue();
		         }
		     }
		     throw new IllegalArgumentException("the given string doesn't match any Status.");
		 }
		 
		 public static String getEnumNameFromValue(Integer i){
			 for (HostAction action : HostAction.values()) {
				 if(action.getValue().intValue()==i.intValue()){
					 return action.name();
				 }
				 
				 
			 }
			  throw new IllegalArgumentException("the given number doesn't match any Status.");
		 }
		 
		 
		 public static void main(String[] args) {
			for(HostAction action : HostAction.values()){
				System.out.println(action.getEnumNameFromValue(action.getValue()));
			}
		}
}
