package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

/** 
 * Represents the operation to be executed on the document 
 *
 */
public enum ProcessorOperationEnum {

	PUBLISH("PUBLISH"),
	DELETE("DELETE"),
	REPLACE("REPLACE"),
	UPDATE("UPDATE");
	
	/** 
	 * The name of the operation 
	 */
	@Getter
	private String name;
	
	private ProcessorOperationEnum(String pname) {
		name = pname;
	}

}
