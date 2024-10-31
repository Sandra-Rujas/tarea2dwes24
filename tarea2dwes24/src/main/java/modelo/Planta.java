package modelo;

public class Planta {

		
		//Atributos
		private String codigo;
		private String nombreComun;
		private String nombreCientifico;
		
		
		//Constructor
		public Planta(String codigo, String nombreComun, String nombreCientifico) {
			this.codigo = codigo;
			this.nombreComun = nombreComun;
			this.nombreCientifico = nombreCientifico;
		}
		
		//Getters and Setters
		public String getCodigo() {
			return codigo;
		}
		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}
		public String getNombrecomun() {
			return nombreComun;
		}
		public void setNombrecomun(String nombreComun) {
			this.nombreComun = nombreComun;
		}
		public String getNombrecientifico() {
			return nombreCientifico;
		}
		public void setNombrecientifico(String nombreCientifico) {
			this.nombreCientifico = nombreCientifico;
		}

		//Método toString
		@Override
		public String toString() {
			return "Planta [codigo=" + codigo + ", nombreComun=" + nombreComun + ", nombreCientifico=" + nombreCientifico
					+ "]";
		}

		
		

	}
