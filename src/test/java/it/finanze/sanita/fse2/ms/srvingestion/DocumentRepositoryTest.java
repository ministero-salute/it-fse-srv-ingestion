package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.DocumentReferenceETY;

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
    public void setup() throws Exception {
        this.initTestRepository();
    } 
    
    
    
    @Test
    void insertTest() throws OperationException {
    	DocumentReferenceETY etyA = new DocumentReferenceETY(); 

    	etyA.setOperation(ProcessorOperationEnum.PUBLISH);
    	etyA.setJsonString(DOCUMENT_TEST_JSON_STRING_C); 
    	
    	documentRepository.insert(etyA); 
    	  	
    	DocumentReferenceETY retrievedEtyC = documentRepository.findById(etyA.getId()); 

    	
    	assertEquals(DocumentReferenceETY.class, retrievedEtyC.getClass()); 
    	assertEquals(String.class, retrievedEtyC.getJsonString().getClass()); 
    	
    	assertEquals(ProcessorOperationEnum.PUBLISH, retrievedEtyC.getOperation());
    	assertEquals(DOCUMENT_TEST_JSON_STRING_C, retrievedEtyC.getJsonString()); 
    	 	
    }
    
    
    @Test
    void deleteTest() throws OperationException {
    	DocumentReferenceETY etyA = new DocumentReferenceETY(); 

    	etyA.setIdentifier(DOCUMENT_TEST_IDENTIFIER_DEL); 
    	etyA.setJsonString(DOCUMENT_TEST_JSON_STRING_DEL); 
 
    	
    	  	
    	DocumentReferenceETY ety = documentRepository.insert(etyA);
    	
    	documentRepository.deleteByIdentifier(ety.getId()); 
    	
    	DocumentReferenceETY retrievedEtyDel = documentRepository.findById(ety.getId()); 

    	assertEquals(DocumentReferenceETY.class, retrievedEtyDel.getClass()); 

    	 	
    }
    
    
    @Test
    void findByIdTest() throws Exception {
    	DocumentReferenceETY etyA = new DocumentReferenceETY(); 

    	etyA.setJsonString(DOCUMENT_TEST_JSON_STRING_C); 
    	
    	DocumentReferenceETY ety = documentRepository.insert(etyA); 
    	  
    	DocumentReferenceETY retrievedEty = documentRepository.findById(ety.getId()); 
    	
    	
    	assertEquals(DocumentReferenceETY.class, retrievedEty.getClass()); 
    	assertEquals(String.class, retrievedEty.getJsonString().getClass()); 
    	
    	assertEquals(DOCUMENT_TEST_JSON_STRING_C, retrievedEty.getJsonString()); 	
    	
    }
    
    
    @Test
    void findAllTest() throws Exception {  	
    	DocumentReferenceETY etyA = new DocumentReferenceETY(); 

    	etyA.setIdentifier(DOCUMENT_TEST_IDENTIFIER_C); 
    	etyA.setJsonString(DOCUMENT_TEST_JSON_STRING_C); 
    	    	
    	documentRepository.insert(etyA); 
    	
    	
    	List<DocumentReferenceETY> etyRetrievedList = documentRepository.findAll(); 
    	DocumentReferenceETY firstElemEtyInList = etyRetrievedList.get(0); 
    	
    	assertEquals(ArrayList.class, etyRetrievedList.getClass()); 
    	assertEquals(true, etyRetrievedList.size() > 0); 
    	
    	assertEquals(DocumentReferenceETY.class, firstElemEtyInList.getClass()); 
    	assertEquals(String.class, firstElemEtyInList.getJsonString().getClass()); 
    	
    }

     
    @AfterAll
    public void teardown() {
        this.dropTestSchema();
    } 
    
} 
