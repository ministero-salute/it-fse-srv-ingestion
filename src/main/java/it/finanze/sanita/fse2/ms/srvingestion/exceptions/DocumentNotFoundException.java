package it.finanze.sanita.fse2.ms.srvingestion.exceptions;

/** 
 * Thrown when a document is not found by the Srv Query Microservice 
 *
 */
public class DocumentNotFoundException extends Exception {

	
	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = -588291874057815740L;

	
	/**
     * Complete constructor.
     *
     * @param msg	Message to be shown.
     *              It should describe what the operation was trying to accomplish.
     */
    public DocumentNotFoundException(final String msg) {
        super(msg);
        
    }
    
}
