package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Credencial;
import modelo.Planta;
import utils.ConexionBD;

public class CredencialDAO {
	

	//Declaración de variables para la consultas.
	static PreparedStatement ps;
	static Connection con;
	ResultSet rs;
	
	
	//Conexión a la base de datos.
	public CredencialDAO(Connection con) {
		this.con = con;
	}
	
	/*Método insertar credencial nueva.
	 * Comprobamos la conexión.
	 * Declaramos una variable int resultado para obtener el total de filas afectadas. 
	 * Devuelve 0 si no se realiza ninguna inserción. En caso de que sea añadida la fila, devuelve 1 ya que el metodo 'executeipdate()' 
	 * devuelve un entero con el total de filas afectadas.
	 * Pedimos por parámetro la credencial y lo añadidos al PrepareStatememt sacando los valores necesarios para la query.
	 * Cerramos conexión BD.
	 * Tratamos las excepciones.
	 */
	public int insertarCredencial(Credencial credencial) {
		
		int resultado = 0;
		
		try {
			
			if (con == null) {
				con = ConexionBD.getConexion();
				}
			
			String sql="INSERT INTO credencial(id,usuario,password) VALUES (?,?,?)";
			
			ps=con.prepareStatement(sql);
			ps.setLong(1, credencial.getId());
			ps.setString(2, credencial.getUsuario());
			ps.setString(3, credencial.getPassword());
			resultado = ps.executeUpdate(sql);
			if(resultado > 0) {
				return resultado;
			}
			con.close();
			
		}catch(SQLException e) {
			
			System.err.println("Error al guardar la credencial: "+e.getMessage());
			e.printStackTrace();
		}
		
		return resultado;
	}

	/*Método borrar credencial segúun el id.
	 * Comprobamos la conexión.
	 *Pedimos por parámetro el id de la credencial que queremos eliminar.
	 *Como en el anterior método, devuelve un entero que representa el número de filas afectadas por la
	 *operación de eliminación. Devuelve un 0 si no se elimina ninguna fila.
	 *Cerramos conexión BD.
	 *Tratamos las excepciones.
	 */
	public int borrarCredencial(Long id) {

		int resultado = 0;
		try {
		
		if (con == null) {
			con = ConexionBD.getConexion();
			}
		
		String sql="DELETE FROM credencial WHERE id=?";		
		ps = con.prepareStatement(sql);
		ps.setLong(1, id);
		resultado = ps.executeUpdate(sql);
		if(resultado > 0) {
			return resultado;
		}		
		con.close();
			
			
		} catch (SQLException e) {
			System.err.println("Error al borrar la credencial: "+e.getMessage());

			e.printStackTrace();
			
		}
		return resultado;
	}
	

	/* Método actualizar credencial según el id.
	 *Comprobamos la conexión con al BD.
	 *Preparamos la query con el metodo prepareStatement añadiendo el id para completarla
	 * Devuelve un entero que representa el número de filas afectadas por la
	 *operación de actualización. Devuelve un 0 si no se actualiza ninguna fila.
	 *Cerramos conexión.
	 *Tratamos las excepciones.
	 */
	public int actualizarCredencial(Long id) {
		int resultado = 0;
		try {
			if (con == null) {
				con = ConexionBD.getConexion();
			}
			
			String sql = "UPDATE FROM credencial WHERE id = ?" ;
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
	
	
	/*Método Login. Se piden por parámetros el usuario y la contraseña. 
	 * Con este método obtenemos la contraseña segun el usuario enviado por parametro y añadido a la query.
	 * Comprobamos que la contraseña del usuario coincide con la introducida por parametro y devolvemos true si es asi o false si no.
	 * Tratamos las excepciones.
	 */
		public boolean login(String usuario, String password) {
		String sql = "SELECT password FROM credencial WHERE usuario = ?";
		
		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, usuario);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String contraseña = rs.getString("password");
				return contraseña.equals(password);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false; 
	}
	
	
	/*Método devolver todas las credenciales.
	 * Creamos la lista de Credenciales.
	 * Comrpobamos la conexión.
	 * Recorremos todas las credenciales obtenemos los valores de cada una y las creamos.
	 *  Añadimos las credenciales a una lista.
	 * Y devuelve esa misma lista.
	 * Tratamos las excepciones. */
		
	public List<Credencial> findAll() {
			ArrayList<Credencial> listaCredencial = new ArrayList<Credencial>();

			try {
				if (con == null) {
					con = ConexionBD.getConexion();
				}	
				String sql="SELECT * FROM credencial";
				ps=con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Long id = rs.getLong("id");
	                String usuario = rs.getString("usuario");
	                String password = rs.getString("password");

	                Credencial credencial = new Credencial(id, usuario, password);

	                listaCredencial.add(credencial);
	            }
	            
	            con.close(); 								
			}catch(SQLException e) {
				System.err.println("Error al buscar la credencial: "+ e.getMessage());
				e.printStackTrace();		
		}
			return listaCredencial;
	}
	

	/*Método devolver todos las credenciales.
	 * Creamos la lista de credenciales.
	 * Comprobamos la conexión.
	 * Recorremos todas las credenciales obtenemos los valores de cada uno y los vamos creando.
	 * Añadimos las credenciales a una lista.
	 * Y devuelve esa misma lista.
	 * Tratamos las excepciones. */
	
	public Credencial findById(Long id) {
		
			Credencial credencial=null;
			
			try {
				
				if (con == null) {
					con = ConexionBD.getConexion();
				}
			
			String sql="SELECT * FROM credencial WHERE id=?";
			ps=con.prepareStatement(sql);
			ps.setLong(1, id);
			rs=ps.executeQuery();
			
			if(rs.next()) {
				
				Long codigo=rs.getLong("id");
				String usuario=rs.getString("usuario");
				String password=rs.getString("password");
				
				credencial=new Credencial(codigo,usuario,password);
				
			}
			con.close();
			
		}catch(SQLException e) {
			System.err.println("Error al buscar la credencial por código: "+ e.getMessage());
			e.printStackTrace();	
			
		} 	
			return credencial;
	}


}
