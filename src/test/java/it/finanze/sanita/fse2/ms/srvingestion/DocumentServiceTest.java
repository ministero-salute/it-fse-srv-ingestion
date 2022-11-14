/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import it.finanze.sanita.fse2.ms.srvingestion.base.AbstractTest;
import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;
import it.finanze.sanita.fse2.ms.srvingestion.service.IDocumentSRV;

@SpringBootTest
@ComponentScan
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(Constants.Profile.TEST)
class DocumentServiceTest extends AbstractTest {

	
	@Autowired
	public IDocumentSRV documentService; 
	
		
    public static final String DOCUMENT_TEST_IDENTIFIER_A = "testIdentifierRepoA"; 
    public static final String DOCUMENT_TEST_JSON_STRING_A = "{\"jsonString\": \"testA\"}"; 

    public static final String DOCUMENT_TEST_IDENTIFIER_B = "testIdentifierRepoB"; 
    public static final String DOCUMENT_TEST_JSON_STRING_B = "{\"jsonString\": \"testB\"}"; 
    
    public static final String DOCUMENT_TEST_IDENTIFIER_C = "testIdentifierRepoC"; 
    public static final String DOCUMENT_TEST_JSON_STRING_C = "{\"jsonString\": \"testC\"}"; 
    
    public static final String DOCUMENT_TEST_IDENTIFIER_DEL = "testIdentifierRepoDel"; 
    public static final String DOCUMENT_TEST_JSON_STRING_DEL = "{\"jsonString\": \"testDel\"}"; 
    
    public static final String DOCUMENT_TEST_IDENTIFIER_NOT_FOUND = "testIdentifierRepoNotFound"; 
    
    public static final ProcessorOperationEnum DOCUMENT_TEST_OPERATION = ProcessorOperationEnum.PUBLISH;
    
    
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
    void insertTest() throws OperationException, DocumentNotFoundException, EmptyDocumentException {
    	DocumentDTO dto = new DocumentDTO(); 

    	dto.setIdentifier(DOCUMENT_TEST_IDENTIFIER_C); 
    	dto.setOperation(DOCUMENT_TEST_OPERATION);
    	dto.setJsonString(DOCUMENT_TEST_JSON_STRING_C);
    	
    	String wii = "WII";
    	   	    	
    	StagingDocumentETY ety = documentService.insert(dto,wii);
    	String mongoId = ety.getId(); 
    		
    	DocumentDTO retrievedDto = documentService.getDocumentById(mongoId); 
    	
    	
    	assertEquals(DocumentDTO.class, retrievedDto.getClass()); 
    	assertEquals(String.class, retrievedDto.getIdentifier().getClass()); 
    	assertEquals(String.class, retrievedDto.getJsonString().getClass());   	
    	
    	assertEquals(DOCUMENT_TEST_IDENTIFIER_C, retrievedDto.getIdentifier()); 
    	assertEquals(DOCUMENT_TEST_JSON_STRING_C, retrievedDto.getJsonString()); 
    } 
    
    @Test
    void insertEmptyBundle() throws OperationException, DocumentNotFoundException, EmptyDocumentException {
    	DocumentDTO dto = new DocumentDTO(); 

    	dto.setIdentifier(DOCUMENT_TEST_IDENTIFIER_C); 
    	dto.setOperation(DOCUMENT_TEST_OPERATION);
    	dto.setJsonString(anyString()); 
    	
    	String wii = "WII";
    	   	    	
    	assertThrows(EmptyDocumentException.class, () -> documentService.insert(dto,wii)); 
    } 
    
    @Test
    void deleteTest() throws OperationException, EmptyDocumentException {
    	DocumentDTO dto = new DocumentDTO(); 

    	dto.setIdentifier(DOCUMENT_TEST_IDENTIFIER_DEL); 
    	dto.setOperation(DOCUMENT_TEST_OPERATION);
    	dto.setJsonString(DOCUMENT_TEST_JSON_STRING_DEL); 
    	   	    	
    	String wii = "WII";
    	StagingDocumentETY ety = documentService.insert(dto,wii);
    	String mongoId = ety.getId(); 
    		
    	documentService.delete(mongoId); 
    	
    		
    	assertThrows(Exception.class, () -> documentService.getDocumentById(DOCUMENT_TEST_IDENTIFIER_DEL)); 
    	
    	
    }
    
    @Test
    void getDocumentByIdTest() throws OperationException, DocumentNotFoundException, EmptyDocumentException {
    	DocumentDTO dtoC = new DocumentDTO(); 

    	dtoC.setIdentifier(DOCUMENT_TEST_IDENTIFIER_C); 
    	dtoC.setOperation(DOCUMENT_TEST_OPERATION);
    	dtoC.setJsonString(DOCUMENT_TEST_JSON_STRING_C); 
    	    	
    	String wii = "WII";
    	StagingDocumentETY ety = documentService.insert(dtoC,wii);
    	String mongoId = ety.getId();     	
    	
    	DocumentDTO retrievedDtoC = documentService.getDocumentById(mongoId); 
    	
    	
    	assertEquals(DocumentDTO.class, retrievedDtoC.getClass()); 
    	assertEquals(String.class, retrievedDtoC.getIdentifier().getClass()); 
    	assertEquals(String.class, retrievedDtoC.getJsonString().getClass());   	
    	
    	assertEquals(DOCUMENT_TEST_IDENTIFIER_C, retrievedDtoC.getIdentifier()); 
    	assertEquals(DOCUMENT_TEST_JSON_STRING_C, retrievedDtoC.getJsonString()); 

    } 
    
    @Test
    void getDocumentByIdNotFoundTest() throws OperationException, DocumentNotFoundException, EmptyDocumentException {
    	assertThrows(DocumentNotFoundException.class, () -> documentService.getDocumentById(DOCUMENT_TEST_IDENTIFIER_NOT_FOUND)); 
    } 
    
    @Test
    void getDocumentTest() {
    	List<DocumentDTO> dtoRetrievedList = documentService.getDocuments(); 
    	DocumentDTO firstElemEtyInList = dtoRetrievedList.get(0); 
    	
    	assertEquals(ArrayList.class, dtoRetrievedList.getClass()); 
    	assertEquals(true, dtoRetrievedList.size() > 0); 
    	
    	assertEquals(DocumentDTO.class, firstElemEtyInList.getClass()); 
    	assertEquals(ProcessorOperationEnum.class, firstElemEtyInList.getOperation().getClass()); 
    	assertEquals(String.class, firstElemEtyInList.getJsonString().getClass()); 
    	
    }
    
}
