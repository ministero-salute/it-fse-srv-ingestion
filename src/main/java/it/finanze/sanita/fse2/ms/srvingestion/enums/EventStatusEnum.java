package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

public enum EventStatusEnum {

	SUCCESS("Success"), 
	ERROR("Error");

	@Getter
	private String name;

	private EventStatusEnum(String inName) {
		name = inName;
	}

} 
