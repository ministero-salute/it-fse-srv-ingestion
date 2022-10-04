package it.finanze.sanita.fse2.ms.srvingestion.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.finanze.sanita.fse2.ms.srvingestion.dto.LogDTO;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ILogEnum;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ResultLogEnum;
import it.finanze.sanita.fse2.ms.srvingestion.service.ILogSRV;
import it.finanze.sanita.fse2.ms.srvingestion.utility.StringUtility;
import lombok.extern.slf4j.Slf4j;

/** 
 * Kafka Logger implementation. Implements Structured Logs,
 * at all logging levels 
 * 
 * @author Guido Rocco  
 */ 
@Service
@Slf4j
public class KafkaLoggerSRV {

	@Autowired
	private ILogSRV logSRV;

	/* 
	 * Specify here the format for the dates 
	 */
	private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS"); 

	/* 
	 * Implements structured logs, at trace level 
	 */
	public void trace(String message, ILogEnum operation, 
			ResultLogEnum result, Date startDateOperation) {

		LogDTO logDTO = LogDTO.builder().
				message(message).
				operation(operation.getCode()).
				op_result(result.getCode()).
				op_timestamp_start(dateFormat.format(startDateOperation)).
				op_timestamp_end(dateFormat.format(new Date())).
				build();

		final String jsonObject = StringUtility.toJSON(logDTO);
		log.trace(jsonObject);
		logSRV.sendLoggerStatus(jsonObject);
	} 

	/* 
	 * Implements structured logs, at debug level 
	 */
	public void debug(String message,  ILogEnum operation,  
			ResultLogEnum result, Date startDateOperation) {

		LogDTO logDTO = LogDTO.builder().
				message(message).
				operation(operation.getCode()).
				op_result(result.getCode()).
				op_timestamp_start(dateFormat.format(startDateOperation)).
				op_timestamp_end(dateFormat.format(new Date())).
				build();
				
		final String jsonObject = StringUtility.toJSON(logDTO);
		log.debug(jsonObject);
		logSRV.sendLoggerStatus(jsonObject);
	} 

	/* 
	 * Implements structured logs, at infos level 
	 */
	public void info(String message, ILogEnum operation,  
			ResultLogEnum result, Date startDateOperation) {

		LogDTO logDTO = LogDTO.builder().
				message(message).
				operation(operation.getCode()).
				op_result(result.getCode()).
				op_timestamp_start(dateFormat.format(startDateOperation)).
				op_timestamp_end(dateFormat.format(new Date())).
				build();
				
		final String jsonObject = StringUtility.toJSON(logDTO);
		log.info(jsonObject);
		logSRV.sendLoggerStatus(jsonObject);
	} 

	/* 
	 * Implements structured logs, at warn level 
	 */
	public void warn(String message, ILogEnum operation,  
			ResultLogEnum result, Date startDateOperation) {

		LogDTO logDTO = LogDTO.builder().
				message(message).
				operation(operation.getCode()).
				op_result(result.getCode()).
				op_timestamp_start(dateFormat.format(startDateOperation)).
				op_timestamp_end(dateFormat.format(new Date())).
				build();

		final String jsonObject = StringUtility.toJSON(logDTO);
		log.warn(jsonObject);
		logSRV.sendLoggerStatus(jsonObject);
	} 

	/* 
	 * Implements structured logs, at error level 
	 */
	public void error(String message, ILogEnum operation,  
			ResultLogEnum result, Date startDateOperation,
			ILogEnum error) {

		LogDTO logDTO = LogDTO.builder().
				message(message).
				operation(operation.getCode()).
				op_result(result.getCode()).
				op_timestamp_start(dateFormat.format(startDateOperation)).
				op_timestamp_end(dateFormat.format(new Date())).
				build();

		final String jsonObject = StringUtility.toJSON(logDTO);
		log.error(jsonObject);
		logSRV.sendLoggerStatus(jsonObject);
	}

}