package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

/** 
 * Event Status Enum 
 *
 */
public enum EventStatusEnum {

	SUCCESS("Success"), 
	ERROR("Error");

	/** 
	 * The error name 
	 */
	@Getter
	private String name;

	private EventStatusEnum(String inName) {
		name = inName;
	}

} 
