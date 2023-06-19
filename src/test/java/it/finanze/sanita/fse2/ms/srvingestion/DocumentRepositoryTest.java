/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.*;
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
    
	
    
	@BeforeEach
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
