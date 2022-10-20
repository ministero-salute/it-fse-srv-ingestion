/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** 
 * Kafka Message DTO 
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessageDTO {

	/* 
	 * The transaction ID of the message 
	 */
	private String txId; 
	
	/* 
	 * The message to be sent 
	 */
	private String message; 
	
}
