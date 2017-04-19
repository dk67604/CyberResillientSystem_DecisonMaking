package org.edu.crsmdp.ids;

public class IDSState {
	
	private String infection;
	private String output;
	private String idsState;
	
	
	
	
	public IDSState(String infection, String output, String idsState) {
		this.infection = infection;
		this.output = output;
		this.idsState = idsState;
	}
	public String getinfection() {
		return infection;
	}
	public void setinfection(String infection) {
		this.infection = infection;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getIdsState() {
		return idsState;
	}
	public void setIdsState(String idsState) {
		this.idsState = idsState;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idsState == null) ? 0 : idsState.hashCode());
		result = prime * result + ((infection == null) ? 0 : infection.hashCode());
		result = prime * result + ((output == null) ? 0 : output.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IDSState other = (IDSState) obj;
		if (idsState == null) {
			if (other.idsState != null)
				return false;
		} else if (!idsState.equals(other.idsState))
			return false;
		if (infection == null) {
			if (other.infection != null)
				return false;
		} else if (!infection.equals(other.infection))
			return false;
		if (output == null) {
			if (other.output != null)
				return false;
		} else if (!output.equals(other.output))
			return false;
		return true;
	}
	
	
	

}
