package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

import modelo.Ejemplar;

public class Validacion {
	
	static Scanner sc = new Scanner (System.in);

	/*Método para validar nombre*/	
	public static boolean validarNombre(String nombre) {
		return nombre != null && nombre.matches("^[a-zA-ZÀ-ÿ]+( [a-zA-ZÀ-ÿ]+)*$") && nombre.length() >=3 && nombre.length()<=50;
	}
	
	/*Método para validar usuario*/	
	public static boolean validarUsuario(String usuario) {
		return usuario != null && usuario.matches("^[a-zA-Z0-9]+$") && usuario.length()<=15;
	}
	
	/*Método para validar email*/
	public static boolean validarEmail(String email) {
		return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
				&& email.length() >=5 && email.length()<=50;
		
	}

	/*Método para validar contraseña*/	
	public static boolean validarPassword(String password) {	
		return password != null && !password.contains(" ") && password.length() >= 4 && password.length() <= 15;
	}
	
	/*Método para validar código*/
	public static boolean validarCodigo(String codigo) {
		return codigo.matches("^[A-Za-z]{1,50}$");
	}
	


}
