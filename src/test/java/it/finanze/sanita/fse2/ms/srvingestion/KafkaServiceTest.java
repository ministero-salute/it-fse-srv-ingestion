package it.finanze.sanita.fse2.ms.srvingestion;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.service.impl.KafkaSRV;

@SpringBootTest
@ComponentScan
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(Constants.Profile.TEST)
class KafkaServiceTest {

	@Autowired 
	public KafkaSRV kafkaService; 
	
	
	public String TEST_TX_ID = "TX_ID_TEST"; 
	public String TEST_MESSAGE = "This is a test message"; 
	public ProcessorOperationEnum TEST_KEY = ProcessorOperationEnum.PUBLISH;
	
	
	
	@Test
	void sendMessageTest() {
		String topic = "ingestion-dataprocessor-topic"; 
		RecordMetadata output = kafkaService.sendMessage(topic, TEST_KEY, TEST_MESSAGE, true); 
		
		
		assertEquals(TEST_MESSAGE.length(), output.serializedValueSize() , "Il value non coincide"); 
		assertEquals(topic, output.topic(), "Il topic non coincide"); 
	} 
	
	@Test
	void notifyDataProcessorTest() {
		String topic = "ingestion-dataprocessor-topic";
		assertDoesNotThrow(() -> kafkaService.notifyDataProcessor(topic, TEST_TX_ID, TEST_KEY));
	} 
	
}
