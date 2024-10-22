package principal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import com.mysql.cj.jdbc.MysqlDataSource;

import modelo.Planta;

public class Principal {

	public static void main(String[] args) {
		
		System.out.println("INI");
	
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Dame el codigo de una nueva planta");
		String codigo=sc.nextLine().trim().toUpperCase();
		System.out.println("Dame el nombre común");
		String nombre_comun=sc.nextLine();
		System.out.println("Dame el nombre cientifico");
		String nombre_cientifico=sc.nextLine();
		
		
		
		Planta planta_nueva=new Planta(codigo,nombre_comun,nombre_cientifico);
		
		
		//Cpnexión.
		Connection con;	
		//MysqlDataSource.
		MysqlDataSource m= new MysqlDataSource();
		//Fichero db.proporties.
		Properties prop=null;
		//FileInputStream.
		FileInputStream fis;
		
		//Variables para la conexión.
		String url;
		String usuario;
		String password;
		
		try {
			
			fis=new FileInputStream("src/main/resources/db.properties");
			prop.load(fis);
			
			//Introducimos los valores de url,usuario y password.
			url= prop.getProperty("url");
			usuario= prop.getProperty("usuario");
			password= prop.getProperty("password");
			
			//Introducimos los datos en MysqlDataSource.
			m.setUrl(url);
			m.setUser(usuario);
			m.setPassword(password);
			
			
			con=m.getConnection();
			
			String sql= "INSERT INTO plantas(codigo,nombrecomun,nombrecientifico) VALUES ("+planta_nueva.getCodigo()+" , "+planta_nueva.getNombrecomun()+" ,"+ planta_nueva.getNombrecientifico();
			
			
			PreparedStatement ps= con.prepareStatement(sql);
			ps.execute();
			con.close();
			
			
		}catch(SQLException e) {
			
			System.out.println("Se ha producido una SQLException"+ e.getLocalizedMessage());
			e.printStackTrace();
			
			
		}catch(FileNotFoundException e) {
			
			System.out.println("Se ha producido un FileNotFoundException" +e.getLocalizedMessage());
			e.printStackTrace();
			
		}catch(IOException e){
			
			System.out.println("Se ha producido una IOException: "+ e.getLocalizedMessage());
			e.printStackTrace();
			
		}
		
	
		
		


			

	
	
		
		System.out.println("FIN");
	}

}
