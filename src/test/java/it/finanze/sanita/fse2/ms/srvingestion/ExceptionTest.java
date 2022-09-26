package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.finanze.sanita.fse2.ms.srvingestion.base.AbstractTest;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.BusinessException;


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

	
} 
