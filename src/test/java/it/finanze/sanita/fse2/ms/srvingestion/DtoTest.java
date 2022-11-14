/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import it.finanze.sanita.fse2.ms.srvingestion.base.AbstractTest;
import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.KafkaDataProcessorDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.KafkaMessageDTO;
import it.finanze.sanita.fse2.ms.srvingestion.enums.EventStatusEnum;
import it.finanze.sanita.fse2.ms.srvingestion.enums.EventTypeEnum;


@SpringBootTest
@ComponentScan
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(Constants.Profile.TEST)
class DtoTest extends AbstractTest {

	public final String TEST_MESSAGE = "testMessage"; 
	public final String TEST_TX_ID = "edda98df-0bef-4f1e-9e71-b0f54a9fd018"; 
	
	
	@Test
	void kafkaDataProcessorDtoTest() {
		Date eventDate = new Date(); 
		KafkaDataProcessorDTO dto = KafkaDataProcessorDTO.builder()
				.eventDate(eventDate).eventType(EventTypeEnum.INGESTION)
				.eventStatus(EventStatusEnum.SUCCESS).message(TEST_MESSAGE).build(); 
		
		
		assertEquals(KafkaDataProcessorDTO.class, dto.getClass()); 
		assertEquals(Date.class, dto.getEventDate().getClass()); 
		assertEquals(EventTypeEnum.class, dto.getEventType().getClass()); 
		assertEquals(EventStatusEnum.class, dto.getEventStatus().getClass()); 
		assertEquals(String.class, dto.getMessage().getClass()); 
		
		assertEquals(EventTypeEnum.INGESTION, dto.getEventType()); 
		assertEquals(EventStatusEnum.SUCCESS, dto.getEventStatus()); 
		assertEquals(eventDate, dto.getEventDate()); 
		assertEquals(TEST_MESSAGE, dto.getMessage()); 
		
	} 
	
	@Test
	void kafkaMessageDtoTest() {
		KafkaMessageDTO dto = new KafkaMessageDTO(); 
		
		dto.setTxId(TEST_TX_ID); 
		dto.setMessage(TEST_MESSAGE); 
		
		assertEquals(KafkaMessageDTO.class, dto.getClass()); 
		assertEquals(String.class, dto.getTxId().getClass()); 
		assertEquals(String.class, dto.getMessage().getClass()); 
		
		assertEquals(TEST_TX_ID, dto.getTxId()); 
		assertEquals(TEST_MESSAGE, dto.getMessage()); 
		
	} 
	
	@Test
	void documentReferenceDtoTest() {
    	DocumentDTO dto = new DocumentDTO(); 

    	dto.setIdentifier(DOCUMENT_TEST_IDENTIFIER); 
    	dto.setJsonString(DOCUMENT_TEST_JSON_STRING); 
    	    	
    	
    	assertEquals(DocumentDTO.class, dto.getClass()); 
    	assertEquals(String.class, dto.getIdentifier().getClass()); 
    	assertEquals(String.class, dto.getJsonString().getClass()); 

    	assertEquals(DOCUMENT_TEST_IDENTIFIER, dto.getIdentifier()); 
    	assertEquals(DOCUMENT_TEST_JSON_STRING, dto.getJsonString()); 

    	
	}
}
