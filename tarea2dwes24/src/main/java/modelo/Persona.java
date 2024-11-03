package modelo;

public class Persona {
	
	//Atributos
	private Long id;
	private String nombre;
	private String email;
	private Long idCredencial;
	
	
	 //Constructor por defecto.
    public Persona() {
		
	}
	
    //Constructor por parámetros.
	public Persona(Long id, String nombre, String email, Long idCredencial) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.idCredencial = idCredencial;
	}
	
	//Getters y Setters.

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

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", email=" + email + ", idCredencial=" + idCredencial + "]";
	}

	
	
	
	

}
