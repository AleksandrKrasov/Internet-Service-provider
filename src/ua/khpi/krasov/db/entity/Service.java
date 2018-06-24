package ua.khpi.krasov.db.entity;

/**
 * Entity Service class. It allows to store data from
 * DB. Class does not have any logic. Class extends Entity class.
 * This is serializable.
 * 
 * @author A.Krasov
 * @see Entity
 *
 */
public class Service extends Entity {

	private static final long serialVersionUID = 8443823000835259544L;
	
	private String name;
	
	private String nameRu;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNameRu() {
		return nameRu;
	}

	public void setNameRu(String nameRu) {
		this.nameRu = nameRu;
	}

	@Override
	public String toString() {
		return "Service [name=" + name + "]";
	}
	
}
