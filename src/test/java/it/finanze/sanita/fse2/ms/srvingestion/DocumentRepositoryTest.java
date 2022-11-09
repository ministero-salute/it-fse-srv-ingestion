/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import it.finanze.sanita.fse2.ms.srvingestion.base.AbstractTest;
import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;

@SpringBootTest
@ComponentScan
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(Constants.Profile.TEST)
class DocumentRepositoryTest extends AbstractTest {

	
    public static final String DOCUMENT_TEST_IDENTIFIER_A = "testIdentifierRepoA"; 
    public static final String DOCUMENT_TEST_JSON_STRING_A = "{\"jsonString\": \"testA\"}"; 

    public static final String DOCUMENT_TEST_IDENTIFIER_B = "testIdentifierRepoB"; 
    public static final String DOCUMENT_TEST_JSON_STRING_B = "{\"jsonString\": \"testB\"}"; 
    
    public static final String DOCUMENT_TEST_IDENTIFIER_C = "testIdentifierRepoC"; 
    public static final String DOCUMENT_TEST_JSON_STRING_C = "{\"jsonString\": \"testC\"}"; 
    
    public static final String DOCUMENT_TEST_IDENTIFIER_DEL = "testIdentifierRepoDel"; 
    public static final String DOCUMENT_TEST_JSON_STRING_DEL = "{\"jsonString\": \"testDel\"}";  
    
	
    
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
    void insertTest() throws OperationException {
    	StagingDocumentETY etyA = new StagingDocumentETY();

    	etyA.setOperation(ProcessorOperationEnum.PUBLISH);
    	etyA.setDocument(Document.parse(DOCUMENT_TEST_JSON_STRING_C)); 
    	
    	documentRepository.insert(etyA); 
    	  	
    	StagingDocumentETY retrievedEtyC = documentRepository.findById(etyA.getId());

    	
    	assertEquals(StagingDocumentETY.class, retrievedEtyC.getClass());
    	assertEquals(Document.class, retrievedEtyC.getDocument().getClass()); 
    	
    	assertEquals(ProcessorOperationEnum.PUBLISH, retrievedEtyC.getOperation());
    	assertEquals(Document.parse(DOCUMENT_TEST_JSON_STRING_C), retrievedEtyC.getDocument()); 
    	 	
    }
    
    
    @Test
    void deleteTest() throws OperationException {
    	StagingDocumentETY etyA = new StagingDocumentETY();

    	etyA.setIdentifier(DOCUMENT_TEST_IDENTIFIER_DEL); 
    	etyA.setDocument(Document.parse(DOCUMENT_TEST_JSON_STRING_DEL)); 
 
    	
    	  	
    	StagingDocumentETY ety = documentRepository.insert(etyA);
    	
    	documentRepository.deleteByIdentifier(ety.getId()); 
    	
    	StagingDocumentETY retrievedEtyDel = documentRepository.findById(ety.getId());

    	assertEquals(StagingDocumentETY.class, retrievedEtyDel.getClass());

    	 	
    }
    
    
    @Test
    void findByIdTest() throws Exception {
    	StagingDocumentETY etyA = new StagingDocumentETY();

    	etyA.setDocument(Document.parse(DOCUMENT_TEST_JSON_STRING_C)); 
    	
    	StagingDocumentETY ety = documentRepository.insert(etyA);
    	  
    	StagingDocumentETY retrievedEty = documentRepository.findById(ety.getId());
    	
    	
    	assertEquals(StagingDocumentETY.class, retrievedEty.getClass());
    	assertEquals(Document.class, retrievedEty.getDocument().getClass()); 
    	
    	assertEquals(DOCUMENT_TEST_JSON_STRING_C, retrievedEty.getDocument().toJson()); 	
    	
    }
    
    
    @Test
    void findAllTest() throws Exception {  	
    	StagingDocumentETY etyA = new StagingDocumentETY();

    	etyA.setIdentifier(DOCUMENT_TEST_IDENTIFIER_C); 
    	etyA.setDocument(Document.parse(DOCUMENT_TEST_JSON_STRING_C)); 
    	    	
    	documentRepository.insert(etyA); 
    	
    	
    	List<StagingDocumentETY> etyRetrievedList = documentRepository.findAll();
    	StagingDocumentETY firstElemEtyInList = etyRetrievedList.get(0);
    	
    	assertEquals(ArrayList.class, etyRetrievedList.getClass()); 
    	assertEquals(true, etyRetrievedList.size() > 0); 
    	
    	assertEquals(StagingDocumentETY.class, firstElemEtyInList.getClass());
    	assertEquals(Document.class, firstElemEtyInList.getDocument().getClass()); 
    	
    } 
    
} 
