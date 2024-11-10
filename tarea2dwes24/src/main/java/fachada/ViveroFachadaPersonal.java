package fachada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import controlador.Controlador;
import controlador.ServicioCredencial;
import controlador.ServicioEjemplar;
import controlador.ServicioMensaje;
import controlador.ServicioPersona;
import controlador.ServicioPlanta;
import controlador.Sesion;
import modelo.Ejemplar;
import modelo.Mensaje;
import utils.Validacion;

public class ViveroFachadaPersonal {

	/*A continuación encontrarás el menú dirigido al personal del vivero,
	 * con los menús y submenús con las opciones correspondientes a su perfil.
	 */
	
	Scanner sc = new Scanner(System.in);
	Sesion gestionSesion = Sesion.getSesion();
	

	Controlador serviciosControlador = Controlador.getServicios();

	ServicioPlanta svPlanta = serviciosControlador.getServiciosPlanta();
	ServicioEjemplar svEjemplar = serviciosControlador.getServiciosEjemplar();
	ServicioPersona svPersona = serviciosControlador.getServiciosPersona();
	ServicioCredencial svCredencial = serviciosControlador.getServiciosCredenciales();
	ServicioMensaje svMensaje = serviciosControlador.getServiciosMensaje();

	private static ViveroFachadaPersonal portalPersonal;

	private ViveroFachadaPersonal() {

	}

	public static ViveroFachadaPersonal getPortalPersonal() {
		if (portalPersonal == null) {
			portalPersonal = new ViveroFachadaPersonal();
		}
		return portalPersonal;
	}
	
	
	// <----------------------------- MENU PERSONAL ------------------------------------->

	public void menuPersonal(Sesion gestionSesion) {

		int opcion = 0;

		do {

			System.out.println("¡BIENVENIDO AL MENÚ DEL PERSONAL!");
			System.out.println("Por favor, selecciona una opción:");
			System.out.println("1. Gestión de las plantas.");
			System.out.println("2. Gestión de los ejemplares.");
			System.out.println("3. Gestión de los mensajes.");
			System.out.println("4. Cerrar sesión.");
			
			
			try {
			opcion = sc.nextInt();

			if (opcion < 1 || opcion > 4) {

				System.out.println("Opción incorrecta.");
				continue;
			}
			} catch (InputMismatchException e) {
	            System.out.println("Error: Debes ingresar un número entero. Intenta de nuevo.");
	            sc.nextLine(); 
	            continue;
	        }
			

			switch (opcion) {

			case 1:

				menuPersonalPlantas();
				break;

			case 2:

				menuPersonalEjemplares(gestionSesion);
				break;

			case 3:

				menuPersonalMensajes();
				break;

			case 4:
				System.out.println("Has cerrado sesión.");
				gestionSesion.setUsuario(null);
				return;
				
			default:
                System.out.println("Opción incorrecta. Por favor, intenta de nuevo.");
			}

		} while (opcion != 4);

	}
	
	
	// <---------------------------- MENU EJEMPLARES PERSONAL --------------------------------------->

