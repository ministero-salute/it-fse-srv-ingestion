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
 * The Enum containing the Operation ID and Description 
 * in the Structured Log 
 *
 */
public enum OperationLogEnum implements ILogEnum {

	GET_DOCUMENT("GET-DOCUMENT", "Recupero Documento CDA2"),
	KAFKA_SENDING_MESSAGE("KAFKA-SENDING-MESSAGE", "Invio Messaggio su Kafka"),
	UPDATE_METADATA_CDA2("UPD-MTD-CDA2","Update metadata CDA2"),
	DELETE_CDA2("DEL-CDA2","Delete CDA2"),
	REPLACE_CDA2("REP-CDA2", "Replace CDA2"),
	PUB_CDA2("PUB-CDA2", "Pubblicazione CDA2");

	/** 
	 * The enum code 
	 */
	@Getter
	private String code;

	/** 
	 * The enum description 
	 */
	@Getter
	private String description;

	private OperationLogEnum(String inCode, String inDescription) {
		code = inCode;
		description = inDescription;
	}

}
