package analista;

public class DBAdapterException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1485387865819745729L;
	public DBAdapterException(String mensaje){
		super(mensaje);
	}

	public String toString(){
		return "DBAdapterException: "+getMessage();
	}
}