	public void menuPersonalEjemplares(Sesion gestionSesion) {

		int opcion = 0;

		do {

			System.out.println("Selecciona una opción:");
			System.out.println("1. Registrar nuevo ejemplar.");
			System.out.println("2. Filtrar ejemplares por tipo de planta.");
			System.out.println("3. Volver al menú principal.");

			try {
			opcion = sc.nextInt();

			if (opcion < 1 || opcion > 3) {

				System.out.println("Opción incorrecta.");
				continue;
			}
			} catch (InputMismatchException e) {
	            System.out.println("Error: Debes ingresar un número entero. Intenta de nuevo.");
	            sc.nextLine(); 
	            continue;
	        }

			switch (opcion) {

			case 1:
				
				System.out.println("PLANTAS: ");
				if (svPlanta.mostrarPlantas().size() == 0) {
                    System.out.println("No hay plantas registradas.");
                } else {
                    for (int i = 0; i < svPlanta.mostrarPlantas().size(); i++) {
						System.out.println(i + 1 + ". " +" CODIGO PLANTA: " + svPlanta.mostrarPlantas().get(i).getCodigo());
                    }
                }
				String codigo;
				sc.nextLine();
				
				do {
					System.out.println("Introduce el código de la planta: ");
					codigo = sc.nextLine();

					if (!Validacion.validarCodigo(codigo)) {
						System.err.println("Introducidos caracteres no válidos en el código.");
						
					}

					if (!svPlanta.codigoExistente(codigo)) {
						System.err.println("Código no existe en el sistema.");
						
					}
					

				} while (!svPlanta.codigoExistente(codigo));

			
				Ejemplar ejemplar = new Ejemplar(codigo);
				
				if(svEjemplar.insertarEjemplar(ejemplar) == 1) {
					String mens = "Ejemplar añadido por: " + gestionSesion.getUsuario() + " a las " + LocalDate.now();
					Mensaje mensaje = new Mensaje (mens, ejemplar.getId() , svCredencial.getIdPersona(gestionSesion.getUsuario()));
					svMensaje.insertarMensaje(mensaje);
				System.out.println("Ejemplar añadido con éxito por: " + gestionSesion.getUsuario() + " a las " + LocalDate.now());
				}
				break;

			case 2:
				System.out.println("PLANTAS: ");
				String codigoPlanta;
				if (svPlanta.mostrarPlantas().size() == 0) {
                    System.out.println("No hay plantas registradas.");
                } else {
                    for (int i = 0; i < svPlanta.mostrarPlantas().size(); i++) {
                        System.out.println(i + 1 + ". " + "CODIGO PLANTA: " + svPlanta.mostrarPlantas().get(i).getCodigo());
                    }
                }
				sc.nextLine();
				do {
					System.out.println("Introduce el código de la planta: ");
					codigoPlanta = sc.nextLine();

					if (!Validacion.validarCodigo(codigoPlanta)) {
						System.err.println("Introducidos caracteres no válidos en el código.");
						
					}

					if (!svPlanta.codigoExistente(codigoPlanta)) {
						System.err.println("Código no existe en el sistema.");
						
					}
					

				} while (!svPlanta.codigoExistente(codigoPlanta));

				ArrayList<Ejemplar> lEjemplaresPlanta = new ArrayList<Ejemplar>();
				lEjemplaresPlanta.addAll(svEjemplar.mostrarEjemplaresPorPlanta(codigoPlanta));

				if (lEjemplaresPlanta.size() == 0) {

					System.out.println("No hay ejemplares registrados.");

				} else {

					for (int i = 0; i < lEjemplaresPlanta.size(); i++) {

						System.out.println((1 + i) + ". " + lEjemplaresPlanta.get(i).toString());
					}
				}
				break;

			}
		} while (opcion != 3);
	}

	
	// <---------------------------------- MENU PLANTAS PERSONAL ------------------------------->
	
	public void menuPersonalPlantas() {
		
		int opcion = 0;
		
		do {
			
			System.out.println("Selecciona una opción:");
			System.out.println("1. Ver todas las plantas.");
			System.out.println("2. Volver al menú principal.");
			
			try {
			opcion = sc.nextInt();
			
			if (opcion < 1 || opcion > 2) {
				
				System.out.println("Opción incorrecta.");
				
				continue;
			}
			} catch (InputMismatchException e) {
	            System.out.println("Error: Debes ingresar un número entero. Intenta de nuevo.");
	            sc.nextLine(); 
	            continue;
	        }
			
			switch (opcion) {
			
			case 1:

				if (svPlanta.mostrarPlantas().size() == 0) {
					System.out.println("No hay plantas registradas.");
				} else {
					for (int i = 0; i < svPlanta.mostrarPlantas().size(); i++) {
						System.out.println(i + 1 + ". " + "CODIGO PLANTA: "
								+ svPlanta.mostrarPlantas().get(i).getCodigo() + " NOMBRE COMUN: "
								+ svPlanta.mostrarPlantas().get(i).getNombrecomun() + " NOMBRE CIENTIFICO: "
								+ svPlanta.mostrarPlantas().get(i).getNombrecientifico() + "\n");
						}
				}
				break;
			
			}
			
		} while (opcion != 2);
	}
	
	
	// <------------------------------------- MENU PERSONAL MENSAJES ---------------------------------->

