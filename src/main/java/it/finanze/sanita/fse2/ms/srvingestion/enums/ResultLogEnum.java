package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

/** 
 * Enum that represents the result of the operation 
 *
 */
public enum ResultLogEnum implements ILogEnum {

	OK("OK", "Operazione eseguita con successo"),
	KO("KO", "Errore nell'esecuzione dell'operazione"); 

	/** 
	 * The Operation Code 
	 */
	@Getter
	private String code;

	/** 
	 * The Operation Description 
	 */
	@Getter
	private String description;

	private ResultLogEnum(String inCode, String inDescription) {
		code = inCode;
		description = inDescription;
	}

}
