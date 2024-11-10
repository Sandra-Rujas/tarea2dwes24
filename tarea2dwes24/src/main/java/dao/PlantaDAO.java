package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import modelo.Planta;
import utils.ConexionBD;

public class PlantaDAO {
	
	private Connection con;
	static PreparedStatement ps;
	static ResultSet rs;

	
	public PlantaDAO(Connection con) {
		this.con = con;

	}

	
	/**
	 * Este método inserta una planta con los detalles proporcionados en la tabla `plantas`.
	 * Devuelve el número de filas afectadas por la operación (debería ser 1 si la inserción es exitosa).
	 * @param planta Objeto de tipo `Planta` que contiene los datos de la planta a insertar.
	 * @return El número de filas afectadas por la inserción (1 si se inserta correctamente, 0 si falla).
	 */
	public int insertarPlanta(Planta planta) {
		int resultado = 0;
		try {
			
			String sql = "INSERT INTO plantas(codigo,nombrecomun,nombrecientifico) VALUES (?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, planta.getCodigo());
			ps.setString(2, planta.getNombrecomun());
			ps.setString(3, planta.getNombrecientifico());
			resultado = ps.executeUpdate();
			
			if (resultado > 0) {
				return resultado;
			}

		} catch (SQLException e) {

			System.err.println("Error al guardar la planta: " + e.getMessage());
			e.printStackTrace();

		}
		return resultado;
	}

	
	
	/**
	 * Este método realiza una consulta a la base de datos para contar las plantas que tienen
	 * el mismo código que la planta proporcionada. Si la consulta devuelve un valor mayor a 0,
	 * se considera que la planta existe.
	 * @param planta La planta cuyo código se quiere verificar.
	 * @return `true` si la planta existe en la base de datos, `false` en caso contrario.
	 */
	public boolean validarPlanta(Planta planta) {
	    boolean existe = false;

	    try {
	        String sql = "SELECT COUNT(*) FROM plantas WHERE codigo = ?";
	        ps = con.prepareStatement(sql);
	        ps.setString(1, planta.getCodigo());

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                existe = (count > 0); 
	            }
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al validar la planta: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return existe;
	}
	
	

	/**
	 * Este método actualiza los campos `nombrecomun` y `nombrecientifico` de la tabla `plantas`
	 * para la planta cuyo `codigo` coincide con el código proporcionado. Si la actualización es exitosa,
	 * se retorna el número de filas afectadas. Si no se realizaron cambios, se retorna 0.
	 * @param planta La planta que contiene los nuevos datos a actualizar.
	 * @return El número de filas afectadas por la actualización. Un valor mayor que 0 indica éxito.
	 */
	public int actualizarPlanta(Planta planta) {
		int resultado = 0;
		try {

			String sql = "UPDATE plantas SET nombrecomun = ?, nombrecientifico = ? WHERE codigo = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, planta.getNombrecomun());
			ps.setString(2, planta.getNombrecientifico());
			ps.setString(3, planta.getCodigo());
			resultado = ps.executeUpdate();
			
			if (resultado > 0) {
				return resultado;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	
	/**
	 * Este método ejecuta una consulta SQL para obtener todas las filas de la tabla `plantas`. 
	 * Por cada fila recuperada, se crea un objeto de tipo `Planta` con los datos correspondientes 
	 * (`codigo`, `nombrecomun`, `nombrecientifico`) y se agrega a una lista. 
	 * La lista de objetos `Planta` es devuelta como resultado.
	 * @return Una lista de objetos `Planta` que contiene todos los registros de la tabla `plantas` 
	 * en la base de datos. Si ocurre un error, la lista estará vacía.
	 */
	public List<Planta> mostrarPlantas() {
		ArrayList<Planta> listaPlantas = new ArrayList<Planta>();
		try {
			
			String sql = "SELECT * FROM plantas";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("codigo");
				String nombrecomun = rs.getString("nombrecomun");
				String nombrecientifico = rs.getString("nombrecientifico");

				Planta planta = new Planta(codigo, nombrecomun, nombrecientifico);

				listaPlantas.add(planta);
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar la planta: " + e.getMessage());
			e.printStackTrace();
		}
		return listaPlantas;
		

	}
	
	/**
	 * Este método busca en la tabla `plantas` si existe una planta con el código proporcionado.
	 * @param codigo El código de la planta a verificar.
	 * @return `true` si el código ya existe en la base de datos, `false` en caso contrario.
	 */
	public boolean codigoExistente(String codigo) {
		 String sql = "SELECT codigo FROM plantas WHERE codigo = ?";
		    
		    try  {
		    	PreparedStatement ps = con.prepareStatement(sql);
		        ps.setString(1, codigo.toUpperCase());
		    	ResultSet rs = ps.executeQuery();
		        return rs.next(); 
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    return false; 
	}


}
