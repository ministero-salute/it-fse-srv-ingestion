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

import lombok.Builder;
import lombok.Data;

/** 
 * The DTO for Structured Logs 
 * 
 */
@Builder
@Data
public class LogDTO {

	/** 
	 * Log Type 
	 */
	final String log_type = "eds-structured-log";

	/** 
	 * Log Message 
	 */
	private String message;

	/** 
	 * The operation that is executed
	 */
	private String operation;

	/** 
	 * The operation result 
	 */
	private String op_result;

	/** 
	 * The operation start timestamp  
	 */
	private String op_timestamp_start;

	/** 
	 * The operationt end timestamp  
	 */
	private String op_timestamp_end;

	/** 
	 * The operation error, if present 
	 */
	private String op_error;

	/** 
	 * The operation error description, if present 
	 */
	private String op_error_description;

}