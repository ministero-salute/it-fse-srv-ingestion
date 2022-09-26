package it.finanze.sanita.fse2.ms.srvingestion.exceptions;

public class UnsupportedOperationException extends Exception {

	
	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = -464060455789591814L;

	
	/**
     * Complete constructor.
     *
     * @param msg	Message to be shown.
     *              It should describe what the operation was trying to accomplish.
     */
    public UnsupportedOperationException(final String msg) {
        super(msg);
        
    }
}
