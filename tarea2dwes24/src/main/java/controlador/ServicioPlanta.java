package controlador;

import java.util.List;

import dao.PlantaDAO;
import modelo.Planta;
import utils.ConexionBD;

public class ServicioPlanta {
	
	private PlantaDAO plantaDAO;
	private ConexionBD con;
	
	public ServicioPlanta() {
		
		con = ConexionBD.getInstance();
		plantaDAO = (PlantaDAO) con.getPlantaDAO();
	}
		
	public int insertarPlanta(Planta planta) {
		return plantaDAO.insertarPlanta(planta);
	}
	
	
	public int modificarPlanta(Planta planta) {
		return plantaDAO.actualizarPlanta(planta);
	}	
	
	
	public List<Planta> mostrarPlantas(){
		return plantaDAO.mostrarPlantas();
	}
	
	public boolean validarPlanta(Planta planta) {
		return plantaDAO.validarPlanta(planta);
	}
	
	public boolean codigoExistente(String codigo) {
		return plantaDAO.codigoExistente(codigo); 
	}
	
	

}
