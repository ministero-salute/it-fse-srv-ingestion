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
	
	
	private EventTypeEnum eventType;
	
	private Date eventDate;
	
	private EventStatusEnum eventStatus;
	
	private String message;
	
	
} 


