package it.finanze.sanita.fse2.ms.srvingestion.service.impl;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.config.kafka.KafkaPropertiesCFG;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.BusinessException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.KafkaException;
import it.finanze.sanita.fse2.ms.srvingestion.service.IKafkaSRV;
import it.finanze.sanita.fse2.ms.srvingestion.utility.EncryptDecryptUtility;
import lombok.extern.slf4j.Slf4j;

/**
 * Kafka management service implementation. 
 * 
 * @author vincenzoingenito
 *
 */
@Service
@Slf4j
public class KafkaSRV implements IKafkaSRV {



	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = -1952431076996493492L; 
	

	@Autowired
	private KafkaPropertiesCFG kafkaPropCFG;
	
	/**
	 * Transactional producer.
	 */
	@Autowired
	@Qualifier("txkafkatemplate")
	private transient KafkaTemplate<String, String> txKafkaTemplate;
	
	/**
	 * Not transactional producer.
	 */
	@Autowired
	@Qualifier("notxkafkatemplate")
	private transient KafkaTemplate<String, String> notxKafkaTemplate;
 
	@Override
	public RecordMetadata sendMessage(String topic, ProcessorOperationEnum key, String value, boolean trans) {
		RecordMetadata out = null;
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key.getName(), value);
		try { 
			log.debug(Constants.Logs.KAFKA_SENDING_MESSAGE); 
			out = kafkaSend(producerRecord, trans);
		} catch (Exception e) {
			log.error(Constants.Logs.KAFKA_SENDING_FAILED, e); 
			throw new BusinessException(e);
		}   
		return out;
	} 

	@SuppressWarnings("unchecked")
	private RecordMetadata kafkaSend(ProducerRecord<String, String> producerRecord, boolean trans) {
		RecordMetadata out = null;
		Object result = null;

		if (trans) {  
			result = txKafkaTemplate.executeInTransaction(t -> { 
				try {
					return t.send(producerRecord).get();
				} catch(InterruptedException e) {
					Thread.currentThread().interrupt(); 
					throw new BusinessException(e); 
				} catch (Exception e) {
					throw new BusinessException(e);
				}  
			});  
		} else { 
			notxKafkaTemplate.send(producerRecord);
		} 

		if(result != null) {
			SendResult<String,String> sendResult = (SendResult<String, String>) result;
			out = sendResult.getRecordMetadata();
			log.debug(Constants.Logs.KAFKA_SEND_SUCCESS); 
		}

		return out;
	}
 
	
	@Override
	// transform key in ENUM operation
	public void notifyDataProcessor(final String topic, final String transactionId, final ProcessorOperationEnum key) throws KafkaException {
		try {
			String message = EncryptDecryptUtility.encrypt(kafkaPropCFG.getCrypto(), transactionId);
			sendMessage(topic, key, message,true);
		} catch (Exception e) {
			log.error(Constants.Logs.KAFKA_SEND_FAILED, e); 
			throw new KafkaException(Constants.Logs.KAFKA_SEND_FAILED, e);
		}

	}
}

