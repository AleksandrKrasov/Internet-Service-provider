package ua.khpi.krasov.db.entity;

import java.io.Serializable;

public class Entity implements Serializable {

	private static final long serialVersionUID = 8792085611790559352L;
	
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
