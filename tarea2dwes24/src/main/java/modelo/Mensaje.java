package modelo;

import java.time.LocalDateTime;

public class Mensaje {

	/** Declaración de atributos */

	private Long id;
	private LocalDateTime fechaHora;
	private String mensaje;
	private Long idEjemplar;
	private Long idPersona;

	/** Constructor por defecto y por parámetros */

	public Mensaje() {

	}

	public Mensaje(String mensaje, Long idEjemplar, Long idPersona) {
		this.mensaje = mensaje;
		this.idEjemplar = idEjemplar;
		this.idPersona = idPersona;
	}
	


	public Mensaje(Long id, LocalDateTime fechaHora, String mensaje, Long idEjemplar, Long idPersona) {
		super();
		this.id = id;
		this.fechaHora = fechaHora;
		this.mensaje = mensaje;
		this.idEjemplar = idEjemplar;
		this.idPersona = idPersona;
	}

	/** Getters y Setters */

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

	@Override
	public String toString() {
		return "ID MENSAJE: " + id  + " MENSAJE: " + mensaje + " ID DEL EJEMPLAR: " + idEjemplar
				+ " ID PERSONA: " + idPersona + " FECHA: " + fechaHora;
	}

	
}
