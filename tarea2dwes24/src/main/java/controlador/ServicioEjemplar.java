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
	
	public int findById(Long codigo){
		return ejemplarDAO.findById(codigo);
	}

	
	public boolean codigoExistente(Long codigo) {
		return ejemplarDAO.codigoExistente(codigo); 
	}
	

}
