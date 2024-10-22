package modelo;

import java.util.Objects;

public class Planta {
	
	//Atributos
	private String codigo;
	private String nombrecomun;
	private String nombrecientifico;
	
	
	//Constructor
	public Planta(String codigo, String nombrecomun, String nombrecientifico) {
		super();
		this.codigo = codigo;
		this.nombrecomun = nombrecomun;
		this.nombrecientifico = nombrecientifico;
	}
	//Getters and Setters
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombrecomun() {
		return nombrecomun;
	}
	public void setNombrecomun(String nombrecomun) {
		this.nombrecomun = nombrecomun;
	}
	public String getNombrecientifico() {
		return nombrecientifico;
	}
	public void setNombrecientifico(String nombrecientifico) {
		this.nombrecientifico = nombrecientifico;
	}
	
	//Métodos Equals y HashCode
	@Override
	public int hashCode() {
		return Objects.hash(codigo, nombrecientifico, nombrecomun);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Planta other = (Planta) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(nombrecientifico, other.nombrecientifico)
				&& Objects.equals(nombrecomun, other.nombrecomun);
	}
	//Método ToString
	@Override
	public String toString() {
		return "Planta [codigo=" + codigo + ", nombrecomun=" + nombrecomun + ", nombrecientifico=" + nombrecientifico
				+ "]";
	}
	
	
	

}