	public void menuPersonalMensajes() {
		
		int opcion = 0;
		
		do {
			
			System.out.println("Selecciona una opción:");
			System.out.println("1. Ver todos los mensajes.");
			System.out.println("2. Ver mensajes por persona.");
			System.out.println("3. Ver mensajes por rango de fechas.");
			System.out.println("4. Ver mensajes por tipo de planta.");
			System.out.println("5. Volver al menú principal.");
			
			try {
			opcion = sc.nextInt();
			
			if (opcion < 1 || opcion > 5) {
				
				System.out.println("Opción incorrecta.");
				continue;
			}
			} catch (InputMismatchException e) {
	            System.out.println("Error: Debes ingresar un número entero. Intenta de nuevo.");
	            sc.nextLine(); 
	            continue;
	        }
			
			switch (opcion) {
			
			case 1:
				
				if (svMensaje.mostrarMensajes().size() == 0) {
					
					System.out.println("No hay mensajes registrados.");

				} else {

					for (int i = 0; i < svMensaje.mostrarMensajes().size(); i++) {
						
						System.out.println(i + 1 + ". " + svMensaje.mostrarMensajes().get(i).toString());
					}
				}
				
				break;
				
			case 2:
				Long idPersona = null;
				
				System.out.println("PERSONAS: ");
				if (svPersona.mostrarPersona().size() == 0) {
					System.out.println("No hay personas registradas.");

				} else {

					for (int i = 0; i < svPersona.mostrarPersona().size(); i++) {
						System.out.println(i + 1 + ". " + "NOMBRE : " + svPersona.mostrarPersona().get(i).getNombre() + " CODIGO: " + svPersona.mostrarPersona().get(i).getId());
					}
				}
				System.out.println("Introduce codigo de la persona para ver los mensajes: ");
				try {
					idPersona = sc.nextLong();
				} catch (InputMismatchException e) {
					System.err.println("Debes introducir un número");
					sc.nextLine();
					continue;
				}
				sc.nextLine();
				ArrayList<Mensaje> lMensajes = new ArrayList<Mensaje>();
				lMensajes.addAll(svMensaje.buscarMensajePorPersona(idPersona));
				if (lMensajes.size() == 0) {
					System.out.println("No hay mensajes registrados.");

				} else {

					for (int i = 0; i < lMensajes.size(); i++) {
						System.out.println((1 + i) + ". " + lMensajes.get(i).toString());
					}
				}

				break;
				
			case 3:
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate fecha1 = null;
				LocalDate fecha2 = null;
				do {
					try {
						sc.nextLine();
						System.out.println("Introduce la primera fecha para ver los mensajes (01-01-0000): ");
						String fecha1Str = sc.nextLine();
						fecha1 = LocalDate.parse(fecha1Str, formatter);
					} catch (DateTimeParseException e) {
						System.err.println("Fecha añadida incorrectamente.");
					}
				} while (fecha1 == null);
				do {
					try {
						System.out.println("Introduce la segunda fecha para ver los mensajes (01-01-0000): ");
						String fecha2Str = sc.nextLine();
						fecha2 = LocalDate.parse(fecha2Str, formatter);
					} catch (DateTimeParseException e) {
						System.err.println("Fecha añadida incorrectamente.");
					}
				} while (fecha2 == null);
				ArrayList<Mensaje> lMensajesFecha = new ArrayList<Mensaje>();
				svMensaje.buscarMensajePorFecha(fecha1, fecha2);
				lMensajesFecha.addAll(svMensaje.buscarMensajePorFecha(fecha1, fecha2));
				if (lMensajesFecha.size() == 0) {
					System.out.println("No hay mensajes registrados.");

				} else {

					for (int i = 0; i < lMensajesFecha.size(); i++) {
						System.out.println((i + 1) + ". " + lMensajesFecha.get(i).toString());
					}
				}
				break;

			case 4:
				System.out.println("PLANTAS: ");
				String idPlanta;
				if (svPlanta.mostrarPlantas().size() == 0) {
                    System.out.println("No hay plantas registradas.");
                } else {
                    for (int i = 0; i < svPlanta.mostrarPlantas().size(); i++) {
                        System.out.println(i + 1 + ". " + "CODIGO PLANTA: " + svPlanta.mostrarPlantas().get(i).getCodigo());
                    }
                }
				sc.nextLine();
				do {
					System.out.println("Introduce el código de la planta: ");
					idPlanta = sc.nextLine();

					if (!Validacion.validarCodigo(idPlanta)) {
						System.err.println("Introducidos caracteres no válidos en el código.");
						
					}

					if (!svPlanta.codigoExistente(idPlanta)) {
						System.err.println("Código no existe en el sistema.");
						
					}
					

				} while (!svPlanta.codigoExistente(idPlanta));

				ArrayList<Mensaje> lMensajesPlanta = new ArrayList<Mensaje>();

				svMensaje.buscarMensajePorPlanta(idPlanta);
				lMensajesPlanta.addAll(svMensaje.buscarMensajePorPlanta(idPlanta));

				if (lMensajesPlanta.size() == 0) {

					System.out.println("No hay mensajes registrados.");

				} else {

					for (int i = 0; i < lMensajesPlanta.size(); i++) {
						System.out.println((i + 1) + ". " + lMensajesPlanta.get(i).toString());
					}
				}
				break;
				
			}
			
		} while (opcion != 5);
	}

}
