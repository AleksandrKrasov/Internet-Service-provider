package ua.khpi.krasov.db.entity;

public class Service extends Entity{

	private static final long serialVersionUID = 8443823000835259544L;
	
	private String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Service [name=" + name + "]";
	}
	
}
