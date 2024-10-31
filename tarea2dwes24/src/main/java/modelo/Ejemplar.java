package modelo;

public class Ejemplar {
	
	//Atributos
	private Long id;
	private String nombre;
	private Planta planta;
	
	
	 //Constructor por defecto.
    public Ejemplar() {
		
	}
	
	//Constructor
	public Ejemplar(Long id, Planta planta) {
		this.id = id;
		this.nombre = generarNombre();
		this.planta = planta;
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


	public Planta getPlanta() {
		return planta;
	}


	public void setCodigoPlanta(Planta planta) {
		this.planta = planta;
	}
	

	
	//Método toString
	
	@Override
	public String toString() {
		return "Ejemplar [id=" + id + ", nombre=" + nombre + ", planta=" + planta + "]";
	}

	
	//Método Generar Nombre
	
	private String generarNombre() {
        return planta.getCodigo() + "_" + id;
    }


	


}
