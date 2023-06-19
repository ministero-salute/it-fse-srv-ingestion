/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;


/** 
 * The Enum containing the Error Types in the Structured Log 
 *
 */
public enum ErrorLogEnum implements ILogEnum {

	KO_DOCUMENT_CREATE("KO-DOCUMENT-CREATE", "Error while creating document"),
	KO_DOCUMENT_GET("KO-DOCUMENT-GET", "Error while getting document"),
	KO_KAFKA_SENDING_MESSAGE("KO-KAFKA-SENDING-MESSAGE", "Error while sending message on Kafka topic");

	/** 
	 * The error code 
	 */
	@Getter
	private String code;

	/** 
	 * The error description 
	 */
	@Getter
	private String description;

	private ErrorLogEnum(String inCode, String inDescription) {
		code = inCode;
		description = inDescription;
	}

}
