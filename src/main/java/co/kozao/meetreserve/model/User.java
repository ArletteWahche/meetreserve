package co.kozao.meetreserve.model;

public class User {
	
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String password;
	private Role role;
	
	
	public User(Long id, String name, String surname, String email, String password, Role role ) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.role = role;
	}
}
