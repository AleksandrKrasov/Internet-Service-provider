package ua.khpi.krasov.db.entity;

/**
 * Entity User class. It allows to store data from
 * DB. Class does not have any logic. Class extends Entity class.
 * This is serializable.
 * 
 * @author A.Krasov
 * @see Entity
 *
 */
public class User extends Entity {

	private static final long serialVersionUID = 168170747199087024L;
	
	private String login;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private int roleId;
	
	private int bill;
	
	private int statusId;

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
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", firstName=" + firstName + ", LastName=" + lastName + ", password=" + password
				+ ", roleId=" + roleId + ", bill=" + bill + ", status_id=" + statusId + "]";
	}
	
}
