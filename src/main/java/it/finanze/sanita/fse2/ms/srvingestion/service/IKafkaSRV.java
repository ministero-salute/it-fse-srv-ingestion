package it.finanze.sanita.fse2.ms.srvingestion.service;

import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.KafkaException;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.Serializable;



/**
 * Interface for service used to handle kafka communications
 */
public interface IKafkaSRV extends Serializable {

	/**
	 * Send message over kafka topic
	 * @param topic
	 * @param key
	 * @param value
	 * @param trans
	 * @return
	 */
	RecordMetadata sendMessage(String topic, ProcessorOperationEnum key, String value, boolean trans);
 

	/**
	 * Send message to publisher microservice over kafka topic
	 * @param transactionId
	 * @throws KafkaException 
	 */
	void notifyDataProcessor(String topic, String transactionId, ProcessorOperationEnum key) throws KafkaException;
	

}
