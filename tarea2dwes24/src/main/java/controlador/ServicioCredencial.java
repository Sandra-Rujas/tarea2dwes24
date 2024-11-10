package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import dao.CredencialDAO;
import modelo.Credencial;
import modelo.Persona;
import utils.ConexionBD;


public class ServicioCredencial {
		
		private ConexionBD con;
		private CredencialDAO credencialDAO;
		
		public ServicioCredencial() {
			con = ConexionBD.getInstance();
			credencialDAO = (CredencialDAO) con.getCredencialDAO();
		}
		
		public int insertarCredencial(Credencial credencial) {
			return credencialDAO.insertarCredencial(credencial);
		}
		
		public boolean insertarCredencial(String usuario, String password, Long id) {
			return credencialDAO.insertarCredencial(usuario, password, id);
		}
		
		public List<Credencial> mostrarPlantas(){
			return credencialDAO.findAll();
		}
		
		public Credencial findById(Long codigo){
			return credencialDAO.findById(codigo);
		}
		
		public Credencial getCredencialesPorUsuario(String usuario) {
			return credencialDAO.getCredencialesPorUsuario(usuario);
		}
		
		public boolean autenticarAdmin(String usuario,String password) {
			
			return credencialDAO.autenticarAdmin(usuario,password);
		}
		
		public boolean autenticarUsuario(String usuario, String password) {
			
			return credencialDAO.autenticarUsuario(usuario, password);
		}
		
		public boolean validarPassword(String password)  {
			
			return credencialDAO.validarPassword(password);
		}
		
		public boolean login(String usuario, String password) {
			return credencialDAO.login(usuario, password);
		}
		
		public boolean existeUsuario(String nombreUsuario) {
			return credencialDAO.isUsuarioRegistrado(nombreUsuario);
	
			}
		
		public Long getIdCredenciales(String usuario) {
			Credencial credencial = credencialDAO.getCredencialesPorUsuario(usuario);
			return credencial.getId();
	
		}
		
		public Long getIdPersona(String usuario) {
			return credencialDAO.getIdPersona(usuario);
		}
		
	}
	
