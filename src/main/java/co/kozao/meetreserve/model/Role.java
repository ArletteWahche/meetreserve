package co.kozao.meetreserve.model;

public enum Role {
	
	ADMINISTRATOR,
	MANAGER,
	EMPLOYEE;

	public static Role valueOf(int roleParam) throws IllegalArgumentException {
		return Role.valueOf(roleParam);
	}

}



