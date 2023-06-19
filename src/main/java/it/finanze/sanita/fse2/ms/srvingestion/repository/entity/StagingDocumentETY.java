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
package it.finanze.sanita.fse2.ms.srvingestion.repository.entity;

import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MAX_SIZE;
import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MIN_SIZE;

import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import lombok.Data;
import lombok.NoArgsConstructor; 


@Document(collection = "#{@ingestionStagingBean}")
@Data
@NoArgsConstructor
public class StagingDocumentETY {

	/** 
	 * Mongo ID 
	 */
	@Id
	private String id; 
	
	/** 
	 * The document identifier 
	 */
	@Field("identifier")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private String identifier;      
	
	/** 
	 * The operation to be executed on the document 
	 */
	@Field("operation")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private ProcessorOperationEnum operation;  

	/** 
	 * The bundle
	 */
	@Field("document")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private org.bson.Document document;
	
	/** 
	 * The insertion date of the document 
	 */
	@Field("insertion_date")
	private Date insertionDate;
	
	/** 
	 * Workflow instance id. 
	 */
	@Field("workflow_instance_id")
	private String workflowInstanceId;
	
}
