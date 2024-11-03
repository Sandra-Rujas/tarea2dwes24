package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Ejemplar;
import modelo.Mensaje;
import modelo.Persona;
import modelo.Planta;
import utils.ConexionBD;

public class MensajeDAO {

	//Declaración de variables para la consultas.
	static PreparedStatement ps;
	static Connection con;
	ResultSet rs;
	
	
	//Conexión a la base de datos.
	public MensajeDAO(Connection con) {
		this.con = con;
	}
	
	/*Método insertar mensaje nuevo.
	 * Comprobamos la conexión.
	 * Declaramos una variable int resultado para obtener el total de filas afectadas. 
	 * Devuelve 0 si no se realiza ninguna inserción. En caso de que sea añadida la fila, devuelve 1 ya que el metodo 'executeipdate()' 
	 * devuelve un entero con el total de filas afectadas.
	 * Pedimos por parámetro el mensaje y lo añadido al PrepareStatememt sacando los valores necesarios para la query
	 * Cerramos conexión BD.
	 * Tratamos las excepciones.*/
	public int insertarMensaje(Mensaje mensaje) {
		
		int resultado = 0;
		try {
			
			if (con == null) {
				con = ConexionBD.getConexion();
				}
			
			String sql="INSERT INTO planta(id,mensaje,idEjemplar,idPersona) VALUES (?,?,?,?)";

			
			ps=con.prepareStatement(sql);
			ps.setLong(1, mensaje.getId());
			ps.setString(2, mensaje.getMensaje());
			ps.setLong(3, mensaje.getIdEjemplar());
			ps.setLong(4, mensaje.getIdPersona());
			
			resultado = ps.executeUpdate(sql);
			if(resultado > 0) {
				return resultado;
			}
			con.close();
			
		}catch(SQLException e) {
			
			System.err.println("Error al guardar el mensaje: "+e.getMessage());
			e.printStackTrace();
			
			
		}
		return resultado;
	}


	/*Método borrar mensaje según el id.
	 * Comprobamos la conexión.
	 *Pedimos por parámetro el id del ejemplar que queremos eliminar.
	 *Como en el anterior método, devuelve un entero que representa el número de filas afectadas por la
	 *operación de eliminación. Devuelve un 0 si no se elimina ninguna fila.
	 *Cerramos conexión BD.
	 *Tratamos las excepciones.
	 */
	public int borrarMensaje(String codigo) {

		int resultado = 0;
		try {
		
		if (con == null) {
			con = ConexionBD.getConexion();
			}
		
		String sql="DELETE FROM mensaje WHERE codigo=?";		
		ps = con.prepareStatement(sql);
		ps.setString(1, codigo);
		resultado = ps.executeUpdate(sql);
		if(resultado > 0) {
			return resultado;
		}
		con.close();
			
			
		} catch (SQLException e) {
			System.err.println("Error al borrar el mensaje: "+e.getMessage());
			e.printStackTrace();
			
		}
		return resultado;
	}
	


	/* Método actualizar mensaje según el id.
	 *Comprobamos la conexión con al BD.
	 *Preparamos la query con el metodo prepareStatement añadiendo el id para completarla
	 * Devuelve un entero que representa el número de filas afectadas por la
	 *operación de actualización. Devuelve un 0 si no se actualiza ninguna fila.
	 *Cerramos conexión.
	 *Tratamos las excepciones.
	 */
	public int actualizarMensaje(String codigo) {
		int resultado = 0;
		try {
			if (con == null) {
				con = ConexionBD.getConexion();
			}
			
			String sql = "UPDATE FROM mensaje WHERE id = ?" ;
			ps = con.prepareStatement(sql);
			ps.setString(1, codigo);
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
	
	
	
	
	/*Método devolver todos los mensajes.
	 * Creamos la lista de mensajes.
	 * Comprobamos la conexión.
	 * Recorremos todas los mensajes obtenemos los valores de cada uno y los vamos creando.
	 * Añadimos los mensajes a una lista.
	 * Y devuelve esa misma lista.
	 * Tratamos las excepciones. */
	public List<Mensaje> findAll() {
			ArrayList<Mensaje> listaMensajes = new ArrayList<Mensaje>();
			con = null;
			
			try {
				if (con == null) {
					con = ConexionBD.getConexion();
				}	
				String sql="SELECT id, mensaje, idEjemplar, idPersona FROM mensaje";
				ps=con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Long id = rs.getLong("id");
	                String men = rs.getString("mensaje");
	                Long idEjemplar = rs.getLong("idEjemplar");
	                Long idPersona = rs.getLong("idPersona");


	                Mensaje mensaje = new Mensaje(id, men, idEjemplar, idPersona);

	                listaMensajes.add(mensaje);
	            }
	            
	            con.close(); 								
			}catch(SQLException e) {
				System.err.println("Error al buscar el mensaje: "+ e.getMessage());
				e.printStackTrace();		
		}
			return listaMensajes;
	}
	

	/*Método encontrar mensaje por id.
	 * Pedimos por parámetro el id.
	 * Comprobamos conexión.
	 * Recorremos la tabla Mensaje hasta encontrar el id.
	 * Si lo encontramos devolvemos el mensaje con todos sus atributos.
	 * Cerramos la conexión.
	 * Tratamos las excepciones.
	*/
	public Mensaje findById(Long id) {
		
			Mensaje mensaje=null;
			con = null;
			try {
				if (con == null) {
					con = ConexionBD.getConexion();
				}
			
			String sql="SELECT * FROM mensaje WHERE id=?";
			ps=con.prepareStatement(sql);
			ps.setLong(1, id);
			rs=ps.executeQuery();
			
			if(rs.next()) {
				
				Long codigo=rs.getLong("id");
				String men=rs.getString("mensaje");
				Long idEjemplar = rs.getLong("idEjemplar");
                Long idPersona = rs.getLong("idPersona");
				
				mensaje=new Mensaje(codigo,men,idEjemplar,idPersona);
				
			}
			con.close();
			
		}catch(SQLException e) {
			System.err.println("Error al buscar el mensaje por código: "+ e.getMessage());
			e.printStackTrace();	
			
		} 	
			return mensaje;
	}


}
