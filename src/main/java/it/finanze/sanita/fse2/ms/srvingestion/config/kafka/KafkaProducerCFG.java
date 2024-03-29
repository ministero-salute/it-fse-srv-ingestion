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
package it.finanze.sanita.fse2.ms.srvingestion.config.kafka;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import lombok.extern.slf4j.Slf4j;

/**
 *	Kafka producer configuration.
 */
@Configuration
@Slf4j
public class KafkaProducerCFG {
	
	/**
	 *	Kafka properties.
	 */
	@Autowired
	private KafkaPropertiesCFG kafkaPropCFG;
	
	/**
	 *	Kafka producer properties.
	 */
	@Autowired
	private KafkaProducerPropertiesCFG kafkaProducerPropCFG;
 

	/**
	 * Kafka Producer Config 
	 * @return Map  Producer Config 
	 */
	@Bean 
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		
		InetAddress id = getLocalHost();
		
		props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerPropCFG.getClientId() + "-tx" + "-" + id );
		props.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerPropCFG.getRetries());
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerPropCFG.getProducerBootstrapServers());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerPropCFG.getKeySerializer());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerPropCFG.getValueSerializer());
		props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, id + "-" + kafkaProducerPropCFG.getTransactionalId());
		props.put(ProducerConfig.ACKS_CONFIG,kafkaProducerPropCFG.getAck());
		props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,kafkaProducerPropCFG.getIdempotence());
		
		//SSL
		if(kafkaPropCFG.isEnableSSL()) {
            props.put("security.protocol", kafkaPropCFG.getProtocol());
		    props.put("sasl.mechanism", kafkaPropCFG.getMechanism());
		    props.put("sasl.jaas.config", kafkaPropCFG.getConfigJaas());
		    props.put("ssl.truststore.location", kafkaPropCFG.getTrustoreLocation());  
            props.put("ssl.truststore.password", String.valueOf(kafkaPropCFG.getTrustorePassword())); 
		}
		  
		return props;
	}

	/**
	 * @param id  The ID of the local host
	 * @return InetAddress  InetAddress 
	 */
	private InetAddress getLocalHost() {
		InetAddress id = null;
		try {
			id = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			log.error("Errore durante il recupero InetAddress.getLocalHost()", e);
		}
		return id;
	}
	
	/**
	 * Producer Factory 
	 * @return ProducerFactory  ProducerFactory
	 */
	@Bean
	@Qualifier("txkafkatemplateFactory") 
	public ProducerFactory<String, String> producerFactory() {
		log.info("Initializing producer factory");
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}
	
	/**
	 * KafkaTemplate 
	 * 
	 * @return KafkaTemplate  KafkaTemplate
	 */
	@Bean
	@Qualifier("txkafkatemplate") 
	public KafkaTemplate<String, String> txKafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}


	/**
	 * Non transactional producer config. 
	 * 
	 * @return Map The Producer Config Props 
	 */
	@Bean 
	public Map<String, Object> producerWithoutTransactionConfigs() {
		Map<String, Object> props = new HashMap<>();
		
		props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerPropCFG.getClientId()+ "-noTx");
		props.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerPropCFG.getRetries());
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerPropCFG.getProducerBootstrapServers());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerPropCFG.getKeySerializer());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerPropCFG.getValueSerializer());
		
		//SSL
		if(kafkaPropCFG.isEnableSSL()) {
		    props.put("security.protocol", kafkaPropCFG.getProtocol());
		    props.put("sasl.mechanism", kafkaPropCFG.getMechanism());
		    props.put("sasl.jaas.config", kafkaPropCFG.getConfigJaas());
		    props.put("ssl.truststore.location", kafkaPropCFG.getTrustoreLocation());
		    props.put("ssl.truststore.password", String.valueOf(kafkaPropCFG.getTrustorePassword())); 
		}

		return props;
	}
	
	/** 
	 * Non transactional producer 
	 * 
	 * @return ProducerFactory  The ProducerFactory
	 */
	@Bean
	@Qualifier("notxkafkatemplateFactory") 
	public ProducerFactory<String, String> producerFactoryWithoutTransaction() {
		log.info("Initializing producer factory");
		return new DefaultKafkaProducerFactory<>(producerWithoutTransactionConfigs());
	}
	
	/**
	 * Non transactional Kafka Template 
	 * 
	 * @return KafkaTemplate  KafkaTemplate
	 */
	@Bean
	@Qualifier("notxkafkatemplate") 
	public KafkaTemplate<String, String> notxKafkaTemplate() {
		return new KafkaTemplate<>(producerFactoryWithoutTransaction());
	}
 
	 
} 

