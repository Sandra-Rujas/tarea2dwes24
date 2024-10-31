package modelo;

public class Persona {
	
	//Atributos
	private Long id;
	private String nombre;
	private String email;
	private Credencial credencial;
	
	
	 //Constructor por defecto.
    public Persona() {
		
	}
	
    //Constructor por parámetros.
	public Persona(Long id, String nombre, String email, Credencial credencial) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.credencial = credencial;
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

	public Credencial getCredencial() {
		return credencial;
	}

	public void setCredencial(Credencial credencial) {
		this.credencial = credencial;
	}

	
	//Método toString
	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", email=" + email + ", credencial=" + credencial + "]";
	}
	
	
	
	

}
