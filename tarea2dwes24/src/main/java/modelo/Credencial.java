package modelo;

public class Credencial {
	
	//Atributos
	private Long id;
	private String usuario;
	private String password;
	
	 //Constructor por defecto.
    public Credencial() {
		
	}
	
	//Constructor
	public Credencial(Long id, String usuario, String password) {
		this.id = id;
		this.usuario = usuario;
		this.password = password;
	}
	
	
	//Setters and Getters.
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	//Método ToString
	@Override
	public String toString() {
		return "Credencial [id=" + id + ", usuario=" + usuario + ", password=" + password + "]";
	}
	

	
}
