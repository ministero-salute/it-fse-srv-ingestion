package it.finanze.sanita.fse2.ms.srvingestion.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.SendResult;

import it.finanze.sanita.fse2.ms.srvingestion.config.kafka.KafkaTopicCFG;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

/** 
 * Kafka Abstract SRV 
 *
 */
@Service
@Slf4j
public abstract class KafkaAbstractSRV {
	
	/**
	 * Transactional producer.
	 */
	@Autowired
	@Qualifier("txkafkatemplate")
	protected transient KafkaTemplate<String, String> txKafkaTemplate;

	/**
	 * Not transactional producer.
	 */
	@Autowired
	@Qualifier("notxkafkatemplate")
	protected transient KafkaTemplate<String, String> notxKafkaTemplate;

	/** 
	 * Kafka Topic Config 
	 */
	@Autowired
	protected transient KafkaTopicCFG kafkaTopicCFG;

	/** 
	 * Calls the kafkaSend function to send a message on a Kafka topic 
	 * 
	 * @param topic  The topic where the message is to be sent 
	 * @param key  The message key 
	 * @param value  The message value 
	 * @param trans  Boolean for transactionality 
	 * @return RecordMetadata  RecordMetadata
	 */
	public RecordMetadata sendMessage(String topic, String key, String value, boolean trans) {
		RecordMetadata out = null;
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);
		try {
			out = kafkaSend(producerRecord, trans);
		} catch (Exception e) {
			log.error("Send failed.", e);
			throw new BusinessException(e);
		}
		return out;
	}

	/** 
	 * Called by the sendMessage function, sends a message on a Kafka Topic. If transaction 
	 * boolean is set to true, the operation is performed within a local transaction
	 * 
	 * @param producerRecord  The ProducerRecord 
	 * @param trans  Boolean for transactionality 
	 * @return RecordMetadata  RecordMetadata
	 */
	@SuppressWarnings("unchecked")
	protected RecordMetadata kafkaSend(ProducerRecord<String, String> producerRecord, boolean trans) {
		RecordMetadata out = null;
		Object result = null;

		if (trans) {
			result = txKafkaTemplate.executeInTransaction(t -> {
				try {
					return t.send(producerRecord).get();
				} catch (InterruptedException e) {
					log.error("InterruptedException caught. Interrupting thread...");
					Thread.currentThread().interrupt();
					throw new BusinessException(e);
				} catch (Exception e) {
					throw new BusinessException(e);
				}
			});
		} else {
			notxKafkaTemplate.send(producerRecord);
		}

		if (result != null) {
			SendResult<String, String> sendResult = (SendResult<String, String>) result;
			out = sendResult.getRecordMetadata();
			log.info("Send success.");
		}

		return out;
	}
}