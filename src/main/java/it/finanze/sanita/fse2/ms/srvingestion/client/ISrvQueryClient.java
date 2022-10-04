package it.finanze.sanita.fse2.ms.srvingestion.client;

import java.io.Serializable;


/**
 * The interface for the Srv Query Client 
 *
 */
public interface ISrvQueryClient extends Serializable {

	/**
	 * Function used to call the Srv Query Microservice to check the existence of a given document 
	 * on MongoDB. Queries are demanded to the Srv Query Microservice to reduce workload for the Ingestor. 
	 * 
	 * @param docId  The ID of the document whose existence needs to be checked by the SRV Query Microservice 
	 * @return boolean  A boolean that represents whether the document has been found by the Srv Query Microservice 
	 */
    Boolean checkExists(String docId);

}
