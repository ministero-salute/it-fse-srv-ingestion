package it.finanze.sanita.fse2.ms.srvingestion.exceptions;

/** 
 * Thrown when the document to be inserted is empty 
 * 
 */
public class EmptyDocumentException extends Exception {
	
	
	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = 5488934735322353273L;


	/**
     * Complete constructor.
     *
     * @param msg	Message to be shown.
     *              It should describe what the operation was trying to accomplish.
     */
    public EmptyDocumentException(final String msg) {
        super(msg);
        
    }
    
}
