package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

public enum EventTypeEnum {

	INGESTION("Ingestion"),
	KAFKA("Kafka"),
	GENERIC_ERROR("Generic error from Ingestion");

	@Getter
	private String name;

	private EventTypeEnum(String inName) {
		name = inName;
	}

} 
