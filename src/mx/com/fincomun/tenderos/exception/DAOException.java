package mx.com.fincomun.tenderos.exception;

public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAOException(){
		super();
	}
	
	public DAOException(Exception e){
		super(e);
	}
	
	public DAOException(Throwable t){
		super(t);
	}
	
	public DAOException(String mensaje){
		super(mensaje);
		
	}
}
