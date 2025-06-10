/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package it.finanze.sanita.fse2.ms.srvingestion.service;

import java.util.List;

import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.BusinessException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.ConnectionRefusedException;
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
     * @param dto The document to insert
     * @return DocumentReferenceETY The document entity
     * @throws OperationException     If a data-layer error occurs
     * @throws EmptyDocumentException An exception thrown when a document to be
     *                                inserted is empty
     */
    StagingDocumentETY create(DocumentDTO dto, String wii) throws OperationException, EmptyDocumentException;

    /**
     * Replace one Document Creation Request in the staging database
     * 
     * @param dto The document to insert
     * @return DocumentReferenceETY The document entity
     * @throws OperationException        If a data-layer error occurs
     * @throws EmptyDocumentException    An exception thrown when a document to be
     *                                   inserted is empty
     * @throws DocumentNotFoundException
     */
    StagingDocumentETY replace(DocumentDTO dto, String wii)
            throws OperationException, EmptyDocumentException, DocumentNotFoundException;

    /**
     * Updates one Document in the Fhir Server, calling the Data Processor Service
     * 
     * @param dto The document to update
     * @throws OperationException        If a data-layer error occurs
     * @throws EmptyDocumentException    An exception thrown when a document to be
     *                                   inserted is empty
     * @throws DocumentNotFoundException
     */
    Boolean update(final DocumentDTO dto)
            throws EmptyDocumentException, DocumentNotFoundException, ConnectionRefusedException, BusinessException;

    /**
     * Deletes one Document in the Fhir Server, calling the Data Processor Service
     * 
     * @param id The id of the document to delete
     * @throws OperationException        If a data-layer error occurs
     * @throws EmptyDocumentException    An exception thrown when a document to be
     *                                   inserted is empty
     * @throws DocumentNotFoundException
     */
    Boolean delete(final String id) throws DocumentNotFoundException, ConnectionRefusedException, BusinessException;

    /**
     * Retrieves a document from the staging database given its Mongo ID
     * 
     * @param id The id of the document to be retrieved
     * @return DocumentReferenceDTO The retrieved document
     * @throws DocumentNotFoundException An exception thrown when the document is
     *                                   not found on MongoDB
     */
    DocumentDTO getDocumentById(String id) throws DocumentNotFoundException;

    /**
     * Retrieves the list of all documents from the staging database
     * 
     * @return List The list of all documents retrieved from MongoDB
     */
    List<DocumentDTO> getDocuments();

}
