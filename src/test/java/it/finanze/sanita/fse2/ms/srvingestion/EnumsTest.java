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
package it.finanze.sanita.fse2.ms.srvingestion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ErrorLogEnum;
import it.finanze.sanita.fse2.ms.srvingestion.enums.OperationLogEnum;
import it.finanze.sanita.fse2.ms.srvingestion.enums.UIDModeEnum;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Constants.Profile.TEST)
class EnumsTest {

	@Test
    @DisplayName("test UIDModeEnum")
    void UidModeEnumTest() {
        int hostId = UIDModeEnum.HOSTNAME_UUID.getId();
        String hostDesc = UIDModeEnum.HOSTNAME_UUID.getDescription();
        Assertions.assertEquals("HOSTNAME_UUID", hostDesc);
        Assertions.assertEquals(2, hostId);
        
        int uuId = UIDModeEnum.UUID_UUID.getId();
        String uuidDesc = UIDModeEnum.UUID_UUID.getDescription();
        Assertions.assertEquals("UUID_UUID", uuidDesc);
        Assertions.assertEquals(3, uuId);
        
        int ipId = UIDModeEnum.IP_UUID.getId();
        String ipDesc = UIDModeEnum.IP_UUID.getDescription();
        Assertions.assertEquals("IP_UUID", ipDesc);
        Assertions.assertEquals(1, ipId);
	}

    @Test
    @DisplayName("test OperationLogEnum")
    void operationLogEnumTest() {
        String getDesc = OperationLogEnum.GET_DOCUMENT.getDescription();
        String getId = OperationLogEnum.GET_DOCUMENT.getCode();
        Assertions.assertEquals("GET-DOCUMENT", getId);
        Assertions.assertEquals("Recupero Documento CDA2", getDesc);
        
        String kafkaDesc = OperationLogEnum.KAFKA_SENDING_MESSAGE.getDescription();
        String kafkaId = OperationLogEnum.KAFKA_SENDING_MESSAGE.getCode();
        Assertions.assertEquals("KAFKA-SENDING-MESSAGE", kafkaId);
        Assertions.assertEquals("Invio Messaggio su Kafka", kafkaDesc);

        String updateDesc = OperationLogEnum.UPDATE_METADATA_CDA2.getDescription();
        String updateId = OperationLogEnum.UPDATE_METADATA_CDA2.getCode();
        Assertions.assertEquals("UPD-MTD-CDA2", updateId);
        Assertions.assertEquals("Update metadata CDA2", updateDesc);

        String deleteDesc = OperationLogEnum.DELETE_CDA2.getDescription();
        String deleteId = OperationLogEnum.DELETE_CDA2.getCode();
        Assertions.assertEquals("DEL-CDA2", deleteId);
        Assertions.assertEquals("Delete CDA2", deleteDesc);

        String replaceDesc = OperationLogEnum.REPLACE_CDA2.getDescription();
        String replaceId = OperationLogEnum.REPLACE_CDA2.getCode();
        Assertions.assertEquals("REP-CDA2", replaceId);
        Assertions.assertEquals("Replace CDA2", replaceDesc);

        String pubDesc = OperationLogEnum.PUB_CDA2.getDescription();
        String pubId = OperationLogEnum.PUB_CDA2.getCode();
        Assertions.assertEquals("PUB-CDA2", pubId);
        Assertions.assertEquals("Pubblicazione CDA2", pubDesc);

    }
        
        
    @Test
    @DisplayName("test ErrorLogEnum")
    void testErrorLogEnum() {
        String getCode = ErrorLogEnum.KO_DOCUMENT_GET.getCode();
        String getDesc = ErrorLogEnum.KO_DOCUMENT_GET.getDescription();
        Assertions.assertEquals("KO-DOCUMENT-GET", getCode);
        Assertions.assertEquals("Error while getting document", getDesc);
        
        String createCode = ErrorLogEnum.KO_DOCUMENT_CREATE.getCode();
        String createDesc = ErrorLogEnum.KO_DOCUMENT_CREATE.getDescription();
        Assertions.assertEquals("KO-DOCUMENT-CREATE", createCode);
        Assertions.assertEquals("Error while creating document", createDesc);
        
        String sendCode = ErrorLogEnum.KO_KAFKA_SENDING_MESSAGE.getCode();
        String sendDesc = ErrorLogEnum.KO_KAFKA_SENDING_MESSAGE.getDescription();
        Assertions.assertEquals("KO-KAFKA-SENDING-MESSAGE", sendCode);
        Assertions.assertEquals("Error while sending message on Kafka topic", sendDesc);
    }

}
