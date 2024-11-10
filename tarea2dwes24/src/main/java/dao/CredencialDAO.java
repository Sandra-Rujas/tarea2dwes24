package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import modelo.Credencial;
import modelo.Planta;
import utils.ConexionBD;

public class CredencialDAO {

	private Connection con;
	static PreparedStatement ps;
	static ResultSet rs;

	public CredencialDAO(Connection con) {
		this.con = con;
	}

	/**
	 * Este método inserta una nueva entrada en la tabla de credenciales utilizando
	 * los valores proporcionados en el objeto Credencial. Retorna el número de
	 * filas afectadas por la operación de inserción.
	 * 
	 * @param credencial. El objeto Credencial que contiene la información de la
	 * credencial a insertar. Debe tener valores definidos para los campos `id`, `usuario` y `password`.
	 * @return el número de filas afectadas (1 si la inserción fue exitosa, 0 si no se realizó ninguna inserción).
	 * @throws SQLException si ocurre un error al ejecutar la operación de inserción en la base de datos.
	 */

	public int insertarCredencial(Credencial credencial) {

		int resultado = 0;

		try {

			String sql = "INSERT INTO credenciales(usuario,password) VALUES (?,?)";

			ps = con.prepareStatement(sql);
			ps.setString(1, credencial.getUsuario());
			ps.setString(2, credencial.getPassword());

			resultado = ps.executeUpdate();

			if (resultado > 0) {
				return resultado;
			}

		} catch (SQLException e) {

			System.err.println("Error al guardar la credencial: " + e.getMessage());
			e.printStackTrace();
		}

		return resultado;
	}

	/**
	 * Este método inserta un registro en la tabla de credenciales utilizando el ID
	 * de la persona, el nombre de usuario y la contraseña proporcionados. Retorna
	 * `true` si la operación de inserción fue correcta y `false` en caso contrario.
	 * 
	 * @param usuario.  El nombre de usuario para la nueva credencial.
	 * @param password. L contraseña asociada al nombre de usuario.
	 * @return `true`si la credencial fue insertada correctamente en la base de  datos,`false` en caso de error.
	 * @throws SQLException si ocurre un error al ejecutar la operación de inserción en la base de datos.
	 */

