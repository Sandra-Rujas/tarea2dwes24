package controlador;

import java.util.List;

import dao.EjemplarDAO;
import modelo.Credencial;
import modelo.Ejemplar;
import modelo.Persona;
import utils.ConexionBD;

public class ServicioEjemplar {
	
	private ConexionBD con;
	private EjemplarDAO ejemplarDAO;
	
	public ServicioEjemplar() {
		con = ConexionBD.getInstance();
		ejemplarDAO = (EjemplarDAO) con.getEjemplarDAO();
	}
	
	public int insertarEjemplar(Ejemplar ejemplar) {
		return ejemplarDAO.insertarEjemplar(ejemplar);
	}
	
	

	public List<Ejemplar> mostrarEjemplaresPorPlanta(String codigoPlanta){
		return ejemplarDAO.buscarEjemplaresPorPlanta(codigoPlanta);
	}
	
	
	public List<Ejemplar> mostrarEjemplares(){
		return ejemplarDAO.findAll();
	}
	
	public Ejemplar findById(Long codigo){
		return ejemplarDAO.findById(codigo);
	}
	
	public static boolean validarEjemplar(Ejemplar e) {
        if(e.getCodigoPlanta().isEmpty() || e.getCodigoPlanta().length()<3 || e.getCodigoPlanta().length()>20) { 
        	return false;
        	}
        return true;
    }
	
	public boolean codigoExistente(Long codigo) {
		return ejemplarDAO.codigoExistente(codigo); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public boolean addEjemplar(Long id, String nombre, String codigoplanta) {
		Ejemplar ejemplar = new Ejemplar(id, nombre, codigoplanta);
		if(ejemplarDAO.insertarEjemplar(ejemplar) == 1) {
			return true;
		}
		return false;
		
	}
	
	public boolean borrarEjemplar(Long id) {
		if(ejemplarDAO.borrarEjemplar(id) == 1) {
			return true;
		}
		return false;
		
	}
	
	public String findById(Long id) {
		Ejemplar ejemplar = ejemplarDAO.findById(id);
		if (ejemplar != null) {
			return ejemplar.getNombre();
		}
		return null;
	}
	
	public List<Ejemplar> mostrarEjemplares(){
		return ejemplarDAO.findAll();
	}*/

}
