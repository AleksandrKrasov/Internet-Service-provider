package ua.khpi.krasov.db;

import ua.khpi.krasov.db.entity.User;


/**
 * Role sets enumeration for roles used in User: admin, client.
 * 
 *  @author A.Krasov
 *  @version 1.0
 */
public enum Role {
	
	ADMIN, CLIENT;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
}
