package fachada;

import java.util.InputMismatchException;
import java.util.Scanner;
import controlador.Controlador;
import controlador.ServicioCredencial;
import controlador.ServicioPlanta;
import controlador.Sesion;

public class ViveroFachadaInvitado {
	
	/*A continuación encontrarás el menú dirigido al perfil del invitado del vivero,
	 * con los menús y submenús con las opciones correspondientes a su perfil.
	 */

	Scanner sc = new Scanner(System.in);
	Controlador serviciosControlador = Controlador.getServicios();
	Sesion gestionSesion = new Sesion();

	ServicioPlanta svPlanta = serviciosControlador.getServiciosPlanta();
	ServicioCredencial svCredencial = serviciosControlador.getServiciosCredenciales();

	private static ViveroFachadaInvitado portalInvitado;

	private ViveroFachadaInvitado() {

	}

	public static ViveroFachadaInvitado getPortalInvitado() {

		if (portalInvitado == null) {
			
			portalInvitado = new ViveroFachadaInvitado();
		}

		return portalInvitado;
	}

	public void menuInvitado() {

	    int opcion = 0;

	    do {

	        System.out.println("¡BIENVENIDO AL MENÚ PRINCIPAL DEL VIVIERO");
	        System.out.print("\nPor favor, selecciona una opción: ");
	        System.out.println("\n1. Ver todas las plantas en modo invitado");
	        System.out.println("2. Login");
	        System.out.println("3. Salir");

	        try {
	            opcion = sc.nextInt();

	            if (opcion < 1 || opcion > 3) {
	                System.out.println("Opción inválida. Por favor, ingresa una opción entre 1 y 3.");
	                continue; 
	            }
	        } catch (InputMismatchException e) {
	            System.out.println("Error: Debes ingresar un número entero. Intenta de nuevo.");
	            sc.nextLine(); 
	        }

	            switch (opcion) {

	                case 1:
	                	/*Mostrar todas las plantas*/
	                    if (svPlanta.mostrarPlantas().size() == 0) {
	                        System.out.println("No hay plantas registradas.");
	                    } else {
	                        for (int i = 0; i < svPlanta.mostrarPlantas().size(); i++) {
	    						System.out.println(i + 1 + ". " + "CODIGO PLANTA: " + svPlanta.mostrarPlantas().get(i).getCodigo()+ " NOMBRE COMUN: " + svPlanta.mostrarPlantas().get(i).getNombrecomun() + " NOMBRE CIENTIFICO: " +svPlanta.mostrarPlantas().get(i).getNombrecientifico() +"\n");
	                        }
	                    }
	                    break;

	                case 2:
	                	/*Opción Login e inicio de sesión*/
	                    boolean valido = false;

	                    do {
	                        sc.nextLine(); 
	                        System.out.print("Introduce tu usuario: ");
	                        String usuario = sc.nextLine();
	                        System.out.print("Introduce tu contraseña: ");
	                        String password = sc.nextLine();

	                        if (svCredencial.login(usuario, password)) {
	                            valido = true;
	                            gestionSesion.setUsuario(usuario);

	                            if (usuario.equalsIgnoreCase("admin")) {
	                                ViveroFachadaAdmin.getPortalAdmin().menuAdmin(gestionSesion);
	                                
	                            } else {
	                                ViveroFachadaPersonal.getPortalPersonal().menuPersonal(gestionSesion);
	                            }

	                        } else {
	                            System.err.println("\nUsuario o contraseña incorrectos.");
	                        }

	                    } while (!valido);

	                    break;

	                case 3:
	                    System.out.println("Has salido.");
	                    break;

	                default:
	                    System.out.println("Opción incorrecta. Por favor, intenta de nuevo.");
	            }

	    } while (opcion != 3); 
	}


}
