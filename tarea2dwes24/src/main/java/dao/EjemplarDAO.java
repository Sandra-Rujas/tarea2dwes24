package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Ejemplar;
import utils.ConexionBD;

public class EjemplarDAO {
	

	private Connection con;
	static PreparedStatement ps;
	static ResultSet rs;
	

	public EjemplarDAO(Connection con) {
		this.con = con;
	}
	
	/**
	 * Inserta un ejemplar en la base de datos y actualiza su nombre concatenando el id y el código de planta.
	 * @param ejemplar. Contiene el código de la planta del ejemplar a insertar.
	 * @return El número de filas afectadas por la inserción (1 si fue exitosa, 0 si no).
	 * @throws SQLException Si ocurre un error durante la ejecución de las consultas SQL.
	 */
	
	public int insertarEjemplar(Ejemplar ejemplar) {
		
		int resultado = 0;
		
		try {
			
			String sql="INSERT INTO ejemplares(idplanta) VALUES (?)";
			
			ps=con.prepareStatement(sql);
			ps.setString(1, ejemplar.getCodigoPlanta());
			resultado = ps.executeUpdate();
			
			if(resultado > 0) {
				
				/*Actualizamos el nombre del ejemplar*/
				String sqlActualizarNombre = "UPDATE ejemplares SET nombre = CONCAT(id, '_', UPPER(idplanta)) WHERE id = LAST_INSERT_ID()";
	            PreparedStatement psActualizar = con.prepareStatement(sqlActualizarNombre);
	            psActualizar.executeUpdate();
	            
	            /*Recuperamos el id del ejemplar*/
	            String sqlGetId = "SELECT LAST_INSERT_ID()"; 
	            PreparedStatement psGetId = con.prepareStatement(sqlGetId);
	            ResultSet rs = psGetId.executeQuery();
	            if (rs.next()) {
	                ejemplar.setId(rs.getLong(1)); 
	            }
				return resultado;
			}
			
		}catch(SQLException e) {
			
			System.err.println("Error al guardar el ejemplar: "+e.getMessage());
			e.printStackTrace();
		}
		return resultado;
	}
	
	/**
	 * Recupera todos los ejemplares de la base de datos.
	 * Luego, por cada registro, crea un objeto ejemplar y lo agrega a una lista de ejemplares.
	 * @return Una lista de objetos Ejemplar que contiene todos los ejemplares recuperados de la base de datos.
	 * Si no se encuentran ejemplares, se devuelve una lista vacía.
	 * @throws SQLException Si ocurre un error durante la ejecución de la consulta SQL.
	 */
	
	public List<Ejemplar> findAll() {
		
			ArrayList<Ejemplar> listaEjemplares = new ArrayList<Ejemplar>();
			
			try {
					
				String sql="SELECT *  FROM ejemplares";
				ps=con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
	            while (rs.next()) {	
	            	Long id = rs.getLong("id");
	            	String nombre = rs.getNString("nombre");
	                String codigoPlanta = rs.getString("idplanta");
	                Ejemplar ejemplar = new Ejemplar(id, nombre, codigoPlanta);

	                listaEjemplares.add(ejemplar);
	            }
	            
			}catch(SQLException e) {
				
				System.err.println("Error al buscar el ejemplar: "+ e.getMessage());
				e.printStackTrace();		
		}
			return listaEjemplares;
	}
	
	
	/**
	 * Este método realiza una consulta SQL para obtener todos los ejemplares cuya columna idplanta
	 * coincida con el código de la planta proporcionado como parámetro. Devuelve una lista de objetos Ejemplar.
	 * que corresponden a los ejemplares asociados a la planta indicada.
	 * @param codigo. El código de la planta para la cual se buscan los ejemplares.
	 * @return Una lista de objetos Ejemplar que corresponden a los ejemplares de la planta
	 * indicada. Si no se encuentran ejemplares para esa planta, se devuelve una lista vacía.
	 * @throws SQLException Si ocurre un error durante la ejecución de la consulta SQL.
	 */
	
		public List<Ejemplar> buscarEjemplaresPorPlanta(String codigo) {
			
		ArrayList<Ejemplar> listaEjemplaresPorPlanta = new ArrayList<Ejemplar>();
		
		try {
			
			String sql="SELECT *  FROM ejemplares WHERE idplanta = ?";
			
			ps=con.prepareStatement(sql);
			ps.setString(1, codigo);
			
			ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	
                Long id = rs.getLong("id");
                String nombre = rs.getString("nombre");
                String codigoPlanta = rs.getString("idplanta");
                Ejemplar ejemplar = new Ejemplar(id, nombre, codigoPlanta);

                listaEjemplaresPorPlanta.add(ejemplar);
                
            }
            
		}catch(SQLException e) {
			
			System.err.println("Error al buscar el ejemplar: "+ e.getMessage());
			e.printStackTrace();		
	}
		return listaEjemplaresPorPlanta;
}
	

		/**
		 * Este método ejecuta una consulta SQL para buscar un ejemplar en la tabla ejemplares utilizando su id.
		 * Si el ejemplar existe, devuelve su id. En caso contrario, devuelve 0.
		 * @param id. El id del ejemplar a buscar.
		 * @return El id del ejemplar encontrado, o 0 si no se encuentra ningún ejemplar con el id dado.
		 * @throws SQLException Si ocurre un error durante la ejecución de la consulta SQL.
		 */
		
	public int findById(Long id) {
		
			int resultado = 0; 
			try {
				
				String sql="SELECT * FROM ejemplares WHERE id=?";
				ps=con.prepareStatement(sql);
				ps.setLong(1, id);
				rs=ps.executeQuery();
				
				if(rs.next()) {
		            resultado = rs.getInt("id");
				}
			
			}catch(SQLException e) {
			
			System.err.println("Error al buscar la persona por código: "+ e.getMessage());
			e.printStackTrace();			
			} 	
			return resultado;
	}
	/**
	 * Este método ejecuta una consulta SQL para verificar si hay un ejemplar en la tabla ejemplares
	 * con el id specificado. Devuelve true si el ejemplar existe, o false si no se encuentra.
	 * @param codigo El id del ejemplar que se desea verificar.
	 * @return true si el ejemplar con el {@code id} proporcionado existe en la base de datos; false en caso contrario.
	 * @throws SQLException Si ocurre un error durante la ejecución de la consulta SQL.
	 */
	
	public boolean codigoExistente(Long codigo) {
		 String sql = "SELECT id FROM ejemplares WHERE id = ?";
		    
		    try  {
		    	PreparedStatement ps = con.prepareStatement(sql);
		        ps.setLong(1, codigo);
		        
		        try (ResultSet rs = ps.executeQuery()) {
		            return rs.next(); 
		        }
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    return false; 
	}


}



