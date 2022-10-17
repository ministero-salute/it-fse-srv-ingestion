package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import it.finanze.sanita.fse2.ms.srvingestion.base.AbstractTest;
import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;


@SpringBootTest
@ComponentScan
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(Constants.Profile.TEST)
class EntityDtoTest extends AbstractTest {


    
    @Test
    void createDocumentEty() {
    	StagingDocumentETY documentEty = new StagingDocumentETY();
    	
    	documentEty.setIdentifier(DOCUMENT_TEST_IDENTIFIER); 
    	documentEty.setDocument(Document.parse(DOCUMENT_TEST_JSON_STRING)); 
    	    	
    	
    	assertEquals(StagingDocumentETY.class, documentEty.getClass());
    	assertEquals(String.class, documentEty.getIdentifier().getClass()); 
    	assertEquals(Document.class, documentEty.getDocument().getClass()); 

    	assertEquals(DOCUMENT_TEST_IDENTIFIER, documentEty.getIdentifier()); 
    	assertEquals(Document.parse(DOCUMENT_TEST_JSON_STRING), documentEty.getDocument()); 
    } 
    
    	
 }
