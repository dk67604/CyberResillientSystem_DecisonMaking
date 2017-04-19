package org.edu.crsmdp;

public class IDSHostState {
	
	private String infection;
	private String idsState;
	private String output;
	private String operation;
	private String host;
	
	
	
	public IDSHostState(String infection, String idsState, String output, String operation, String host) {
		this.infection = infection;
		this.idsState = idsState;
		this.output = output;
		this.operation = operation;
		this.host = host;
	}
	public String getInfection() {
		return infection;
	}
	public void setInfection(String infection) {
		this.infection = infection;
	}
	public String getIdsState() {
		return idsState;
	}
	public void setIdsState(String idsState) {
		this.idsState = idsState;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((idsState == null) ? 0 : idsState.hashCode());
		result = prime * result + ((infection == null) ? 0 : infection.hashCode());
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
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
		IDSHostState other = (IDSHostState) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
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
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (output == null) {
			if (other.output != null)
				return false;
		} else if (!output.equals(other.output))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "IDSHostState [infection=" + infection + ", idsState=" + idsState + ", output=" + output + ", operation="
				+ operation + ", host=" + host + "]";
	}
	

}
