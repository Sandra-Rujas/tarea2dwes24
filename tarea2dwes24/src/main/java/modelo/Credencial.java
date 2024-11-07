package modelo;

public class Credencial {
	
	/**Declaración de atributos*/
	
	private Long id;
	private String usuario;
	private String password;
	
	
	/**Constructor por defecto y por parámetros*/
	
    public Credencial() {
		
	}
	
	public Credencial(String usuario, String password) {
		this.usuario = usuario;
		this.password = password;
	}
	
	
	/**Getters y Setters*/ 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	

	
	
}
