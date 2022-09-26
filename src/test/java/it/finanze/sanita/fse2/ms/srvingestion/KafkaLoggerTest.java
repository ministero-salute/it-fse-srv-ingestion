package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ErrorLogEnum;
import it.finanze.sanita.fse2.ms.srvingestion.enums.OperationLogEnum;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ResultLogEnum;
import it.finanze.sanita.fse2.ms.srvingestion.service.impl.KafkaLoggerSRV;

@SpringBootTest
@ComponentScan
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(Constants.Profile.TEST)
@ExtendWith(OutputCaptureExtension.class)
class KafkaLoggerTest {
	
	
	public String TEST_LOG_MESSAGE = "Test Log Message"; 
	public OperationLogEnum TEST_LOG_OPERATION = OperationLogEnum.PUB_CDA2; 
	public ResultLogEnum TEST_LOG_RESULT = ResultLogEnum.OK; 
	public Date TEST_LOG_DATE = new Date(); 
	public ErrorLogEnum TEST_LOG_ERROR = ErrorLogEnum.KO_DOCUMENT_CREATE; 
	
	
	@Autowired
	public KafkaLoggerSRV kafkaLogger; 
	
	
	
	@Test
	void traceTest(CapturedOutput output) {
		kafkaLogger.trace(TEST_LOG_MESSAGE, TEST_LOG_OPERATION, TEST_LOG_RESULT, TEST_LOG_DATE); 
		assertTrue(true); 
	} 
	 
	@Test
	void debugTest(CapturedOutput output) {
		kafkaLogger.debug(TEST_LOG_MESSAGE, TEST_LOG_OPERATION, TEST_LOG_RESULT, TEST_LOG_DATE); 
		assertTrue(true); 
	} 
	
	@Test
	void infoTest(CapturedOutput output) {
		kafkaLogger.info(TEST_LOG_MESSAGE, TEST_LOG_OPERATION, TEST_LOG_RESULT, TEST_LOG_DATE);
		assertTrue(true); 
	} 
	
	@Test
	void warnTest(CapturedOutput output) {
		kafkaLogger.warn(TEST_LOG_MESSAGE, TEST_LOG_OPERATION, TEST_LOG_RESULT, TEST_LOG_DATE); 
		assertTrue(true); 
	} 
	
	@Test
	void errorTest(CapturedOutput output) {
		kafkaLogger.error(TEST_LOG_MESSAGE, TEST_LOG_OPERATION, TEST_LOG_RESULT, TEST_LOG_DATE, TEST_LOG_ERROR); 
		assertTrue(true); 
	} 
	
	
}