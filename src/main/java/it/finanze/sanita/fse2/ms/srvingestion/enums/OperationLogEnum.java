package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

public enum OperationLogEnum implements ILogEnum {

	GET_DOCUMENT("GET-DOCUMENT", "Recupero Documento CDA2"),
	KAFKA_SENDING_MESSAGE("KAFKA-SENDING-MESSAGE", "Invio Messaggio su Kafka"),
	UPDATE_METADATA_CDA2("UPD-MTD-CDA2","Update metadata CDA2"),
	DELETE_CDA2("DEL-CDA2","Delete CDA2"),
	REPLACE_CDA2("REP-CDA2", "Replace CDA2"),
	PUB_CDA2("PUB-CDA2", "Pubblicazione CDA2");

	@Getter
	private String code;

	@Getter
	private String description;

	private OperationLogEnum(String inCode, String inDescription) {
		code = inCode;
		description = inDescription;
	}

}
