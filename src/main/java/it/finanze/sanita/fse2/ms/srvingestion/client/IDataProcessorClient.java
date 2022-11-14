/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.client;

import java.io.Serializable;

import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;

/**
 * Interface for Data Processor Client
 *
 */
public interface IDataProcessorClient extends Serializable {

	/**
	 * Function used to send a Document to the Data Processor for processing (synchronous flow). 
	 * Retrieves the Data Processor Host and calls the POST /process API of the Data Processor
	 * to further process the given document. 
	 * 
	 * @param reqDTO  The DocumentReferenceDTO object to send to the Data Processor Microservice
	 * @return boolean  A boolean representing whether the request has been successful 
	 */
    Boolean sendRequestToDataProcessor(final DocumentDTO reqDTO); 

}
