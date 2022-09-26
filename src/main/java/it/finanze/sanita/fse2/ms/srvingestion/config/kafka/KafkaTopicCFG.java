package it.finanze.sanita.fse2.ms.srvingestion.config.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.finanze.sanita.fse2.ms.srvingestion.config.kafka.KafkaTopicCFG;
import lombok.Data;

/**
 *	@author vincenzoingenito
 *
 *	Kafka topic configuration.
 */
@Data
@Component
public class KafkaTopicCFG {

	/**
	 * Topic data processor.
	 */
	@Value("${kafka.dataprocessor.publication.topic}")
	private String ingestionDataProcessorPublicationTopic;

	/**
	 * Topic data processor.
	 */
	@Value("${kafka.dataprocessor.generic.topic}")
	private String ingestionDataProcessorGenericTopic;
	
	/**
	 * Log publisher.
	 */
	@Value("${kafka.log.base-topic}")
	private String logTopic;
} 

