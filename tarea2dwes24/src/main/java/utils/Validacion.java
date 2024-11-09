package utils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

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
	
	
	/*Método para validar fecha*/
	public static java.sql.Date leerFecha() {
        Date ret = null;
        int dia, mes, anio;
        boolean correcto = false;
        do {
            System.out.println("Introduzca un valor para el día (1...31)");
            sc = new Scanner(System.in, "ISO-8859-1");
            dia = sc.nextInt();
            System.out.println("Introduzca un valor para el mes (1...12)");
            sc = new Scanner(System.in, "ISO-8859-1");
            mes = sc.nextInt();
            System.out.println("Introduzca un valor para el año");
            sc = new Scanner(System.in, "ISO-8859-1");
            anio = sc.nextInt();

            try {
                ret = Date.valueOf(LocalDate.of(anio, mes, dia));
                correcto = true;
            } catch (Exception e) {
                System.out.println("Fecha introducida incorrecta.");
                correcto = false;
            }
        } while (!correcto);
        return ret;
    }

}
