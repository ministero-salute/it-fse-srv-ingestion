package it.finanze.sanita.fse2.ms.srvingestion.service.impl;

import org.springframework.stereotype.Service;

import it.finanze.sanita.fse2.ms.srvingestion.service.ILogSRV;
import it.finanze.sanita.fse2.ms.srvingestion.service.KafkaAbstractSRV;

/**
 * 
 * @author vincenzoingenito
 *
 *         Kafka Log service.
 */
@Service
public class LogSRV extends KafkaAbstractSRV implements ILogSRV {

	@Override
	public void sendLoggerStatus(final String log) {
		sendMessage(kafkaTopicCFG.getLogTopic(), "KEY", log, true);
	}

}
