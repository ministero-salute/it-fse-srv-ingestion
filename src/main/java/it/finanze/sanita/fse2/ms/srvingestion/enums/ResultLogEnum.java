package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

public enum ResultLogEnum implements ILogEnum {

	OK("OK", "Operazione eseguita con successo"),
	KO("KO", "Errore nell'esecuzione dell'operazione"); 

	@Getter
	private String code;

	@Getter
	private String description;

	private ResultLogEnum(String inCode, String inDescription) {
		code = inCode;
		description = inDescription;
	}

}
