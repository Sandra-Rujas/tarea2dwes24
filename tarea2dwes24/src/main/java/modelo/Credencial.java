package modelo;

public class Credencial {
	
	/**Declaración de atributos*/
	
	private Long id;
	private String usuario;
	private String password;
	private Long idPersona;
	
	
	/**Constructor por defecto y por parámetros*/
	
    public Credencial() {
		
	}
    
    public Credencial(String usuario, String password) {
		this.usuario = usuario;
		this.password = password;
    }
	   
	
	public Credencial(String usuario, String password, Long idPersona) {
		this.usuario = usuario;
		this.password = password;
		this.idPersona = idPersona;
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

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	
	

	

	
	
}
