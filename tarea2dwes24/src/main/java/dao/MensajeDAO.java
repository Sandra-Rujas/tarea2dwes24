package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modelo.Ejemplar;
import modelo.Mensaje;
import modelo.Persona;
import modelo.Planta;
import utils.ConexionBD;

public class MensajeDAO {

	
	private Connection con;
	static PreparedStatement ps;
	static ResultSet rs;


	public MensajeDAO(Connection con) {
		this.con = con;
	}

	
	/**
	 * Este método ejecuta una consulta SQL para insertar un mensaje en la tabla {@code mensajes}. 
	 * El mensaje se asocia con un ejemplar y una persona, además de registrar la fecha y hora del mensaje.
	 * @param mensaje El objeto Mensaje que contiene los detalles del mensaje a insertar. 
	 * El mensaje incluye el contenido del mensaje, el idEjemplar y el idPersona..
	 * @return Un valor entero que indica el número de filas afectadas por la inserción. 
	 *Si la inserción es correcta, devuelve el número de filas insertadas, un 1.
	 *Si ocurre algún error o la inserción no es correcta, devuelve 0.
	 * @throws SQLException Si ocurre un error durante la ejecución de la consulta SQL, como problemas de conexión o de sintaxis.
	 */
	public int insertarMensaje(Mensaje mensaje) {

		int resultado = 0;
		try {

			String sql = "INSERT INTO mensajes(mensaje,fechahora,idejemplar,idpersona) VALUES (?,?,?,?)";

			ps = con.prepareStatement(sql);
			ps.setString(1, mensaje.getMensaje());
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.setLong(3, mensaje.getIdEjemplar());
			ps.setLong(4, mensaje.getIdPersona());

			resultado = ps.executeUpdate();
			if (resultado > 0) {
				return resultado;
			}

		} catch (SQLException e) {

			System.err.println("Error al guardar el mensaje: " + e.getMessage());
			e.printStackTrace();

		}
		return resultado;
	}

	
	/**
	 * Este método ejecuta una consulta SQL para obtener todos los registros de la tabla mensajes
	 * y los mapea a objetos Mensaje. Además, para cada mensaje recuperado, 
	 * se obtiene el id del mensaje insertado recientemente y se asigna la fecha y hora 
	 * en la que se insertó el mensaje.
	 * @return Una lista de objetos Mensaje que contienen los mensajes registrados en la base de datos.  Si no hay mensajes, la lista estará vacía.
	 * @throws SQLException Si ocurre un error durante la ejecución de la consulta SQL.
	 */
	
