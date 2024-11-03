package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Planta;
import utils.ConexionBD;

public class PlantaDAO {

	// Declaración de variables para la consultas.
	static PreparedStatement ps;
	static Connection con;
	ResultSet rs;

	// Conexión a la base de datos.
	public PlantaDAO(Connection con) {
		this.con = con;
	}

	/*Método insertar planta nueva.
	 * Comprobamos la conexión.
	 * Declaramos una variable int resultado para obtener el total de filas afectadas. 
	 * Devuelve 0 si no se realiza ninguna inserción. En caso de que sea añadida la fila, devuelve 1 ya que el metodo 'executeipdate()' 
	 * devuelve un entero con el total de filas afectadas.
	 * Pedimos por parámetro la planta y lo añadido al PrepareStatememt sacando los valores necesarios para la query
	 * Cerramos conexión BD.
	 * Tratamos las excepciones.
	 */
	public int insertarPlanta(Planta planta) {
		int resultado = 0;
		try {

			if (con == null || con.isClosed()) {
				con = ConexionBD.getConexion();
			}

			String sql = "INSERT INTO planta(codigo,nombrecomun,nombrecientifico) VALUES (?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, planta.getCodigo());
			ps.setString(2, planta.getNombrecomun());
			ps.setString(3, planta.getNombrecientifico());
			resultado = ps.executeUpdate();
			if (resultado > 0) {
				return resultado;
			}
			con.close();

		} catch (SQLException e) {

			System.err.println("Error al guardar la planta: " + e.getMessage());
			e.printStackTrace();

		}
		return resultado;
	}

	
	/*Método borrar planta según el id.
	 * Comprobamos la conexión.
	 *Pedimos por parámetro el id del ejemplar que queremos eliminar.
	 *Como en el anterior método, devuelve un entero que representa el número de filas afectadas por la
	 *operación de eliminación. Devuelve un 0 si no se elimina ninguna fila.
	 *Cerramos conexión BD.
	 *Tratamos las excepciones.
	 */
	public int borrarPlanta(String codigo) {
		int resultado = 0;
		try {

			if (con == null || con.isClosed()) {
				con = ConexionBD.getConexion();
			}

			String sql = "DELETE FROM planta WHERE codigo=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, codigo);
			resultado = ps.executeUpdate(sql);
			if (resultado > 0) {
				return resultado;
			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return resultado;
	}

	/* Método actualizar planta según el id.
	 *Comprobamos la conexión con al BD.
	 *Preparamos la query con el metodo prepareStatement añadiendo el id para completarla
	 * Devuelve un entero que representa el número de filas afectadas por la
	 *operación de actualización. Devuelve un 0 si no se actualiza ninguna fila.
	 *Cerramos conexión.
	 *Tratamos las excepciones.
	 */
	public int actualizarPlanta(String codigo) {
		int resultado = 0;
		try {
			if (con == null || con.isClosed()) {
				con = ConexionBD.getConexion();
			}

			String sql = "UPDATE FROM planta WHERE id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, codigo);
			resultado = ps.executeUpdate(sql);
			if (resultado > 0) {
				return resultado;
			}
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	/*Método devolver todos las plantas.
	 * Creamos la lista de ejemplares.
	 * Comrpobamos la conexión.
	 * Recorremos todas las plantas obtenemos los valores de cada una y las vamos creando.
	 * Añadimos las plantas a una lista.
	 * Y devuelve esa misma lista.
	 * Tratamos las excepciones. */
	public List<Planta> findAll() {
		ArrayList<Planta> listaPlantas = new ArrayList<Planta>();
		con = null;

		try {
			if (con == null || con.isClosed()) {
				con = ConexionBD.getConexion();
			}
			String sql = "SELECT * FROM planta";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("codigo");
				String nombrecomun = rs.getString("nombrecomun");
				String nombrecientifico = rs.getString("nombrecientifico");

				Planta planta = new Planta(codigo, nombrecomun, nombrecientifico);

				listaPlantas.add(planta);
			}

			con.close();
		} catch (SQLException e) {
			System.err.println("Error al buscar la planta: " + e.getMessage());
			e.printStackTrace();
		}
		return listaPlantas;
	}


	/*Método encontrar planta por id.
	 * Pedimos por parámetro el id.
	 * Comprobamos conexión.
	 * Recorremos la tabla Planta hasta encontrar el id.
	 * Si lo encontramos devolvemos la planta con todos sus atributos.
	 * Cerramos la conexión.
	 * Tratamos las excepciones.
	*/
	public Planta findById(String id) {

		Planta planta = null;
		con = null;
		try {
			if (con == null || con.isClosed()) {
				con = ConexionBD.getConexion();
			}

			String sql = "SELECT * FROM planta WHERE id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {

				String codigo = rs.getString("codigo");
				String nombrecomun = rs.getString("nombrecomun");
				String nombrecientifico = rs.getString("nombrecientifico");

				planta = new Planta(codigo, nombrecomun, nombrecientifico);

			}
			con.close();

		} catch (SQLException e) {
			System.err.println("Error al buscar la planta por código: " + e.getMessage());
			e.printStackTrace();

		}
		return planta;
	}

}
