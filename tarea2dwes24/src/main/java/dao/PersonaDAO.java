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


	private Connection con;
	static PreparedStatement ps;
	static ResultSet rs;
	
	public PersonaDAO(Connection con) {
		this.con = con;

	}

	/**
	 * Inserta una nueva persona en la base de datos.
	 * @param persona Objeto Persona con los datos a insertar.
	 * @return El número de filas afectadas (1 si la inserción fue correcta, 0 si no lo fue).
	 */
	
	public int insertarPersona(Persona persona) {
		int resultado = 0;
		String sql = "INSERT INTO personas (nombre, email) VALUES (?, ?)";
		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, persona.getNombre());
			ps.setString(2, persona.getEmail());
			
	        resultado = ps.executeUpdate();
			 if (resultado > 0) {
				 	/*REcuperamos el ID de la persona*/ 
		            String sqlGetId = "SELECT LAST_INSERT_ID()"; 
		            PreparedStatement psGetId = con.prepareStatement(sqlGetId);
		            ResultSet rs = psGetId.executeQuery();
		            if (rs.next()) {
		                persona.setId(rs.getLong(1)); 
		            }
		            return resultado;  
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		return resultado;

	}

	/**
	 * Recupera todas las personas de la base de datos.
	 * @return Una lista de objetos Persona con los datos de todas las personas.
	 */
	
	public List<Persona> findAll() {
		ArrayList<Persona> listaPersonas = new ArrayList<Persona>();

		try {

			String sql = "SELECT * FROM personas";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String nombre = rs.getString("nombre");
				String email = rs.getString("email");
				Persona persona = new Persona(id, nombre, email);
				
				listaPersonas.add(persona);
				
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar la persona: " + e.getMessage());
			e.printStackTrace();
		}
		return listaPersonas;
	}

	/**
	 * Busca una persona en la base de datos por su ID.
	 * @param id El ID de la persona a buscar.
	 * @return Un objeto Persona con los datos de la persona encontrada, o null si no se encuentra.
	 */
	public Persona findById(Long id) {

		Persona persona = null;
		try {

			String sql = "SELECT * FROM personas WHERE id=?";
			ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {

				Long codigo = rs.getLong("id");
				String nombre = rs.getString("nombre");
				String email = rs.getString("email");
				String usuario = rs.getString("usuario");
				String password = rs.getString("password");

				persona = new Persona(nombre, email);

			}

		} catch (SQLException e) {
			System.err.println("Error al buscar la persona por código: " + e.getMessage());
			e.printStackTrace();

		}
		return persona;
	}

	/**
	 * Verifica si un email ya está registrado en la base de datos.
	 * @param email El email que se va a verificar.
	 * @return true si el email está registrado, false si no lo está.
	 */

	public boolean isEmailRegistrado(String email) {

		String sql = "SELECT email FROM personas WHERE email = ?";

		try {
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();
			return rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
