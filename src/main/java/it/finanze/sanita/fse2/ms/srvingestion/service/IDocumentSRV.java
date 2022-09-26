package it.finanze.sanita.fse2.ms.srvingestion.service;

import java.util.List;

import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentReferenceDTO;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.DocumentReferenceETY;

public interface IDocumentSRV {

	
    /**
     * Inserts one document in the staging database
     * @param dto The document to insert 
     * @return 
     * @throws OperationException If a data-layer error occurs
     * @throws EmptyDocumentException 
     */
	DocumentReferenceETY insert(DocumentReferenceDTO dto) throws OperationException, EmptyDocumentException; 
	
    /**
     * Delete one document from the staging database
     * @param identifier The identifier of the document to delete 
     * @throws OperationException If a data-layer error occurs
     */
	void delete(String identifier); 
	
	
    /**
     * Retrieves a document from the staging database given its Mongo ID
     * @throws DocumentNotFoundException 
     * @throws OperationException If a data-layer error occurs
     */
	DocumentReferenceDTO getDocumentById(String id) throws DocumentNotFoundException;

	
    /**
     * Retrieves the list of all documents from the staging database
     * @throws OperationException If a data-layer error occurs
     */
	List<DocumentReferenceDTO> getDocuments();



}
