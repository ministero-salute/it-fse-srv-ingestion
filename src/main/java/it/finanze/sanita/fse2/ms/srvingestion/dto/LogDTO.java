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