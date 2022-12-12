/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.base;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;
import it.finanze.sanita.fse2.ms.srvingestion.repository.mongo.impl.DocumentRepo;


public abstract class AbstractTest {

    @Autowired
    protected DocumentRepo documentRepository; 

    /**
     * This is a test collection initialized on MongoDB 
     */
    public static final String DOCUMENT_TEST_IDENTIFIER_A = "testIdentifierA"; 
    public static final String DOCUMENT_TEST_JSON_STRING_A = "{\"jsonString\": \"testA\"}"; 

    public static final String DOCUMENT_TEST_IDENTIFIER_B = "testIdentifierB"; 
    public static final String DOCUMENT_TEST_JSON_STRING_B = "{\"jsonString\": \"testB\"}"; 
    
    /**
     * This collection does not exist
     */
    public static final String DOCUMENT_TEST_FAKE_NAME = "testDocumentFake";
    /**
     * Sample parameters for multiple tests
     */
    public static final String DOCUMENT_TEST_IDENTIFIER = "testIdentifier"; 
    public static final String DOCUMENT_TEST_JSON_STRING = "{\"jsonString\": \"test\"}"; 

    public static final ProcessorOperationEnum DOCUMENT_TEST_OPERATION = ProcessorOperationEnum.PUBLISH;

    @Autowired
    public MongoTemplate mongo;
    
    protected AbstractTest() {}

    protected void populateStagingCollection() {
        
    	StagingDocumentETY documentA = new StagingDocumentETY();
    	documentA.setIdentifier(DOCUMENT_TEST_IDENTIFIER_A);
    	documentA.setOperation(DOCUMENT_TEST_OPERATION);
    	documentA.setDocument(Document.parse(DOCUMENT_TEST_JSON_STRING_A)); 
    	
    	
    	StagingDocumentETY documentB = new StagingDocumentETY();
    	documentB.setIdentifier(DOCUMENT_TEST_IDENTIFIER_B); 
    	documentB.setOperation(DOCUMENT_TEST_OPERATION);
    	documentB.setDocument(Document.parse(DOCUMENT_TEST_JSON_STRING_B)); 
    	
        mongo.insert(documentA);
        mongo.insert(documentB);
    }
    
}
