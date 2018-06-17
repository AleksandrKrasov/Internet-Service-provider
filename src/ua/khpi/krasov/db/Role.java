package ua.khpi.krasov.db;

import ua.khpi.krasov.db.entity.User;

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
