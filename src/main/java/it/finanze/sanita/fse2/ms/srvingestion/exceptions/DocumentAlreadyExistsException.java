package it.finanze.sanita.fse2.ms.srvingestion.exceptions;

public class DocumentAlreadyExistsException extends Exception {


	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = -2050275694993866242L;

	
	/**
     * Complete constructor.
     *
     * @param msg	Message to be shown.
     *              It should describe what the operation was trying to accomplish.
     */
    public DocumentAlreadyExistsException(final String msg) {
        super(msg); 
    }
        
}
