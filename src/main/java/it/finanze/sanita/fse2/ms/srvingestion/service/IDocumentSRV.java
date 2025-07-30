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

import it.finanze.sanita.fse2.ms.srvingestion.dto.UdpDocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.request.SendToUdpDocumentRequestDTO;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.KafkaException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;

/**
 * Interface for Document Service
 *
 */
public interface IDocumentSRV {

    /**
     * Inserts one Document Creation Request in the staging database
     */
    boolean publish(SendToUdpDocumentRequestDTO dto, String wii)
            throws OperationException, EmptyDocumentException, KafkaException;

    /**
     * Replace one Document Creation Request in the staging database
     */
    boolean replace(SendToUdpDocumentRequestDTO dto, String wii)
            throws DocumentNotFoundException, EmptyDocumentException, OperationException, KafkaException;

    /**
     * Updates one Document in the Fhir Server, calling the Data Processor Service
     * 
     */
    boolean update(SendToUdpDocumentRequestDTO dto);

    /**
     * Deletes one Document in the Fhir Server, calling the Data Processor Service
     * 
     */
    boolean delete(String identifier) throws DocumentNotFoundException;

    /**
     * Retrieves a document from the staging database given its Mongo ID
     * 
     * @param id The id of the document to be retrieved
     * @return DocumentReferenceDTO The retrieved document
     * @throws DocumentNotFoundException An exception thrown when the document is
     *                                   not found on MongoDB
     */
    UdpDocumentDTO getDocumentById(String id) throws DocumentNotFoundException;

    /**
     * Retrieves the list of all documents from the staging database
     * 
     * @return List The list of all documents retrieved from MongoDB
     */
    List<UdpDocumentDTO> getDocuments();

}
