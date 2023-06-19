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
package it.finanze.sanita.fse2.ms.srvingestion.dto;


import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MAX_SIZE;
import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MIN_SIZE;

import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.finanze.sanita.fse2.ms.srvingestion.enums.PriorityTypeEnum;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Document DTO class 
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {

	/** 
	 * The doc identifier 
	 */
	@JsonProperty("identifier")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private String identifier; 
	
	/** 
	 * The operation to be executed (CREATE, UPDATE, REPLACE or DELETE) 
	 */
	@JsonProperty("operation")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private ProcessorOperationEnum operation;    

	/** 
	 * The JSON string of the document 
	 */
	@JsonProperty("jsonString")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private String jsonString;

	/** 
	 * The Priority Type (HIGH, MEDIUM or LOW) 
	 */
	@JsonProperty("priorityType")
	private PriorityTypeEnum priorityTypeEnum;
	
	/** 
	 * The Insertion Date 
	 */
	@JsonProperty("insertionDate")
	private Date insertionDate; 
	
}
