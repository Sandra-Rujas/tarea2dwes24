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
	

	//Declaración de variables para la consultas.
	 PreparedStatement ps;
	 Connection con;
	 ResultSet rs;
	
	
	//Conexión a la base de datos.
	public EjemplarDAO(Connection con) {
		this.con = con;
	}
	
	/*Método insertar ejemplar nuevo.
	 * Comprobamos la conexión.
	 * Declaramos una variable int resultado para obtener el total de filas afectadas. 
	 * Devuelve 0 si no se realiza ninguna inserción. En caso de que sea añadida la fila, devuelve 1 ya que el metodo 'executeipdate()' 
	 * devuelve un entero con el total de filas afectadas.
	 * Pedimos por parámetro el ejemplar y lo añadido al PrepareStatememt sacando los valores necesarios para la query
	 * Cerramos conexión BD.
	 * Tratamos las excepciones.
	 */
	public int insertarEjemplar(Ejemplar ejemplar) {
		int resultado = 0;
		try {
			if (con == null) {
				con = ConexionBD.getConexion();
				}
			
			String sql="INSERT INTO ejemplar(id,nombre,email,credencial) VALUES (?,?,?,?)";

			
			ps=con.prepareStatement(sql);
			ps.setLong(1, ejemplar.getId());
			ps.setString(2, ejemplar.getNombre());
			ps.setString(3, ejemplar.getCodigoPlanta());
			resultado = ps.executeUpdate();
			if(resultado > 0) {
				return resultado;
			}
			con.close();
			
		}catch(SQLException e) {
			
			System.err.println("Error al guardar la persona: "+e.getMessage());
			e.printStackTrace();
		}
		return resultado;
	}

	/*Método borrar ejemplar según el id.
	 * Comprobamos la conexión.
	 *Pedimos por parámetro el id del ejemplar que queremos eliminar.
	 *Como en el anterior método, devuelve un entero que representa el número de filas afectadas por la
	 *operación de eliminación. Devuelve un 0 si no se elimina ninguna fila.
	 *Cerramos conexión BD.
	 *Tratamos las excepciones.
	 */
	public int borrarEjemplar(Long id) {
		int resultado = 0;
		try {
		
		if (con == null) {
			con = ConexionBD.getConexion();
			}
		
		String sql="DELETE FROM ejemplar WHERE id=?";		
		ps = con.prepareStatement(sql);
		ps.setLong(1, id);
		resultado = ps.executeUpdate(sql);
		if(resultado > 0) {
			return resultado;
		}
		con.close();
			
			
		} catch (SQLException e) {
			System.err.println("Error al borrar la persona: "+e.getMessage());

			e.printStackTrace();
			
		}
		return resultado;
	}
	

	/* Método actualizar ejemplar según el id.
	 *Comprobamos la conexión con al BD.
	 *Preparamos la query con el metodo prepareStatement añadiendo el id para completarla
	 * Devuelve un entero que representa el número de filas afectadas por la
	 *operación de actualización. Devuelve un 0 si no se actualiza ninguna fila.
	 *Cerramos conexión.
	 *Tratamos las excepciones.
	 */
	public int actualizarEjemplar(Long id) {
		int resultado = 0;
		con = null;
		try {
			if (con == null) {
				con = ConexionBD.getConexion();
			}
			
			String sql = "UPDATE FROM ejemplar WHERE id = ?" ;
			ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			resultado = ps.executeUpdate(sql);
			if(resultado > 0) {
				return resultado;
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	
	/*Método devolver todos los ejemplares.
	 * Creamos la lista de ejemplares.
	 * Comprobamos la conexión.
	 * Recorremos todas los ejemplares obtenemos los valores de cada uno y los vamos creando.
	 * Añadimos los ejemplares a una lista.
	 * Y devuelve esa misma lista.
	 * Tratamos las excepciones. */
	public List<Ejemplar> findAll() {
			ArrayList<Ejemplar> listaEjemplares = new ArrayList<Ejemplar>();
			con = null;
			
			try {
				if (con == null) {
					con = ConexionBD.getConexion();
				}	
				String sql="SELECT *  FROM ejemplar";
				ps=con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Long id = rs.getLong("id");
	                String nombre = rs.getString("nombre");
	                String codigoPlanta = rs.getString("codigoPlanta");

	                Ejemplar ejemplar = new Ejemplar(id, nombre, codigoPlanta);

	                listaEjemplares.add(ejemplar);
	            }
	            con.close(); 	
	            
			}catch(SQLException e) {
				System.err.println("Error al buscar la persona: "+ e.getMessage());
				e.printStackTrace();		
		}
			return listaEjemplares;
	}
	

	/*Método encontrar ejemplar por id.
	 * Pedimos por parámetro el id.
	 * Comprobamos conexión.
	 * Recorremos la tabla Ejemplar hasta encontrar el id.
	 * Si lo encontramos devolvemos el ejemplar con todos sus atributos.
	 * Cerramos la conexión.
	 * Tratamos las excepciones.
	*/
	public Ejemplar findById(Long id) {
		
			Ejemplar ejemplar=null;
			try {
				if (con == null) {
					con = ConexionBD.getConexion();
				}
			
			String sql="SELECT * FROM ejemplar WHERE id=?";
			ps=con.prepareStatement(sql);
			ps.setLong(1, id);
			rs=ps.executeQuery();
			
			if(rs.next()) {
				
				Long codigo=rs.getLong("id");
				String nombre=rs.getString("nombre");
				String codigoPlanta=rs.getString("codigoPlanta");
				
				ejemplar=new Ejemplar(codigo,nombre,codigoPlanta);
				
			}
			con.close();
			
		}catch(SQLException e) {
			System.err.println("Error al buscar la persona por código: "+ e.getMessage());
			e.printStackTrace();	
			
		} 	
			return ejemplar;
	}


}