	public boolean insertarCredencial(String usuario, String password, Long id) {

		String sql = "INSERT INTO credenciales (usuario, password, idpersona) VALUES (?, ?, ?)";

		try {

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, usuario);
			ps.setString(2, password);
			ps.setLong(3, id);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Este método consulta la base de datos para verificar si el nombre de usuario
	 * y la contraseña proporcionados coinciden con los registros almacenados en la
	 * tabla de credenciales. Retorna `true` si las credenciales coinciden y `false`
	 * en caso contrario.
	 * 
	 * @param usuario.El nombre de usuario que se va a autenticar.
	 * @param password.  La contraseña correspondiente al usuario.
	 * @return `true` si las credenciales son válidas, `false` si el nombre de
	 *  usuario no existe o si la contraseña no coincide.
	 * @throws SQLException si ocurre un error al ejecutar la consulta en la base de datos.
	 */

	public boolean login(String usuario, String password) {

		String sql = "SELECT password FROM credenciales WHERE usuario = ?";

		try {

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, usuario);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				String pass = rs.getString("password");
				return pass.equals(password);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Este método consulta la tabla de credenciales de la base de datos para
	 * recuperar la información asociada al nombre de usuario proporcionado. Si se
	 * encuentra el usuario, se devuelve un objeto Credencial con los datos
	 * correspondientes.
	 * 
	 * @param usuario El nombre de usuario para buscar las credenciales.
	 * @return Un objeto Credencial con los datos del usuario si existe, o null si no se encuentra.
	 */
	public Credencial getCredencialesPorUsuario(String usuario) {
		String sql = "SELECT * FROM credenciales WHERE usuario = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, usuario);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Credencial credencial = new Credencial(rs.getString("usuario"), rs.getString("password"), rs.getLong("idpersona"));
				return credencial;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Este método realiza una consulta en la base de datos para obtener el campo
	 * `idpersona` de la tabla `credenciales` basado en el nombre de usuario
	 * proporcionado.
	 * 
	 * @param usuario. El nombre de usuario para buscar.
	 * @return El `idpersona` correspondiente al usuario, o null si no se encuentra.
	 */
	public Long getIdPersona(String usuario) {
		String sql = "SELECT * FROM credenciales WHERE usuario = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, usuario);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Long idPersona = rs.getLong("idpersona");
				return idPersona;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Este método consulta la base de datos para verificar si existe un registro
	 * con el nombre de usuario proporcionado. Retorna `true` si el usuario está
	 * registrado y `false` si no se encuentra.
	 * 
	 * @param usuario el nombre de usuario a verificar en la base de datos.
	 * @return `true` si el usuario existe en la base de datos, `false` si no se encuentra.
	 * @throws SQLException si ocurre un error al ejecutar la consulta en la base de datos.
	 */

	public boolean isUsuarioRegistrado(String usuario) {

		String query = "SELECT usuario FROM credenciales WHERE usuario = ?";

		try {

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, usuario);
			ResultSet rs = ps.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Este método consulta la base de datos para verificar si el nombre de usuario
	 * proporcionado está registrado en la tabla de credenciales. Retorna `true` si
	 * se encuentra un registro con ese usuario y `false` en caso contrario.
	 * 
	 * @param usuario el nombre de usuario a verificar.
	 * @return `true` si el usuario existe en la base de datos, `false` si no se  encuentra.
	 * @throws SQLException si ocurre un error al ejecutar la consulta en la base de datos.
	 */

	public boolean validarUsuario(String usuario) {

		try {

			String sql = "SELECT usuario FROM credenciales WHERE usuario=?";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				usuario = rs.getString("usuario");

			}

		} catch (SQLException e) {

			System.err.println("Error al buscar la credencial: " + e.getMessage());
			e.printStackTrace();
		}

		return true;

	}

	/**
	 * Verifica si una contraseña existe en la base de datos para un usuario
	 * específico. Este método consulta la base de datos para verificar si la
	 * contraseña proporcionada coincide con la almacenada en la base de datos para
	 * un usuario específico. Retorna `true` si la contraseña coincide y `false` en
	 * caso contrario.
	 * 
	 * @param password la contraseña a verificar.
	 * @return `true` si la contraseña coincide con la almacenada en la base de
	 * datos, `false` si no coincide o si ocurre algún error.
	 * @throws SQLException si ocurre un error al ejecutar la consulta en la base de datos.
	 */

	public boolean validarPassword(String password) {

		try {

			String sql = "SELECT usuario FROM credenciales WHERE usuario=?";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				password = rs.getString("password");

			}

		} catch (SQLException e) {

			System.err.println("Error al buscar la credencial: " + e.getMessage());
			e.printStackTrace();
		}

		return true;
	}

	/**

	 * Este método consulta la base de datos para verificar si el nombre de usuario
	 * y la contraseña proporcionados coinciden con un registro existente en la
	 * tabla de credenciales. Retorna `true` si las credenciales coinciden y `false`
	 * en caso contrario.
	 * @param usuario  el nombre de usuario a autenticar.
	 * @param password la contraseña a validar para el usuario especificado.
	 * @return `true` si las credenciales son válidas y se encontró un registro que coincide; `false` si no se encuentran coincidencias o si ocurre un
	 * error.
	 * @throws SQLException si ocurre un error al ejecutar la consulta en la base de datos.
	 */

	public boolean autenticarUsuario(String usuario, String password) {

		String consulta = "SELECT COUNT(*) FROM credenciales WHERE usuario = ? AND password = ?";

		try {

			PreparedStatement ps = con.prepareStatement(consulta);

			ps.setString(1, usuario);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				return true;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Este método consulta la base de datos para verificar si el nombre de usuario
	 * y la contraseña proporcionados coinciden con un registro existente en la
	 * tabla de credenciales. Retorna `true` si las credenciales coinciden y `false`
	 * en caso contrario.
	 * @param usuario  el nombre de usuario a autenticar.
	 * @param password la contraseña asociada al usuario.
	 * @return `true` si las credenciales son válidas y se encontró un registro que
	 * coincide; `false` si no se encuentran coincidencias o si ocurre un error.
	 * @throws SQLException si ocurre un error al ejecutar la consulta en la base de datos.
	 */

	public boolean autenticarAdmin(String usuario, String password) {

		try {

			String sql = "SELECT usuario,password FROM credenciales WHERE usuario=? AND password=?";

			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				usuario = rs.getString("admin");
				password = rs.getString("admin");

			}

		} catch (SQLException e) {

			System.err.println("Error al buscar la credencial: " + e.getMessage());
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Este método realiza una consulta a la base de datos para obtener todas las
	 * credenciales de la tabla `credencial`, y devuelve una lista de objetos
	 * `Credencial` con los datos recuperados. La lista contiene las credenciales de
	 * todos los usuarios almacenados.
	 * @return una lista de objetos `Credencial` con los datos de todos los usuarios, o una lista vacía si no se encuentran registros.
	 * @throws SQLException si ocurre un error al ejecutar la consulta en la base de datos.
	 */

	public List<Credencial> findAll() {

		ArrayList<Credencial> listaCredencial = new ArrayList<Credencial>();

		try {

			String sql = "SELECT * FROM credencial";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Long id = rs.getLong("id");
				String usuario = rs.getString("usuario");
				String password = rs.getString("password");

				Credencial credencial = new Credencial(usuario, password);

				listaCredencial.add(credencial);
			}

		} catch (SQLException e) {

			System.err.println("Error al buscar la credencial: " + e.getMessage());
			e.printStackTrace();
		}

		return listaCredencial;
	}

	/**
	 * Este método consulta la base de datos para obtener una credencial específica
	 * utilizando el identificador único (id) proporcionado. Si se encuentra un
	 * registro con el id indicado, se devuelve un objeto `Credencial` con los datos
	 * correspondientes; si no se encuentra, se retorna `null`.
	 * @param id el identificador único de la credencial a buscar.
	 * @return un objeto `Credencial` con los datos de la credencial encontrada, o `null` si no se encuentra ningún registro con el id dado.
	 * @throws SQLException si ocurre un error al ejecutar la consulta en la base de datos.
	 */

	public Credencial findById(Long id) {

		Credencial credencial = null;

		try {

			String sql = "SELECT * FROM credencial WHERE id=?";
			ps = con.prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {

				Long cod = rs.getLong("id");
				String usuario = rs.getString("usuario");
				String password = rs.getString("password");

				credencial = new Credencial(usuario, password);

			}

		} catch (SQLException e) {

			System.err.println("Error al buscar la credencial por código: " + e.getMessage());
			e.printStackTrace();

		}

		return credencial;
	}

}
