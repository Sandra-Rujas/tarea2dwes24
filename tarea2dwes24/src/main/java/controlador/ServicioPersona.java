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

	public ServicioPersona() {
		con = ConexionBD.getInstance();
		personaDAO = (PersonaDAO) con.getPersonaDAO();
	}

	public int insertarPersona(Persona persona) {
		return personaDAO.insertarPersona(persona);
	}

	public List<Persona> mostrarPersona() {
		return personaDAO.findAll();
	}

	public Persona findById(Long codigo) {
		return personaDAO.findById(codigo);
	}

	public boolean isEmailRegistrado(String email) {
		return personaDAO.isEmailRegistrado(email);
	}
}
