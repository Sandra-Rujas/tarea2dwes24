package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Credencial;
import modelo.Persona;
import utils.ConexionBD;

public class PersonaDAO {
	
	//Declaración de variables para la consultas.
	 PreparedStatement ps;
	 Connection con;
	 ResultSet rs;
	
	
	//Conexión a la base de datos.
	public PersonaDAO(Connection con) {
		this.con = con;
	}
	
	/*Método insertar persona nueva.
	 * Comprobamos la conexión.
	 * Declaramos una variable int resultado para obtener el total de filas afectadas. 
	 * Devuelve 0 si no se realiza ninguna inserción.
	 * Pedimos por parámetro la persona y lo añadido al PrepareStatememt sacando los valores necesarios para la query
	 * Cerramos conexión BD.
	 * Tratamos las excepciones.
	 */
	public int insertarPersona(Persona persona) {
		int resultado = 0;
		try {
			if (con == null) {
				con = ConexionBD.getConexion();
				}
			
			String sql="INSERT INTO persona(id,nombre,email,credencial) VALUES (?,?,?,?)";

			
			ps=con.prepareStatement(sql);
			ps.setLong(1, persona.getId());
			ps.setString(2, persona.getNombre());
			ps.setString(3, persona.getEmail());
			ps.setLong(4, persona.getIdCredencial());
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

	/*Método borrar persona según el id.
	 * Comprobamos la conexión.
	 *Pedimos por parámetro el id del ejemplar que queremos eliminar.
	 *Como en el anterior método, devuelve un entero que representa el número de filas afectadas por la
	 *operación de eliminación. Devuelve un 0 si no se elimina ninguna fila.
	 *Cerramos conexión BD.
	 *Tratamos las excepciones.
	 */
	public int borrarPersona(Long id) {
		int resultado = 0;
		try {
		
		if (con == null) {
			con = ConexionBD.getConexion();
			}
		
		String sql="DELETE FROM persona WHERE id=?";		
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
	

	/* Método actualizar persona según el id.
	 *Comprobamos la conexión con al BD.
	 *Preparamos la query con el metodo prepareStatement añadiendo el id para completarla
	 * Devuelve un entero que representa el número de filas afectadas por la
	 *operación de actualización. Devuelve un 0 si no se actualiza ninguna fila.
	 *Cerramos conexión.
	 *Tratamos las excepciones.
	 */	public int actualizarCredencial(Long id) {
		int resultado = 0;
		try {
			if (con == null) {
				con = ConexionBD.getConexion();
			}
			
			String sql = "UPDATE FROM persona WHERE id = ?" ;
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
	
	
	 /*Método devolver todos las personas.
		 * Creamos la lista de personas.
		 * Comprobamos la conexión.
		 * Recorremos todas las personas obtenemos los valores de cada una y las vamos creando.
		 * Añadimos las personas a una lista.
		 * Y devuelve esa misma lista.
		 * Tratamos las excepciones. */
	public List<Persona> findAll() {
			ArrayList<Persona> listaPersonas = new ArrayList<Persona>();
			con = null;
			
			try {
				if (con == null) {
					con = ConexionBD.getConexion();
				}	
				String sql="SELECT id, nombre, email, credencial FROM persona";
				ps=con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Long id = rs.getLong("id");
	                String nombre = rs.getString("nombre");
	                String email = rs.getString("email");
	                Long credencial = rs.getLong("credencial"); 

	                Persona persona = new Persona(id, nombre, email, credencial);

	                listaPersonas.add(persona);
	            }
	            con.close(); 	
	            
			}catch(SQLException e) {
				System.err.println("Error al buscar la persona: "+ e.getMessage());
				e.printStackTrace();		
		}
			return listaPersonas;
	}
	

	/*Método encontrar persona por id.
	 * Pedimos por parámetro el id.
	 * Comprobamos conexión.
	 * Recorremos la tabla Persona hasta encontrar el id.
	 * Si lo encontramos devolvemos la persona con todos sus atributos.
	 * Cerramos la conexión.
	 * Tratamos las excepciones.
	*/
	public Persona findById(Long id) {
		
			Persona persona=null;
			con = null;
			try {
				if (con == null) {
					con = ConexionBD.getConexion();
				}
			
			String sql="SELECT * FROM persona WHERE id=?";
			ps=con.prepareStatement(sql);
			ps.setLong(1, id);
			rs=ps.executeQuery();
			
			if(rs.next()) {
				
				Long codigo=rs.getLong("id");
				String nombre=rs.getString("nombre");
				String email=rs.getString("email");
				Long credencial = rs.getLong("credencial"); // ???
				
				persona=new Persona(codigo,nombre,email, credencial);
				
			}
			con.close();
			
		}catch(SQLException e) {
			System.err.println("Error al buscar la persona por código: "+ e.getMessage());
			e.printStackTrace();	
			
		} 	
			return persona;
	}


}
