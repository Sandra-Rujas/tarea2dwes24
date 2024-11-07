package modelo;

public class Persona {
	
	/** Declaración de atributos */
	
	private Long id;
	private String nombre;
	private String email;
	private Long idCredencial;

	
	/** Constructor por defecto y por parámetros */
	
    public Persona() {
		
	}
	
	public Persona(String nombre, String email) {
		this.nombre = nombre;
		this.email = email;
		
	}
	
	/** Getters y Setters */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdCredencial() {
		return idCredencial;
	}

	public void setIdCredencial(Long idCredencial) {
		this.idCredencial = idCredencial;
	}


	
	
	
	

}
