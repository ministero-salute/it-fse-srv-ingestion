package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

public enum ProcessorOperationEnum {

	PUBLISH("PUBLISH"),
	DELETE("DELETE"),
	REPLACE("REPLACE"),
	UPDATE("UPDATE");
	
	@Getter
	private String name;
	
	private ProcessorOperationEnum(String pname) {
		name = pname;
	}

}
