package modelo;

public class Ejemplar {
	
	//Atributos
	private Long id;
	private String nombre;
	private String codigoPlanta;
	
	
	 //Constructor por defecto.
    public Ejemplar() {
		
	}
	
	//Constructor
	public Ejemplar(Long id, String nombre, String codigoplanta) {
		this.id = id;
		this.nombre = nombre;
		this.codigoPlanta = codigoplanta;
	}


	//Getters y Setters
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
	
	public String getCodigoPlanta() {
		return codigoPlanta;
	}

	public void setCodigoPlanta(String codigoPlanta) {
		this.codigoPlanta = codigoPlanta;
	}

	
	// to String
	@Override
	public String toString() {
		return "Ejemplar [id=" + id + ", nombre=" + nombre + ", codigoPlanta=" + codigoPlanta + "]";
	}

	
	

	


}
