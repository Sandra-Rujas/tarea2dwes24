package modelo;

public class Planta {

		
	/** Declaración de atributos */
	
		private String codigo;
		private String nombreComun;
		private String nombreCientifico;
		
		
		/** Constructor por defecto y por parámetros */
		
		public Planta(String codigo, String nombreCientifico, String nombreComun) {
			this.codigo = codigo;
			this.nombreCientifico = nombreCientifico;
			this.nombreComun = nombreComun;
		}
		
		/** Getters y Setters */
		
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

		

		
		

	}
