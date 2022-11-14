/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import brave.Tracer;
import it.finanze.sanita.fse2.ms.srvingestion.base.AbstractTest;
import it.finanze.sanita.fse2.ms.srvingestion.client.impl.DataProcessorClient;
import it.finanze.sanita.fse2.ms.srvingestion.client.impl.SrvQueryClient;
import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.controller.impl.DocumentCTL;
import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.enums.PriorityTypeEnum;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;
import it.finanze.sanita.fse2.ms.srvingestion.service.IDocumentSRV;
import it.finanze.sanita.fse2.ms.srvingestion.utility.ProfileUtility;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(Constants.Profile.TEST)
class DocumentControllerTest extends AbstractTest {

	@Autowired
	ServletWebServerApplicationContext webServerAppCtxt;

	@MockBean
	Tracer tracer;

	@Autowired
	MockMvc mvc; 
	
	@Autowired
	DocumentCTL documentCTL; 

	@Autowired
	IDocumentSRV documentService;

	@MockBean
	ProfileUtility profileUtility;

	@MockBean
	private SrvQueryClient srvQueryClient;

	@MockBean
	private DataProcessorClient dataProcessorClient;

    static final String DOCUMENT_TEST_IDENTIFIER_C = "testIdentifierRepoC";
    static final String DOCUMENT_TEST_JSON_STRING_C = "{\"jsonString\": \"testC\"}"; 
    
    static final String DOCUMENT_TEST_IDENTIFIER_PUT = "testIdentifierRepoPut"; 
    static final ProcessorOperationEnum DOCUMENT_TEST_OPERATION_PUT = ProcessorOperationEnum.UPDATE; 
    static final String DOCUMENT_TEST_JSON_STRING_PUT = "{\"jsonString\": \"testPut\"}"; 
    
    static final String DOCUMENT_TEST_IDENTIFIER_DELETE = "testIdentifierRepoDelete"; 
	
    static final String DOCUMENT_TEST_IDENTIFIER_NOT_FOUND = "testIdentifierRepoNotFound"; 

    static final String DOCUMENT_TEST_IDENTIFIER_REPLACE = "testIdentifierRepoReplace"; 
    static final ProcessorOperationEnum DOCUMENT_TEST_OPERATION_REPLACE = ProcessorOperationEnum.REPLACE; 
    static final String DOCUMENT_TEST_JSON_STRING_REPLACE = "{\"jsonString\": \"testReplace\"}"; 
    
    static final ProcessorOperationEnum DOCUMENT_TEST_OPERATION_DELETE = ProcessorOperationEnum.DELETE; 

	@BeforeAll
	public void setup() {
		mongo.dropCollection(StagingDocumentETY.class);
		populateStagingCollection();

	}

