/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.service;

import java.util.List;

import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;

/** 
 * Interface for Document Service 
 *
 */
public interface IDocumentSRV {
	
    /**
     * Inserts one Document Creation Request in the staging database
     * 
     * @param dto  The document to insert 
     * @return DocumentReferenceETY  The document entity 
     * @throws OperationException  If a data-layer error occurs
     * @throws EmptyDocumentException  An exception thrown when a document to be inserted is empty 
     */
	StagingDocumentETY insert(DocumentDTO dto, String wii)throws OperationException, EmptyDocumentException;

    /**
     * Retrieves a document from the staging database given its Mongo ID
     * 
     * @param id  The id of the document to be retrieved 
     * @return DocumentReferenceDTO  The retrieved document 
     * @throws DocumentNotFoundException  An exception thrown when the document is not found on MongoDB 
     */
	DocumentDTO getDocumentById(String id) throws DocumentNotFoundException;

    /**
     * Retrieves the list of all documents from the staging database
     * 
     * @return List  The list of all documents retrieved from MongoDB 
     */
	List<DocumentDTO> getDocuments();

}
