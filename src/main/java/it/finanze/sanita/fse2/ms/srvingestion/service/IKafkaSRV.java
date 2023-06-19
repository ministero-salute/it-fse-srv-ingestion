/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
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
