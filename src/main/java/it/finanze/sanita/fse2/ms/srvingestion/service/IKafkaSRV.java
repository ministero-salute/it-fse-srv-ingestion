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
	 * 
	 * @param topic  The Kafka topic where the message is published  
	 * @param key  The message key 
	 * @param value  The message value 
	 * @param trans  Boolean for transactionality 
	 * @return RecordMetadata  RecordMetadata
	 */
	RecordMetadata sendMessage(String topic, ProcessorOperationEnum key, String value, boolean trans);


	 /** 
	  * Send message to Data Processor microservice over kafka topic 
	  * 
	  * @param topic  The Kafka topic where the message is published 
	  * @param transactionId  The transaction ID 
	  * @param key  The message key 
	  * @throws KafkaException  Generic Kafka Exception 
	  */
	void notifyDataProcessor(String topic, String transactionId, ProcessorOperationEnum key) throws KafkaException;
	

}
