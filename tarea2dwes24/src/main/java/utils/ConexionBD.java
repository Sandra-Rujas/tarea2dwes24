package utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ConexionBD {
	
	//Atributo
	private static Connection con;
	
	private static ConexionBD f;
	
	//Realiza la conexión.
	public static Connection getConexion() {
		
		try {
			
			if (con == null || con.isClosed()) {
				
				Properties properties = new Properties();
				MysqlDataSource m = new MysqlDataSource();
				FileInputStream fis;
				fis = new FileInputStream("src/resources/db.properties");
				properties.load(fis);
				m.setUrl(properties.getProperty("url"));
				m.setPassword(properties.getProperty("password"));
				m.setUser(properties.getProperty("usuario"));
				fis.close();
				con = m.getConnection();
			}
			
			return con;
			
		} catch (FileNotFoundException e) {
			
			System.out.println("Error al acceder al fichero properties " + e.getMessage());
			
		} catch (IOException e) {
			
			System.out.println("Error al leer las propiedades del fichero properties" + e.getMessage());
			
		} catch (SQLException e) {
			
			System.out.println("Se ha producido una SQLException: " + e.getMessage());
			
		} catch (Exception e) {
			
			System.out.println("Se ha producido una Exception: " + e.getMessage());
			
			e.printStackTrace();
		}
		
		return con;
	}
	
	//Método estático que devuelve instancia correspondiente al atributo estático de la clase.
	
		public static ConexionBD getCon() {
		
			if(f==null)
				
				f=new ConexionBD();
			
		return f;
		
	}
		
		//Método Cerrar Conexión.
		public static void cerrarConexion() {
			try {
				
				if (con != null && !con.isClosed()) {
					con.close();
				}
				
			} catch (SQLException e) {
				System.out.println("Se ha producido una SQLException: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	

