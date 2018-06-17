package ua.khpi.krasov.db.entity;

public class User extends Entity {

	private static final long serialVersionUID = 168170747199087024L;
	
	private String login;
	
	private String firstName;
	
	private String LastName;
	
	private String password;
	
	private int roleId;
	
	private int bill;
	
	private int status_id;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getBill() {
		return bill;
	}

	public void setBill(int bull) {
		this.bill = bull;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", firstName=" + firstName + ", LastName=" + LastName + ", password=" + password
				+ ", roleId=" + roleId + ", bill=" + bill + ", status_id=" + status_id + "]";
	}
	
}
