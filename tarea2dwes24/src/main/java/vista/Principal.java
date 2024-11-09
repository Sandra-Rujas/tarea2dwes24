package vista;



import fachada.ViveroFachadaInvitado;


public class Principal {

	public static void main(String[] args) {
		
		ViveroFachadaInvitado portalInvitado = ViveroFachadaInvitado.getPortalInvitado();
    	
        portalInvitado.menuInvitado();
		
		
		
	}
	
	}


