package modelo;

public class Ejemplar {

	/** Declaración de atributos */

	private Long id;
	private String nombre;
	private String codigoPlanta;

	/**Constructor por defecto y por parámetros*/
	
	public Ejemplar() {

	}

	public Ejemplar(Long id, String codigoplanta) {
		this.id = id;
		this.nombre = id + "_" + codigoplanta;
		this.codigoPlanta = codigoplanta;
	}

	/**Getters y Setters*/ 
	
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

}