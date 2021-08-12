package exceptions;

public class ValorDuplicadoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValorDuplicadoException(String mensagem) {
		super(mensagem);
	}
}
