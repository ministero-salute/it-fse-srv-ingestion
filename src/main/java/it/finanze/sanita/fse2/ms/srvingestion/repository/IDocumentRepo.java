package it.finanze.sanita.fse2.ms.srvingestion.repository;

import java.util.List;

import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.DocumentReferenceETY;

/** 
 * The interface for the Document Repository 
 *
 */
public interface IDocumentRepo {

	/**
	 * Processes a creation or update request on the staging database.
	 * 
	 * @param ety Document to insert.
	 * @return DocumentReferenceETY  The Document Reference Entity created 
	 * @throws OperationException  Generic MongoDB Exception 
	 */
	DocumentReferenceETY insert(DocumentReferenceETY ety) throws OperationException;

	/**
	 * Processes a deletion request with given identifier 
	 * 
	 * @param identifier The identifier of the document that needs to be deleted 
	 */
	void deleteByIdentifier(String identifier); 
	
	
	/**
	 * Returns a document from the staging database given its identifier. 
	 * 
	 * @param id  The id of the document to be searched 
	 * @return DocumentReferenceETY  Document Entity 
	 */
	DocumentReferenceETY findById(String id); 

	
	/**
	 * Returns all the documents from the staging database.
	 * 
	 * @return ArrayList  List of Document Reference Entities 
	 */
	public List<DocumentReferenceETY> findAll();





	
}
