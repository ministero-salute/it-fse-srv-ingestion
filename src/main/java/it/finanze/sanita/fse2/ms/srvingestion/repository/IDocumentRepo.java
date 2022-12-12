/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.repository;

import java.util.List;

import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;

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
	StagingDocumentETY insert(StagingDocumentETY ety) throws OperationException;
	
	/**
	 * Returns a document from the staging database given its identifier. 
	 * 
	 * @param id  The id of the document to be searched 
	 * @return DocumentReferenceETY  Document Entity 
	 */
	StagingDocumentETY findById(String id);
	
	/**
	 * Returns all the documents from the staging database.
	 * 
	 * @return ArrayList  List of Document Reference Entities 
	 */
	public List<StagingDocumentETY> findAll();

}
