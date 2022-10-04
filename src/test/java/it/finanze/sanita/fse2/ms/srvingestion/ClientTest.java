package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import it.finanze.sanita.fse2.ms.srvingestion.client.impl.DataProcessorClient;
import it.finanze.sanita.fse2.ms.srvingestion.client.impl.SrvQueryClient;
import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentReferenceDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.DocumentResponseDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.ResourceExistResDTO;
import it.finanze.sanita.fse2.ms.srvingestion.enums.PriorityTypeEnum;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.BusinessException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.ConnectionRefusedException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(Constants.Profile.TEST)
public class ClientTest {


	@Autowired
	private SrvQueryClient srvQueryClient; 
	
	@Autowired
	private DataProcessorClient dataProcessorClient; 
	
	@MockBean
	private RestTemplate restTemplate; 
		
	
	private String TEST_DOC_ID = "testDocId"; 
	private String TEST_IDENTIFIER = "testIdentifier"; 
	private ProcessorOperationEnum TEST_OPERATION = ProcessorOperationEnum.PUBLISH; 
	private String TEST_JSON_STRING = "{\"test\": \"testString\"}"; 
	private PriorityTypeEnum TEST_PRIORITY_TYPE = PriorityTypeEnum.HIGH; 
	
	
	@Test
	@DisplayName("Client Test - Srv Query Success")
	void srvQueryClientTest() {
		new ResponseEntity<ResourceExistResDTO>(HttpStatus.OK); 
		
		ResourceExistResDTO mockResponse = new ResourceExistResDTO(); 
		mockResponse.setExist(true); 
		
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ResourceExistResDTO.class))).thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

		
		Boolean response = srvQueryClient.checkExists(TEST_DOC_ID); 
		
		assertTrue(response); 

	} 
	
	@Test
	@DisplayName("Client Test - Srv Query Connection Refused Exception")
	void srvQueryClientExceptionTest() {
		new ResponseEntity<ResourceExistResDTO>(HttpStatus.OK); 
		
		ResourceExistResDTO mockResponse = new ResourceExistResDTO(); 
		mockResponse.setExist(true); 
		
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ResourceExistResDTO.class))).thenThrow(new ResourceAccessException("Test Error"));
			
		assertThrows(ConnectionRefusedException.class, () -> srvQueryClient.checkExists(TEST_DOC_ID)); 

	} 
	
	@Test
	@DisplayName("Client Test - Srv Query Business Exception")
	void srvQueryClientBusinessExceptionTest() {
		new ResponseEntity<ResourceExistResDTO>(HttpStatus.OK); 
		
		ResourceExistResDTO mockResponse = new ResourceExistResDTO(); 
		mockResponse.setExist(true); 
		
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ResourceExistResDTO.class))).thenThrow(new BusinessException("Test Error"));
			
		assertThrows(BusinessException.class, () -> srvQueryClient.checkExists(TEST_DOC_ID)); 

	} 
	
	@Test
	@DisplayName("Client Test - Srv Data Processor Success")
	void srvDataProcessorClient() {
		
		DocumentResponseDTO mockResponseDto = new DocumentResponseDTO(); 

		DocumentReferenceDTO document = new DocumentReferenceDTO(); 
		document.setIdentifier(TEST_IDENTIFIER); 
		document.setOperation(TEST_OPERATION); 
		document.setJsonString(TEST_JSON_STRING); 
		document.setPriorityTypeEnum(TEST_PRIORITY_TYPE); 
		
		
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(DocumentResponseDTO.class)))
			.thenReturn(new ResponseEntity<DocumentResponseDTO>(mockResponseDto, HttpStatus.OK)); 

		
		Boolean response = dataProcessorClient.sendRequestToDataProcessor(document); 
		
		assertTrue(response); 
		
	} 
	
	@Test
	@DisplayName("Client Test - Srv Data Processor Connection Refused Exception")
	void srvDataProcessorExceptionTest() {
		
		DocumentReferenceDTO document = new DocumentReferenceDTO(); 
		document.setIdentifier(TEST_IDENTIFIER); 
		document.setOperation(TEST_OPERATION); 
		document.setJsonString(TEST_JSON_STRING); 
		document.setPriorityTypeEnum(TEST_PRIORITY_TYPE); 
				
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(DocumentResponseDTO.class))).thenThrow(new ResourceAccessException("Test Error"));
			
		assertThrows(ConnectionRefusedException.class, () -> dataProcessorClient.sendRequestToDataProcessor(document)); 

	} 
	
	@Test
	@DisplayName("Client Test - Srv Data Processor Business Exception")
	void srvDataProcessorBusinessExceptionTest() {
		
		DocumentReferenceDTO document = new DocumentReferenceDTO(); 
		document.setIdentifier(TEST_IDENTIFIER); 
		document.setOperation(TEST_OPERATION); 
		document.setJsonString(TEST_JSON_STRING); 
		document.setPriorityTypeEnum(TEST_PRIORITY_TYPE); 
				
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(DocumentResponseDTO.class))).thenThrow(new BusinessException("Test Error"));
			
		assertThrows(BusinessException.class, () -> dataProcessorClient.sendRequestToDataProcessor(document)); 

	} 
	
}
