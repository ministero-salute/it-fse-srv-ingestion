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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.finanze.sanita.fse2.ms.srvingestion.config.kafka.KafkaTopicCFG;
import lombok.Data;

/**
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

