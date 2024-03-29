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

import org.junit.jupiter.api.Test;

import it.finanze.sanita.fse2.ms.srvingestion.base.AbstractTest;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.BusinessException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentAlreadyExistsException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.KafkaException;


class ExceptionTest extends AbstractTest {

	@Test
	void businessExceptionTest() {
		BusinessException exc = new BusinessException("Error"); 
		
		assertEquals(BusinessException.class, exc.getClass()); 
		assertEquals("Error", exc.getMessage()); 
		
	} 
	
	@Test
	void businessExceptionTestWithoutMsg() {
		BusinessException exc = new BusinessException(new RuntimeException()); 
		
		assertEquals(BusinessException.class, exc.getClass()); 
		
	}

	@Test
	void documentAlreadyExistsExceptionTest() {
		DocumentAlreadyExistsException exc = new DocumentAlreadyExistsException("Error"); 
		
		assertEquals(DocumentAlreadyExistsException.class, exc.getClass()); 
		assertEquals("Error", exc.getMessage()); 
		
	} 

	@Test
	void emptyDocumentExceptionTest() {
		EmptyDocumentException exc = new EmptyDocumentException("Error"); 
		
		assertEquals(EmptyDocumentException.class, exc.getClass()); 
		assertEquals("Error", exc.getMessage()); 
		
	} 
	
	@Test
	void kafkaExceptionTest() {
		KafkaException exc = new KafkaException("Error", new RuntimeException()); 
		
		assertEquals(KafkaException.class, exc.getClass()); 
		assertEquals("Error", exc.getMessage()); 
		
	}

} 
