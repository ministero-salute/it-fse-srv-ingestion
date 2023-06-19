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
package it.finanze.sanita.fse2.ms.srvingestion.client;

import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;

/**
 * Interface for Data Processor Client
 *
 */
public interface IDataProcessorClient {

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
