package fachada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
import modelo.Persona;
import modelo.Planta;
import utils.Validacion;

public class ViveroFachadaAdmin {
	
	/*A continuación encontrarás el menú dirigido al administrador del vivero,
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

	private static ViveroFachadaAdmin portalAdmin;

	private ViveroFachadaAdmin() {

	}

	public ViveroFachadaAdmin(Sesion sesion) {
		this.gestionSesion = sesion;
	}

	public static ViveroFachadaAdmin getPortalAdmin() {
		if (portalAdmin == null) {
			portalAdmin = new ViveroFachadaAdmin();
		}
		return portalAdmin;
	}

	// <------------------------------------------ MENU ADMIN ----------------------------------------->

	public void menuAdmin(Sesion gestionSesion) {
		int opcion = 0;
		do {
			System.out.println("¡BIENVENIDO AL MENÚ DEL ADMINISTRADOR!");
			System.out.println("Selecciona una opción:");
			System.out.println("1. Gestión de plantas.");
			System.out.println("2. Gestión de ejemplares.");
			System.out.println("3. Gestión de mensajes.");
			System.out.println("4. Gestión de personas.");
			System.out.println("5. Cerrar sesion.");

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
				menuAdminPlantas();
				break;
			case 2:
				menuAdminEjemplares(gestionSesion);
				break;
			case 3:
				menuAdminMensajes(gestionSesion);
				break;
			case 4:
				menuAdminPersonas();
				break;
			case 5:
				System.out.println("Has cerrado sesión.");
				gestionSesion.setUsuario(null);
				return;

			default:
				System.out.println("Opción incorrecta. Por favor, intenta de nuevo.");

			}
		} while (opcion != 5);
	}

	// <------------------------------------ MENU ADMIN PLANTA ---------------------------------------------->

	public void menuAdminPlantas() {
		int opcion = 0;
		do {
			System.out.println("Selecciona una opción:");
			System.out.println("1. Ver plantas.");
			System.out.println("2. Crear nueva planta.");
			System.out.println("3. Modificar planta.");
			System.out.println("4. Volver al menú principal.");
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
			case 2:
				sc.nextLine();

				String codigo;
				String nombreComun;
				String nombreCientifico;

				do {
					System.out.println("Introduce el código de la planta: ");
					codigo = sc.nextLine().toUpperCase();

					if (!Validacion.validarCodigo(codigo)) {
						System.err.println("Introducidos caracteres no válidos en el código.");

					}

					if (svPlanta.codigoExistente(codigo)) {
						System.err.println("Código ya registrado en el sistema.");

					}
					if (Validacion.validarCodigo(codigo) && !svPlanta.codigoExistente(codigo)) {
						break;

					}

				} while (true);

				do {
					System.out.println("Introduce el nombre común de la planta: ");
					nombreComun = sc.nextLine();

					if (!Validacion.validarNombre(nombreComun)) {
						System.err.println("Introducidos caracteres no válidos en el nombre común.");
					}

				} while (!Validacion.validarNombre(nombreComun));

				do {
					System.out.println("Introduce el nombre científico de la planta: ");
					nombreCientifico = sc.nextLine();

					if (!Validacion.validarNombre(nombreCientifico)) {
						System.err.println("Introducidos caracteres no válidos en el nombre común.");
					}

				} while (!Validacion.validarNombre(nombreCientifico));

				Planta planta = new Planta(codigo, nombreComun, nombreCientifico);

				if (svPlanta.insertarPlanta(planta) > 0) {
					System.out.println("Nueva planta registrada con éxito");
				} else {
					System.err.println("Error al registrar la nueva planta.");
				}

				break;

			case 3:
				System.out.println("PLANTAS");
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
				sc.nextLine();

				System.out.println("PLANTAS: ");
				String idPlanta;
				if (svPlanta.mostrarPlantas().size() == 0) {
                    System.out.println("No hay plantas registradas.");
                } else {
                    for (int i = 0; i < svPlanta.mostrarPlantas().size(); i++) {
                        System.out.println(i + 1 + ". " + "CODIGO PLANTA: " + svPlanta.mostrarPlantas().get(i).getCodigo());
                    }
                }
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

				do {
					System.out.println("Introduce el nombre común de la planta: ");
					nombreComun = sc.nextLine();

					if (!Validacion.validarNombre(nombreComun)) {
						System.err.println("Nombre comun no valido.");
					}
				} while (!Validacion.validarNombre(nombreComun));

				do {
					System.out.println("Introduce el nombre cientifico:");
					nombreCientifico = sc.nextLine();

					if (!Validacion.validarNombre(nombreCientifico)) {
						System.err.println("Nombre cientifico no valido.");
					}
				} while (!Validacion.validarNombre(nombreCientifico));

				if (svPlanta.modificarPlanta(new Planta(idPlanta, nombreComun, nombreCientifico)) > 0) {
					System.out.println("Planta modificada con exito.");

				}

				break;
			}
		} while (opcion != 4);
	}

	// <------------------------------------------- MENU ADMIN EJEMPLARES ---------------------------------------->

	public void menuAdminEjemplares(Sesion gestionSesion) {
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
						System.out.println(
								i + 1 + ". " + "CODIGO PLANTA: " + svPlanta.mostrarPlantas().get(i).getCodigo());
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

				if (svEjemplar.insertarEjemplar(ejemplar) == 1) {
					String mens = "Ejemplar añadido por: " + gestionSesion.getUsuario() + " a las " + LocalDate.now();
					Mensaje mensaje = new Mensaje(mens, ejemplar.getId(),
							svCredencial.getIdPersona(gestionSesion.getUsuario()));
					svMensaje.insertarMensaje(mensaje);
					System.out.println("Ejemplar añadido con éxito por: " + gestionSesion.getUsuario() + " a las "
							+ LocalDate.now());
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

	// <------------------------------------------- MENU ADMIN PERSONAS ----------------------------------------->

	public void menuAdminPersonas() {
		int opcion = 0;
		do {
			System.out.println("Selecciona una opción:");
			System.out.println("1. Registrar nueva persona.");
			System.out.println("2. Ver todas las personas.");
			System.out.println("3. Volver al menú principal.");

			try {
				opcion = sc.nextInt();
				if (opcion < 1 || opcion > 3) {
					System.err.println("Opción incorrecta.");
					continue;
				}
			} catch (InputMismatchException e) {
				System.err.println("Error: Debes ingresar un número entero. Intenta de nuevo.");
				sc.nextLine();
				continue;
			}
			switch (opcion) {
			case 1:
				sc.nextLine();
				System.out.println("Registrar usuario");

				String nombre;
				String email;
				String usuario;
				String password;

				do {
					System.out.println("Introduce un nombre:");
					nombre = sc.nextLine();

					if (!Validacion.validarNombre(nombre)) {

						System.err.println("Caracteres no válidos en el nombre.");
					} else {

						break;
					}

				} while (true);

				do {
					System.out.println("Introduce un email:");
					email = sc.nextLine();

					if (!Validacion.validarEmail(email)) {
						System.err.println("Formato de email no válido.");
					}

					if (svPersona.isEmailRegistrado(email)) {
						System.err.println("Email ya registrado en el sistema.");
					}

					if (Validacion.validarEmail(email) && !svPersona.isEmailRegistrado(email)) {
						break;
					}

				} while (true);

				Persona persona = new Persona(nombre, email);
				if (svPersona.insertarPersona(persona) > 0) {
					System.out.println("Persona insertada con exito.");
				}

				System.out.println("Datos para añadir las credenciales del usuario:");

				do {
					System.out.println("Introduce nombre de usuario:");
					usuario = sc.nextLine();

					if (!Validacion.validarUsuario(usuario)) {
						System.err.println("El nombre de usuario no puede contener espacios.");
					}

					if (svCredencial.existeUsuario(usuario)) {
						System.err.println("El nombre de usuario ya existe.");
					}

					if (Validacion.validarUsuario(usuario) && !svCredencial.existeUsuario(usuario)) {
						break;
					}

				} while (true);

				do {
					System.out.println("Introduce una contraseña:");
					password = sc.nextLine();

					if (!Validacion.validarPassword(password)) {
						System.err.println(
								"La contraseña debe tener entre 6 y 10 caracteres y no puede contener espacios.");
					} else {
						break;
					}

				} while (true);

				if (svCredencial.insertarCredencial(usuario, password, persona.getId())) {
					System.out.println("Nuevo usuario registrado con éxito");
				} else {
					System.err.println("Error al registrar al nuevo usuario.");
				}
				break;
			case 2:
				if (svPersona.mostrarPersona().size() == 0) {
					System.out.println("No hay personas registradas.");

				} else {

					for (int i = 0; i < svPersona.mostrarPersona().size(); i++) {
						System.out.println(i + 1 + ". " + svPersona.mostrarPersona().toString());
					}
				}
				break;

			}
		} while (opcion != 3);
	}

	// <--------------------------------------- MENU ADMIN MENSAJES --------------------------------------->

	public void menuAdminMensajes(Sesion gestionSesion) {
		int opcion = 0;
		do {
			System.out.println("Selecciona una opción:");
			System.out.println("1. Nuevo mensaje.");
			System.out.println("2. Ver mensajes.");
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
				System.out.println("EJEMPLARES");
				if (svEjemplar.mostrarEjemplares().size() == 0) {
					System.out.println("No hay plantas registradas.");
				} else {

					for (int i = 0; i < svEjemplar.mostrarEjemplares().size(); i++) {
						System.out.println(i + 1 + ". " + svEjemplar.mostrarEjemplares().get(i).toString());
					}
				}
				Long idEjemplar = null;
				sc.nextLine();
				do {
					try {
					System.out.println("Introduce el código del ejemplar: ");
					idEjemplar = sc.nextLong();
					} catch (InputMismatchException e) {
						System.out.println("Error: Debes ingresar un número entero. Intenta de nuevo.");
						sc.nextLine();
						return;
					}

					if (!svEjemplar.codigoExistente(idEjemplar)) {
						System.err.println("Código no existe en el sistema.");
						
					}
					
				} while (!svEjemplar.codigoExistente(idEjemplar));
				sc.nextLine();
				System.out.println("Introduce el mensaje:");
				String mens = sc.nextLine();

				svMensaje.insertarMensaje(
						new Mensaje(mens, idEjemplar, svCredencial.getIdPersona(gestionSesion.getUsuario())));

				break;
			case 2:
				menuAdminVerMensajes();
				break;

			}
		} while (opcion != 3);
	}

	// <------------------------------- MENU ADMIN VER MENSAJES ------------------------------------------------>

	public void menuAdminVerMensajes() {
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
						System.out.println(i + 1 + ". " + svPersona.mostrarPersona().get(i).toString());
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
