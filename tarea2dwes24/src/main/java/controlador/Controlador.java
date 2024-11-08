package controlador;

public class Controlador {
	
	
	private ServicioPlanta servicioPlanta;
	private ServicioCredencial servicioCredencial;
	private ServicioPersona servicioPersona;
	private ServicioEjemplar servicioEjemplar;
	private static Controlador servicios;

	
	public static Controlador getServicios() {
		
		if(servicios == null) 
			
			servicios=new Controlador();
			return servicios;
		
	}
	
	
	private Controlador() {
		
		servicioPlanta=new ServicioPlanta();
		servicioCredencial= new ServicioCredencial();
		servicioPersona=new ServicioPersona();
		servicioEjemplar=new ServicioEjemplar();
		
	}
	
	public ServicioPlanta getServiciosPlanta() {
		return new ServicioPlanta();
	}

	public ServicioEjemplar getServiciosEjemplar() {
		return new ServicioEjemplar();
	}
	
	public ServicioPersona getServiciosPersona() {
		return new ServicioPersona();
	}
	
	public ServicioCredencial getServiciosCredenciales() {
		return new ServicioCredencial();
	}
	
	public ServicioMensaje getServiciosMensaje() {
		return new ServicioMensaje();
	}
}
