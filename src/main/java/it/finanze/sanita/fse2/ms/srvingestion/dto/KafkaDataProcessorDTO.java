/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.dto;

import java.util.Date;

import it.finanze.sanita.fse2.ms.srvingestion.enums.EventStatusEnum;
import it.finanze.sanita.fse2.ms.srvingestion.enums.EventTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Content of a Kafka message
 */
@Getter
@Setter
@Builder
public class KafkaDataProcessorDTO implements AbstractDTO {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 7080680277816570116L;
	
	/** 
	 * The Event Type 
	 */
	private EventTypeEnum eventType;
	
	/** 
	 * The Event Date 
	 */
	private Date eventDate;
	
	/** 
	 * The Event Status 
	 */
	private EventStatusEnum eventStatus;
	
	/** 
	 * The Kafka message 
	 */
	private String message;
	
	
} 


