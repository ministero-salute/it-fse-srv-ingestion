package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import it.finanze.sanita.fse2.ms.srvingestion.base.AbstractTest;
import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.DocumentReferenceETY;


@SpringBootTest
@ComponentScan
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(Constants.Profile.TEST)
class EntityDtoTest extends AbstractTest {


    
    @Test
    void createDocumentEty() {
    	DocumentReferenceETY documentEty = new DocumentReferenceETY(); 
    	
    	documentEty.setIdentifier(DOCUMENT_TEST_IDENTIFIER); 
    	documentEty.setJsonString(DOCUMENT_TEST_JSON_STRING); 
    	    	
    	
    	assertEquals(DocumentReferenceETY.class, documentEty.getClass()); 
    	assertEquals(String.class, documentEty.getIdentifier().getClass()); 
    	assertEquals(String.class, documentEty.getJsonString().getClass()); 

    	assertEquals(DOCUMENT_TEST_IDENTIFIER, documentEty.getIdentifier()); 
    	assertEquals(DOCUMENT_TEST_JSON_STRING, documentEty.getJsonString()); 
    } 
    
    	
 }
