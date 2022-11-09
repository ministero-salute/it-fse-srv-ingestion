/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import it.finanze.sanita.fse2.ms.srvingestion.base.AbstractTest;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.DocumentResponseDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.error.base.ErrorResponseDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.LogTraceInfoDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.ResponseDTO;


class ResponseDTOTest extends AbstractTest {

	public HttpStatus RESPONSE_CODE_TEST = HttpStatus.OK; 
	public String RESPONSE_MESSAGE_TEST = "Test Message"; 
	
	public String SPAN_ID_TEST = "d9fg5hkaq8"; 
	public String TRACE_ID_TEST = "d9fgd8aasd"; 
	
	public String IN_TYPE_TEST = "type"; 
	public String IN_TITLE_TEST = "title"; 
	public String IN_DETAIL_TEST = "detail"; 
	public Integer IN_STATUS_TEST = 1; 
	public String IN_INSTANCE_TEST = "instance"; 
	public String IN_TX_ID_TEST = "txId"; 


	
	
	@Test
	void logTraceInfoDtoTest() {
		LogTraceInfoDTO dto = new LogTraceInfoDTO(SPAN_ID_TEST, TRACE_ID_TEST); 
		
		assertEquals(LogTraceInfoDTO.class, dto.getClass()); 
		assertEquals(String.class, dto.getSpanID().getClass()); 
		assertEquals(String.class, dto.getTraceID().getClass()); 
		
		assertEquals(SPAN_ID_TEST, dto.getSpanID()); 
		assertEquals(TRACE_ID_TEST, dto.getTraceID()); 
		
	} 
	
	@Test
	void responseDtoTest() {
		LogTraceInfoDTO logTraceInfoDto = new LogTraceInfoDTO(SPAN_ID_TEST, TRACE_ID_TEST); 

		ResponseDTO dto = new ResponseDTO(logTraceInfoDto); 
		
		assertEquals(ResponseDTO.class, dto.getClass()); 
		assertEquals(String.class, dto.getSpanID().getClass()); 
		assertEquals(String.class, dto.getTraceID().getClass()); 
		
		assertEquals(SPAN_ID_TEST, dto.getSpanID()); 
		assertEquals(TRACE_ID_TEST, dto.getTraceID()); 
	} 
	
	@Test
	void errorResponseDtoTest() {
		LogTraceInfoDTO logTraceInfoDto = new LogTraceInfoDTO(SPAN_ID_TEST, TRACE_ID_TEST); 

		ErrorResponseDTO dto = new ErrorResponseDTO(logTraceInfoDto, IN_TYPE_TEST, IN_TITLE_TEST,
				IN_DETAIL_TEST, IN_STATUS_TEST, IN_INSTANCE_TEST); 
		
		
		assertEquals(ErrorResponseDTO.class, dto.getClass()); 
		assertEquals(String.class, dto.getType().getClass()); 
		assertEquals(String.class, dto.getTitle().getClass()); 
		assertEquals(String.class, dto.getDetail().getClass()); 
		assertEquals(Integer.class, dto.getStatus().getClass()); 
		assertEquals(String.class, dto.getInstance().getClass()); 
		
		assertEquals(IN_TYPE_TEST, dto.getType()); 
		assertEquals(IN_TITLE_TEST, dto.getTitle()); 
		assertEquals(IN_DETAIL_TEST, dto.getDetail()); 
		assertEquals(IN_STATUS_TEST, dto.getStatus()); 
		assertEquals(IN_INSTANCE_TEST, dto.getInstance()); 
		
	} 
	
	@Test
	void errorResponseDtoLogInfoOnlyTest() {
		LogTraceInfoDTO logTraceInfoDto = new LogTraceInfoDTO(SPAN_ID_TEST, TRACE_ID_TEST); 

		ErrorResponseDTO dto = new ErrorResponseDTO(logTraceInfoDto); 
		
		
		assertEquals(ErrorResponseDTO.class, dto.getClass()); 
		assertEquals(String.class, dto.getSpanID().getClass()); 
		assertEquals(String.class, dto.getTraceID().getClass()); 
		
		assertEquals(SPAN_ID_TEST, dto.getSpanID()); 
		assertEquals(TRACE_ID_TEST, dto.getTraceID()); 
	} 
	
	@Test
	void documentResponseDtoNoArgsConstructor() {
		DocumentResponseDTO documentResponseDto = new DocumentResponseDTO(); 
		
		documentResponseDto.setSpanID(SPAN_ID_TEST); 
		documentResponseDto.setTraceID(TRACE_ID_TEST); 
		
		
		assertEquals(DocumentResponseDTO.class, documentResponseDto.getClass()); 
		assertEquals(String.class, documentResponseDto.getSpanID().getClass()); 
		assertEquals(String.class, documentResponseDto.getTraceID().getClass()); 
		
		assertEquals(SPAN_ID_TEST, documentResponseDto.getSpanID()); 
		assertEquals(TRACE_ID_TEST, documentResponseDto.getTraceID()); 

	}
	
	@Test
	void documentResponseDtoTestWithArgsConstructor() {
		LogTraceInfoDTO logTraceInfoDto = new LogTraceInfoDTO(SPAN_ID_TEST, TRACE_ID_TEST); 
		
		DocumentResponseDTO documentResponseDto = new DocumentResponseDTO(logTraceInfoDto); 
		
		
		assertEquals(DocumentResponseDTO.class, documentResponseDto.getClass()); 
		assertEquals(String.class, documentResponseDto.getSpanID().getClass()); 
		assertEquals(String.class, documentResponseDto.getTraceID().getClass()); 
		
		assertEquals(SPAN_ID_TEST, documentResponseDto.getSpanID()); 
		assertEquals(TRACE_ID_TEST, documentResponseDto.getTraceID()); 

	}

	
}