	@AfterAll
	public void teardown() {
		mongo.dropCollection(StagingDocumentETY.class);
	}

    
    @Test
	void livenessCheckCtlTest() throws Exception {

		mvc.perform(get("http://localhost:" + webServerAppCtxt.getWebServer().getPort() + webServerAppCtxt.getServletContext().getContextPath() + "/status").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(
	            status().is2xxSuccessful()
	        );
	} 
	
	String getBaseUrl() {
		return "http://localhost:" + webServerAppCtxt.getWebServer().getPort() + webServerAppCtxt.getServletContext().getContextPath() + "/v1";
	}
	
	@Test
	void addDocumentTest() throws Exception {
    	DocumentDTO dtoC = new DocumentDTO(); 
    	List<DocumentDTO> dtoList= new ArrayList<DocumentDTO>(); 
        ObjectMapper objectMapper = new ObjectMapper(); 

		dtoC.setOperation(ProcessorOperationEnum.PUBLISH);
		dtoC.setPriorityTypeEnum(PriorityTypeEnum.HIGH);
    	dtoC.setIdentifier(DOCUMENT_TEST_IDENTIFIER_C); 
    	dtoC.setJsonString(DOCUMENT_TEST_JSON_STRING_C); 
    	   	
    	dtoList.add(dtoC); 

	    MockHttpServletRequestBuilder builder =
	            MockMvcRequestBuilders.post(getBaseUrl() + "/document").content(objectMapper.writeValueAsString(dtoC)); 
    	   	
	    
	    mvc.perform(builder
	            .contentType(MediaType.APPLICATION_JSON_VALUE))
	            .andExpect(status().is2xxSuccessful()); 

	} 
	
	@Test
	void addEmptyDocumentTest() throws Exception {
    	DocumentDTO dtoC = new DocumentDTO(); 
    	List<DocumentDTO> dtoList= new ArrayList<DocumentDTO>(); 
        ObjectMapper objectMapper = new ObjectMapper(); 

		dtoC.setOperation(ProcessorOperationEnum.PUBLISH);
		dtoC.setPriorityTypeEnum(PriorityTypeEnum.HIGH);
    	dtoC.setIdentifier(DOCUMENT_TEST_IDENTIFIER_C); 
    	dtoC.setJsonString(null); 
    	   	
    	dtoList.add(dtoC);

		MockHttpServletRequestBuilder builder =
	            MockMvcRequestBuilders.post(getBaseUrl() + "/document").content(objectMapper.writeValueAsString(dtoC)); 
    	   	
		mvc.perform(builder
	            .contentType(MediaType.APPLICATION_JSON_VALUE))
	            .andExpect(status().is4xxClientError());
	}

	@Test
	void insertUpdateDocumentTest() throws Exception {
    	DocumentDTO dtoC = new DocumentDTO(); 
    	List<DocumentDTO> dtoList= new ArrayList<DocumentDTO>(); 
        ObjectMapper objectMapper = new ObjectMapper(); 

    	dtoC.setIdentifier(DOCUMENT_TEST_IDENTIFIER_PUT); 
    	dtoC.setOperation(DOCUMENT_TEST_OPERATION_PUT); 
    	dtoC.setJsonString(DOCUMENT_TEST_JSON_STRING_PUT); 
    	   	
    	dtoList.add(dtoC);

		given(srvQueryClient.checkExists(anyString())).willReturn(true);
		given(dataProcessorClient.sendRequestToDataProcessor(any(DocumentDTO.class))).willReturn(true);

		MockHttpServletRequestBuilder builder =
	            MockMvcRequestBuilders.put(getBaseUrl() + "/document").content(objectMapper.writeValueAsString(dtoC)); 
    	   	
	    
	    mvc.perform(builder
	            .contentType(MediaType.APPLICATION_JSON_VALUE))
	            .andExpect(status().is2xxSuccessful()); 
	    
	    
	} 
	
	@Test
	void insertUpdateDocumentWithUnsupportedOperationTest() throws Exception {
    	DocumentDTO dtoC = new DocumentDTO(); 
    	List<DocumentDTO> dtoList= new ArrayList<DocumentDTO>(); 
        ObjectMapper objectMapper = new ObjectMapper(); 

    	dtoC.setIdentifier(DOCUMENT_TEST_IDENTIFIER_PUT); 
    	dtoC.setOperation(DOCUMENT_TEST_OPERATION_DELETE); 
    	dtoC.setJsonString(DOCUMENT_TEST_JSON_STRING_PUT); 
    	   	
    	dtoList.add(dtoC);

		given(srvQueryClient.checkExists(anyString())).willReturn(true);

		MockHttpServletRequestBuilder builder =
	            MockMvcRequestBuilders.put(getBaseUrl() + "/document").content(objectMapper.writeValueAsString(dtoC)); 
    	   	
	    
	    mvc.perform(builder
	            .contentType(MediaType.APPLICATION_JSON_VALUE))
	            .andExpect(status().is4xxClientError()); 
	    
	    
	}

	@Test
	void insertUpdateDocumentWithDocumentNotFoundTest() throws Exception {
		DocumentDTO dtoC = new DocumentDTO();
		List<DocumentDTO> dtoList= new ArrayList<DocumentDTO>();
		ObjectMapper objectMapper = new ObjectMapper();

		dtoC.setIdentifier(DOCUMENT_TEST_IDENTIFIER_PUT);
		dtoC.setOperation(DOCUMENT_TEST_OPERATION_DELETE);
		dtoC.setJsonString(DOCUMENT_TEST_JSON_STRING_PUT);

		dtoList.add(dtoC);

		given(srvQueryClient.checkExists(anyString())).willReturn(false);

		MockHttpServletRequestBuilder builder =
				MockMvcRequestBuilders.put(getBaseUrl() + "/document").content(objectMapper.writeValueAsString(dtoC));


		mvc.perform(builder
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is4xxClientError());


	}

	@Test
	void insertReplaceDocumentTest() throws Exception {
    	DocumentDTO dtoC = new DocumentDTO(); 
    	List<DocumentDTO> dtoList= new ArrayList<DocumentDTO>(); 
        ObjectMapper objectMapper = new ObjectMapper(); 

    	dtoC.setIdentifier(DOCUMENT_TEST_IDENTIFIER_REPLACE); 
    	dtoC.setOperation(DOCUMENT_TEST_OPERATION_REPLACE); 
    	dtoC.setJsonString(DOCUMENT_TEST_JSON_STRING_REPLACE); 
    	   	
    	dtoList.add(dtoC);

		given(srvQueryClient.checkExists(anyString())).willReturn(true);

		MockHttpServletRequestBuilder builder =
	            MockMvcRequestBuilders.put(getBaseUrl() + "/document/metadata").content(objectMapper.writeValueAsString(dtoC)); 
    	   	
	    
	    mvc.perform(builder
	            .contentType(MediaType.APPLICATION_JSON_VALUE))
	            .andExpect(status().is2xxSuccessful()); 
	    
	    
	}

	@Test
	void insertReplaceWithDocumentNotFoundTest() throws Exception {
		DocumentDTO dtoC = new DocumentDTO();
		List<DocumentDTO> dtoList= new ArrayList<DocumentDTO>();
		ObjectMapper objectMapper = new ObjectMapper();

		dtoC.setIdentifier(DOCUMENT_TEST_IDENTIFIER_REPLACE);
		dtoC.setOperation(DOCUMENT_TEST_OPERATION_REPLACE);
		dtoC.setJsonString(DOCUMENT_TEST_JSON_STRING_REPLACE);

		dtoList.add(dtoC);

		given(srvQueryClient.checkExists(anyString())).willReturn(false);

		MockHttpServletRequestBuilder builder =
				MockMvcRequestBuilders.put(getBaseUrl() + "/document/metadata").content(objectMapper.writeValueAsString(dtoC));


		mvc.perform(builder
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is4xxClientError());


	}

	@Test
	void insertUpdateDocumentEmptyBundleTest() throws Exception {
    	DocumentDTO dtoC = new DocumentDTO(); 
    	List<DocumentDTO> dtoList= new ArrayList<DocumentDTO>(); 

    	dtoC.setIdentifier(DOCUMENT_TEST_IDENTIFIER_PUT); 
    	dtoC.setOperation(DOCUMENT_TEST_OPERATION_PUT); 
    	dtoC.setJsonString(""); 
    	   	
    	dtoList.add(dtoC);

		given(srvQueryClient.checkExists(anyString())).willReturn(true);

		mvc.perform(
			put(getBaseUrl() + "/document", dtoC).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(
				status().is4xxClientError()
			);
	} 
	

	@Test
	void insertUpdateDocumentErrorTest() throws Exception {
    	DocumentDTO dtoC = new DocumentDTO(); 
    	List<DocumentDTO> dtoList= new ArrayList<DocumentDTO>(); 

    	dtoC.setIdentifier(DOCUMENT_TEST_IDENTIFIER_PUT); 
    	dtoC.setOperation(ProcessorOperationEnum.PUBLISH);
    	dtoC.setJsonString(DOCUMENT_TEST_JSON_STRING_PUT); 
    	   	
    	dtoList.add(dtoC);

		given(srvQueryClient.checkExists(anyString())).willReturn(true);

		mvc.perform(put(getBaseUrl() + "/document", dtoC).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(
				status().is4xxClientError()
			);

	} 
	
	@Test
	void insertDeleteDocumentTest() throws Exception {
        
	    MockHttpServletRequestBuilder builder =
	            MockMvcRequestBuilders.delete(getBaseUrl() + "/document/identifier/" + DOCUMENT_TEST_IDENTIFIER_DELETE);

		given(srvQueryClient.checkExists(anyString())).willReturn(true);
		given(dataProcessorClient.sendRequestToDataProcessor(any(DocumentDTO.class))).willReturn(true);

		mvc.perform(builder
	            .contentType(MediaType.APPLICATION_JSON_VALUE))
	            .andExpect(status().is2xxSuccessful()); 

	}

	@Test
	void insertDeleteDocumentNotFoundTest() throws Exception {

		MockHttpServletRequestBuilder builder =
				MockMvcRequestBuilders.delete(getBaseUrl() + "/document/identifier/" + DOCUMENT_TEST_IDENTIFIER_DELETE);

		given(srvQueryClient.checkExists(anyString())).willReturn(false);


		mvc.perform(builder
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is4xxClientError()); 

	}

	@Test
	void getDocumentsTest() throws Exception {
	
	mvc.perform(get(getBaseUrl() + "/document").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(
            status().is2xxSuccessful()
        );
	} 
	
	@Test
	void getDocumentsByIdTest() throws Exception {
    	DocumentDTO dtoC = new DocumentDTO(); 

    	dtoC.setIdentifier(DOCUMENT_TEST_IDENTIFIER_C); 
    	dtoC.setJsonString(DOCUMENT_TEST_JSON_STRING_C); 
    	   	
    	String wii = "WII";
    	
    	StagingDocumentETY ety = documentService.insert(dtoC,wii);
    	String mongoId = ety.getId(); 
    	

		mvc.perform(get(getBaseUrl() + "/document/" + mongoId).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(
				status().is(200)
			);
	
	} 
	
	@Test
	void getDocumentsByIdNotFoundTest() throws Exception {
			            
		mvc.perform(get(getBaseUrl() + "/document/" + DOCUMENT_TEST_IDENTIFIER_NOT_FOUND).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(
				status().is4xxClientError()
			); 
	} 
	
	@Test
	void genericExceptionTest() {
		assertThrows(Exception.class, () -> { throw new Exception("Test Exception"); }); 
	}
}
