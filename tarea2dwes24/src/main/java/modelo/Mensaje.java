package modelo;

import java.time.LocalDateTime;

public class Mensaje {
	
		//Atributos
		private Long id;    
	    private LocalDateTime fechaHora; 
	    private String mensaje;
	    private Ejemplar idEjemplar;           
	    private Persona idPersona;
		
	    
	    //Constructor por defecto.
	    public Mensaje() {
			
		}


		// Constructor
	    public Mensaje(Long id, String mensaje, Ejemplar ejemplar, Persona persona) {
			this.id = id;
			this.fechaHora = LocalDateTime.now();
			this.mensaje = mensaje;
			this.idEjemplar = ejemplar;
			this.idPersona = persona;
		}

	    
	    //Getters and Setters.
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public LocalDateTime getFechaHora() {
			return fechaHora;
		}

		public void setFechaHora(LocalDateTime fechaHora) {
			this.fechaHora = fechaHora;
		}

		public String getMensaje() {
			return mensaje;
		}

		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}

		public Ejemplar getEjemplar() {
			return idEjemplar;
		}

		public void setEjemplar(Ejemplar ejemplar) {
			this.idEjemplar = ejemplar;
		}

		public Persona getPersona() {
			return idPersona;
		}

		public void setPersona(Persona persona) {
			this.idPersona = persona;
		}


		//Método toString
		@Override
		public String toString() {
			return "Mensaje [id=" + id + ", fechaHora=" + fechaHora + ", mensaje=" + mensaje + ", ejemplar=" + idEjemplar
					+ ", persona=" + idPersona + "]";
		}            
	    
	
	

	 

}
