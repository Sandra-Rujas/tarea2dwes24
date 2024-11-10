package controlador;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import dao.MensajeDAO;
import modelo.Mensaje;
import modelo.Persona;
import utils.ConexionBD;

public class ServicioMensaje {
	
	private ConexionBD con;
	private MensajeDAO mensajeDAO;
	
	public ServicioMensaje() {
		con = ConexionBD.getInstance();
		mensajeDAO = (MensajeDAO) con.getMensajeDAO();
	}
	
	
	public int insertarMensaje(Mensaje mensaje) {
		return mensajeDAO.insertarMensaje(mensaje);
	}
	
	
	public List<Mensaje> mostrarMensajes(){
		return mensajeDAO.findAll();
	}
	
	public Mensaje findById(Long codigo){
		return mensajeDAO.findById(codigo);
	}
	
	public List<Mensaje> buscarMensajePorPersona(Long id){
		return mensajeDAO.buscarMensajePorPersona(id);
	}
	
	public List<Mensaje> buscarMensajePorFecha(LocalDate fecha1, LocalDate fecha2){
		return mensajeDAO.buscarMensajePorFecha(fecha1, fecha2);
	}
	
	public List<Mensaje> buscarMensajePorPlanta(String codigo){
		return mensajeDAO.buscarMensajePorPlanta(codigo);
	}
	
}