	public List<Mensaje> findAll() {

		ArrayList<Mensaje> listaMensajes = new ArrayList<Mensaje>();

		try {

			String sql = "SELECT * FROM mensajes";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String men = rs.getString("mensaje");
				LocalDateTime fecha = rs.getTimestamp("fechahora").toLocalDateTime();
				Long idEjemplar = rs.getLong("idEjemplar");
				Long idPers = rs.getLong("idPersona");
				
				Mensaje mensaje = new Mensaje(id, fecha, men, idEjemplar, idPers);
				listaMensajes.add(mensaje);
			}

		} catch (SQLException e) {

			System.err.println("Error al buscar el mensaje: " + e.getMessage());
			e.printStackTrace();
		}
		return listaMensajes;
	}

	/**

	 * Este método ejecuta una consulta SQL para recuperar el mensaje asociado al ID proporcionado. 
	 * Si se encuentra el mensaje, se mapea a un objeto Mensaje con los datos obtenidos de la base de datos.
	 * @param id El ID del mensaje a buscar.
	 * @return El objeto Mensaje correspondiente al ID proporcionado, o null si no se encuentra el mensaje.
	 * @throws SQLException Si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public Mensaje findById(Long codigo) {

		Mensaje mensaje = null;

		try {

			String sql = "SELECT * FROM mensajes WHERE id=?";
			
			ps = con.prepareStatement(sql);
			ps.setLong(1, codigo);
			rs = ps.executeQuery();

			if (rs.next()) {
				Long id = rs.getLong("id");
				String men = rs.getString("mensaje");
				LocalDateTime fecha = rs.getTimestamp("fechahora").toLocalDateTime();
				Long idEjemplar = rs.getLong("idEjemplar");
				Long idPersona = rs.getLong("idPersona");

				mensaje = new Mensaje(id, fecha, men, idEjemplar, idPersona);
			}

		} catch (SQLException e) {

			System.err.println("Error al buscar el mensaje por código: " + e.getMessage());
			e.printStackTrace();

		}
		return mensaje;
	}

	/**
	 * Este método ejecuta una consulta SQL que busca todos los mensajes cuyo campo `idpersona` coincide con el
	 * `idPersona` proporcionado. Los mensajes encontrados se mapean a objetos Mensaje y se almacenan en una lista 
	 * que es devuelta por el método.
	 * @param idPersona. El ID de la persona cuyo mensajes se desean recuperar.
	 * @return Una lista de objetos Mensaje que contienen los mensajes asociados a la persona indicada. 
	 * Si no se encuentran mensajes, la lista estará vacía.
	 * @throws SQLException Si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public ArrayList<Mensaje> buscarMensajePorPersona(Long idPersona) {

		ArrayList<Mensaje> listaMensajesPorPersona = new ArrayList<Mensaje>();
		try {
			String sql = "SELECT * FROM mensajes WHERE idpersona=?";
			ps = con.prepareStatement(sql);
			ps.setLong(1, idPersona);
			rs = ps.executeQuery();

			while (rs.next()) {
				
				Long id = rs.getLong("id");
				String men = rs.getString("mensaje");
				LocalDateTime fecha = rs.getTimestamp("fechahora").toLocalDateTime();
				Long idEjemplar = rs.getLong("idEjemplar");
				Long idPers = rs.getLong("idPersona");
				
				Mensaje mensaje = new Mensaje(id, fecha, men, idEjemplar, idPers);
				listaMensajesPorPersona.add(mensaje);
				

			}

		} catch (SQLException e) {

			System.err.println("Error al buscar el mensaje por persona: " + e.getMessage());
			e.printStackTrace();

		}
		return listaMensajesPorPersona;
	}

	/**
	 * Este método ejecuta una consulta SQL para obtener todos los mensajes cuya fecha de creación (`fechahora`) se encuentra 
	 * entre las dos fechas proporcionadas. Los mensajes encontrados se mapean a objetos Mensaje y se almacenan en una lista 
	 * que es devuelta por el método.
	 * @param fecha1 La fecha de inicio del rango (inclusive).
	 * @param fecha2 La fecha de fin del rango (inclusive).
	 * @return Una lista de objetos Mensaje que contienen los mensajes encontrados en el rango de fechas especificado.
	 * Si no se encuentran mensajes, la lista estará vacía.
	 * @throws SQLException Si ocurre un error durante la ejecución de la consulta SQL.
	 */
	public ArrayList<Mensaje> buscarMensajePorFecha(LocalDate fecha1, LocalDate fecha2) {

		ArrayList<Mensaje> listaMensajesPorFecha = new ArrayList<Mensaje>();

		try {

			String sql = "SELECT * FROM mensajes WHERE fechahora BETWEEN ? AND ? ";
			ps = con.prepareStatement(sql);

			java.sql.Date fecha1SQL = java.sql.Date.valueOf(fecha1);
			java.sql.Date fecha2SQL = java.sql.Date.valueOf(fecha2);

			ps.setDate(1, fecha1SQL);
			ps.setDate(2, fecha2SQL);
			rs = ps.executeQuery();

			while (rs.next()) {

				Long id = rs.getLong("id");
				String men = rs.getString("mensaje");
				LocalDateTime fecha = rs.getTimestamp("fechahora").toLocalDateTime();
				Long idEjemplar = rs.getLong("idEjemplar");
				Long idPers = rs.getLong("idPersona");

				Mensaje mensaje = new Mensaje(id, fecha, men, idEjemplar, idPers);
				listaMensajesPorFecha.add(mensaje);
				
				/*Recuperamos el id del Ejemplar*/ 	            
				String sqlGetId = "SELECT LAST_INSERT_ID()"; 
	            PreparedStatement psGetId = con.prepareStatement(sqlGetId);
	            ResultSet rs = psGetId.executeQuery();
	            
	            if (rs.next()) {
	            	
	                mensaje.setId(rs.getLong(1)); 
	                mensaje.setFechaHora(fecha);
	            }

			}

		} catch (SQLException e) {
			System.err.println("Error al buscar el mensaje por fecha: " + e.getMessage());
			e.printStackTrace();

		}
		return listaMensajesPorFecha;
	}

	/**
	 * Este método ejecuta una consulta SQL para obtener todos los mensajes asociados a los ejemplares de una planta
	 * determinada. Los mensajes encontrados se mapean a objetos {@link Mensaje} y se añaden a una lista, la cual se retorna.
	 * @param idEjem El identificador del ejemplar que pertenece a la planta que se desea consultar.
	 * @return Una lista de objetos {@link Mensaje} que contienen los mensajes encontrados para la planta especificada.
	 */

	
	public ArrayList<Mensaje> buscarMensajePorPlanta(String idEjem) {
		ArrayList<Mensaje> listaMensajesPorPlanta = new ArrayList<Mensaje>();
		try {

			String sql = "SELECT * FROM mensajes JOIN ejemplares ON mensajes.idejemplar = ejemplares.id JOIN plantas ON ejemplares.idplanta = plantas.codigo WHERE plantas.codigo = ?";

			ps = con.prepareStatement(sql);
			ps.setString(1, idEjem);
			rs = ps.executeQuery();

			while (rs.next()) {
				
				Long id = rs.getLong("id");
				String men = rs.getString("mensaje");
				LocalDateTime fecha = rs.getTimestamp("fechahora").toLocalDateTime();
				Long idEjemplar = rs.getLong("idEjemplar");
				Long idPers = rs.getLong("idPersona");

				Mensaje mensaje = new Mensaje(id, fecha, men, idEjemplar, idPers);
				listaMensajesPorPlanta.add(mensaje);
				

			}

		} catch (SQLException e) {
			System.err.println("Error al buscar el mensaje por planta: " + e.getMessage());
			e.printStackTrace();

		}
		return listaMensajesPorPlanta;
	}
	


}
