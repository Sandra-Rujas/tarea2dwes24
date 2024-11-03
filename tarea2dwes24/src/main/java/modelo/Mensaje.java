package modelo;

import java.time.LocalDateTime;

public class Mensaje {
	
		//Atributos
		private Long id;    
	    private LocalDateTime fechaHora; 
	    private String mensaje;
	    private Long idEjemplar;           
	    private Long idPersona;
		
	    
	    //Constructor por defecto.
	    public Mensaje() {
			
		}


		// Constructor
	    public Mensaje(Long id, String mensaje, Long idEjemplar, Long idPersona) {
			this.id = id;
			this.fechaHora = LocalDateTime.now();
			this.mensaje = mensaje;
			this.idEjemplar = idEjemplar;
			this.idPersona = idPersona;
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


		public Long getIdEjemplar() {
			return idEjemplar;
		}


		public void setIdEjemplar(Long idEjemplar) {
			this.idEjemplar = idEjemplar;
		}


		public Long getIdPersona() {
			return idPersona;
		}


		public void setIdPersona(Long idPersona) {
			this.idPersona = idPersona;
		}


		// to String
		@Override
		public String toString() {
			return "Mensaje [id=" + id + ", fechaHora=" + fechaHora + ", mensaje=" + mensaje + ", idEjemplar="
					+ idEjemplar + ", idPersona=" + idPersona + "]";
		}

		 
	    
	
	

	 

}
