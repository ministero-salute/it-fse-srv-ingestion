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
