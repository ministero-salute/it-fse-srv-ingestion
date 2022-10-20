/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

/** 
 * Event Type Enum 
 *
 */
public enum EventTypeEnum {

	INGESTION("Ingestion"),
	KAFKA("Kafka"),
	GENERIC_ERROR("Generic error from Ingestion");

	/** 
	 * The event type 
	 */
	@Getter
	private String name;

	private EventTypeEnum(String inName) {
		name = inName;
	}

} 
