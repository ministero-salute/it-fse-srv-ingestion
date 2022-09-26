package it.finanze.sanita.fse2.ms.srvingestion.exceptions;


public class KafkaException extends Exception {



    /**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 6692390708830364495L; 
	
	

	/**
     * Complete constructor.
     *
     * @param msg	Message to be shown.
     *              It should describe what the operation was trying to accomplish.
     * @param e		The original MongoExceptions.
     */
    public KafkaException(final String msg, final Exception e) {
        super(msg, e);
        
    }
    
}
