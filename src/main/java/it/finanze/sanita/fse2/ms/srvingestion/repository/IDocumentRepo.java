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
