package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

public enum ErrorLogEnum implements ILogEnum {

	KO_DOCUMENT_CREATE("KO-DOCUMENT-CREATE", "Error while creating document"),
	KO_DOCUMENT_GET("KO-DOCUMENT-GET", "Error while getting document"),
	KO_KAFKA_SENDING_MESSAGE("KO-KAFKA-SENDING-MESSAGE", "Error while sending message on Kafka topic");

	@Getter
	private String code;

	@Getter
	private String description;

	private ErrorLogEnum(String inCode, String inDescription) {
		code = inCode;
		description = inDescription;
	}

}
