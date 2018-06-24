package ua.khpi.krasov.db.entity;

import java.io.Serializable;

/**
 * An abstract class for creation entities. If you want to
 * create any entities you should extend this class.
 * This class implements Serializable interface to give a 
 * possibility to serialize this class.
 * 
 * @author A.Krasov
 * @version 1.0
 *
 */
public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 8792085611790559352L;
	
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
