package com.rest.Entity;

public class Role {
	public static final String USER = "USER";
	// This role implicitly allows access to all views.
	public static final String ADMIN = "ADMIN";

	private Role() {
		// Static methods and fields only
	}

	public static String[] getAllRoles() {
		return new String[] { USER, ADMIN };
	}

}