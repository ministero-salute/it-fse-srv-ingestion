/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.controller.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.sanita.fse2.ms.srvingestion.client.impl.DataProcessorClient;
import it.finanze.sanita.fse2.ms.srvingestion.client.impl.SrvQueryClient;
import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.config.kafka.KafkaTopicCFG;
import it.finanze.sanita.fse2.ms.srvingestion.controller.AbstractCTL;
import it.finanze.sanita.fse2.ms.srvingestion.controller.IDocumentCTL;
import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.DocumentResponseDTO;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentAlreadyExistsException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.KafkaException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.UnsupportedOperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;
import it.finanze.sanita.fse2.ms.srvingestion.service.impl.DocumentSRV;
import it.finanze.sanita.fse2.ms.srvingestion.service.impl.KafkaSRV;
import lombok.extern.slf4j.Slf4j;


/** 
 * Document Controller 
 * 
 */
@RestController
@Slf4j
public class DocumentCTL extends AbstractCTL implements IDocumentCTL {

	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = -8298415975725845794L; 
	
	
	@Autowired
	private DocumentSRV documentService; 
	
	@Autowired
	private KafkaSRV kafkaService; 
	
	@Autowired
	private DataProcessorClient dataProcessorClient; 
	
	@Autowired
	private SrvQueryClient srvQueryClient;

	@Autowired
	private transient KafkaTopicCFG kafkaTopicCFG;

	@Value("${eds.srvquery.read.mock}")
	private boolean srvQueryReadMockEnabled;

	@Override
	public ResponseEntity<DocumentResponseDTO> addDocument(HttpServletRequest request,DocumentDTO document, String wii) throws OperationException, KafkaException, EmptyDocumentException, DocumentAlreadyExistsException {
		log.debug(Constants.Logs.CALLED_API_POST_DOCUMENT); 
		document.setInsertionDate(new Date()); 
		StagingDocumentETY ety = documentService.insert(document,wii);
		String mongoId = ety.getId();
		String topic = kafkaTopicCFG.getIngestionDataProcessorPublicationTopic() + document.getPriorityTypeEnum().getQueue();
		kafkaService.notifyDataProcessor(topic, mongoId, ProcessorOperationEnum.PUBLISH);
		return new ResponseEntity<>(new DocumentResponseDTO(getLogTraceInfo()), HttpStatus.CREATED); 
	}

	@Override
	public ResponseEntity<DocumentResponseDTO> insertReplaceDocument(HttpServletRequest request, DocumentDTO document) throws OperationException, KafkaException, EmptyDocumentException, UnsupportedOperationException, DocumentAlreadyExistsException, DocumentNotFoundException {
		return genericReplaceUpdateDocument(document,null);
	}

	@Override
	public ResponseEntity<DocumentResponseDTO> insertUpdateDocument(HttpServletRequest request, DocumentDTO document) throws OperationException, KafkaException, EmptyDocumentException, UnsupportedOperationException, DocumentAlreadyExistsException, DocumentNotFoundException {
		return genericReplaceUpdateDocument(document,null);
	}

	private ResponseEntity<DocumentResponseDTO> genericReplaceUpdateDocument(final DocumentDTO documentReferenceDTO, final String wii) throws EmptyDocumentException, OperationException, KafkaException, UnsupportedOperationException, DocumentNotFoundException {
		documentReferenceDTO.setInsertionDate(new Date());

		boolean exist = srvQueryClient.checkExists(documentReferenceDTO.getIdentifier());
		if (Boolean.FALSE.equals(exist)) {
			throw new DocumentNotFoundException("Error: document not found!");
		}

		switch (documentReferenceDTO.getOperation()) {
			case UPDATE:
				log.debug(Constants.Logs.CALLED_API_UPDATE_DOCUMENT);
				// Calls Data processor to process Document
				dataProcessorClient.sendRequestToDataProcessor(documentReferenceDTO);
				break;
			case REPLACE:
				log.debug(Constants.Logs.CALLED_API_PUT_DOCUMENT);
				StagingDocumentETY ety = documentService.insert(documentReferenceDTO,wii);
				String mongoId = ety.getId(); 
				String topic = kafkaTopicCFG.getIngestionDataProcessorGenericTopic();
				kafkaService.notifyDataProcessor(topic, mongoId, ProcessorOperationEnum.REPLACE);
				break;
			default:
				// throw bad request
				throw new UnsupportedOperationException("Unsupported operation");
		}

		return new ResponseEntity<>(new DocumentResponseDTO(getLogTraceInfo()), HttpStatus.OK);
	}


	@Override
	public ResponseEntity<DocumentResponseDTO> insertDeleteDocument(HttpServletRequest request, @PathVariable String identifier) throws OperationException, KafkaException, EmptyDocumentException, DocumentNotFoundException {
		log.debug(Constants.Logs.CALLED_API_DELETE_DOCUMENT); 
		
		DocumentDTO documentReferenceDTO = new DocumentDTO();
		documentReferenceDTO.setIdentifier(identifier);
		documentReferenceDTO.setOperation(ProcessorOperationEnum.DELETE);
		documentReferenceDTO.setJsonString(null);
		documentReferenceDTO.setInsertionDate(new Date());

		if (!srvQueryReadMockEnabled && Boolean.FALSE.equals(srvQueryClient.checkExists(documentReferenceDTO.getIdentifier()))) {
			throw new DocumentNotFoundException("Error: document not found!");
		}

		dataProcessorClient.sendRequestToDataProcessor(documentReferenceDTO);
		return new ResponseEntity<>(new DocumentResponseDTO(getLogTraceInfo()), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<DocumentDTO> getDocumentById(HttpServletRequest request,
			@Size(min = 0, max = 100, message = "identifier does not match the expected size") String id)
			throws DocumentNotFoundException {
		log.info(Constants.Logs.CALLED_API_GET_DOCUMENT_BY_IDENTIFIER);  
		
		DocumentDTO document = documentService.getDocumentById(id); 
		return ResponseEntity.status(HttpStatus.OK).body(document);	
	}

	@Override
	public ResponseEntity<List<DocumentDTO>> getDocuments(HttpServletRequest request) {
		
		log.info(Constants.Logs.CALLED_API_GET_DOCUMENTS);  
		
		List<DocumentDTO> response = documentService.getDocuments(); 
		
		return ResponseEntity.status(HttpStatus.OK).body(response);	
	}
}
