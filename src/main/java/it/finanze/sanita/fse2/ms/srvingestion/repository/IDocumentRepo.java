package it.finanze.sanita.fse2.ms.srvingestion.repository;

import java.util.List;

import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.DocumentReferenceETY;

public interface IDocumentRepo {

	/**
	 * Insert one or more documents on the staging database.
	 * 
	 * @param ety Document to insert.
	 * @return 
	 * @throws OperationException 
	 */
	DocumentReferenceETY insert(DocumentReferenceETY ety) throws OperationException;

	/**
	 * Returns all the documents from the staging database.
	 * 
	 * @param identifier The identifier of the document to delete
	 */
	void deleteByIdentifier(String identifier); 
	
	
	/**
	 * Returns a document from the staging database given its identifier. 
	 * 
	 * @return ArrayList<DocumentREferenceETY> .
	 */
	DocumentReferenceETY findById(String id); 

	
	/**
	 * Returns all the documents from the staging database.
	 * 
	 * @return ArrayList<DocumentREferenceETY> .
	 */
	public List<DocumentReferenceETY> findAll();





	
}
