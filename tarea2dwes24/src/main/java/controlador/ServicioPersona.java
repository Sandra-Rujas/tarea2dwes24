package controlador;

import java.util.List;

import dao.CredencialDAO;
import dao.PersonaDAO;
import modelo.Persona;
import modelo.Planta;
import utils.ConexionBD;

public class ServicioPersona {

	
	private ConexionBD con;
	private PersonaDAO personaDAO;
	private CredencialDAO credencialDAO;
	
	public ServicioPersona() {
		con = ConexionBD.getInstance();
		personaDAO = (PersonaDAO) con.getPersonaDAO();
	}
	
	
	public boolean insertarPersona(String nombre, String email, String nombreUsuario, String password) {
		
		int idPersona = personaDAO.insertarPersona(nombre, email, nombreUsuario, password);
		if(idPersona != -1) {
			
			boolean todoRegistrado = credencialDAO.insertarCredencial(idPersona,nombreUsuario, password);
			
			if(todoRegistrado) {
				return true;
			}
		} 
        return false; 
       
}

	
	public List<Persona> mostrarPersona(){
		return personaDAO.findAll();
	}
	
	public Persona findById(Long codigo){
		return personaDAO.findById(codigo);
	}
	
	public boolean isEmailRegistrado(String email) {
		return personaDAO.isEmailRegistrado(email);
	}
}
