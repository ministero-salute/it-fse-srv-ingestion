package it.finanze.sanita.fse2.ms.srvingestion.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentReferenceDTO;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.DocumentReferenceETY;
import it.finanze.sanita.fse2.ms.srvingestion.repository.mongo.impl.DocumentRepo;
import it.finanze.sanita.fse2.ms.srvingestion.service.IDocumentSRV;

/** 
 * Document Service Implementation 
 *
 */
@Service
public class DocumentSRV implements IDocumentSRV, Serializable {
	
	
	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = -2451872011643635887L; 
	
	/** 
	 * Document Repository 
	 */
	@Autowired
	private DocumentRepo documentRepo; 
	
	
	@Override
	public DocumentReferenceETY insert(DocumentReferenceDTO dto) throws OperationException, EmptyDocumentException {
		
		// If the object does not have an OperationCode, it is a creation
		if (ObjectUtils.isEmpty(dto.getOperation())) {
			dto.setOperation(ProcessorOperationEnum.PUBLISH);
		} 
		
		if ((dto.getOperation().equals(ProcessorOperationEnum.PUBLISH)
				|| dto.getOperation().equals(ProcessorOperationEnum.UPDATE)) && ObjectUtils.isEmpty(dto.getJsonString())) {
				throw new EmptyDocumentException(Constants.Logs.ERROR_EMPTY_DOCUMENT); 
		} 
		
		DocumentReferenceETY document = parseDtoToEty(dto); 
		return documentRepo.insert(document); 
	} 
	
	@Override
	public void delete(String identifier) {
		documentRepo.deleteByIdentifier(identifier);
	} 
	
	
	@Override
	public DocumentReferenceDTO getDocumentById(String id) throws DocumentNotFoundException {
		DocumentReferenceETY ety =  documentRepo.findById(id); 
		
		if(ObjectUtils.isEmpty(ety.getId())) {
			throw new DocumentNotFoundException(Constants.Logs.ERROR_DOCUMENT_NOT_FOUND); 
		} 
		
		return parseEtyToDto(ety); 
	} 
	
	
	@Override
	public List<DocumentReferenceDTO> getDocuments() {
		List<DocumentReferenceETY> etyList = documentRepo.findAll(); 
		return buildDtoFromEty(etyList); 	
	}

	
	
	public DocumentReferenceDTO parseEtyToDto(DocumentReferenceETY documentReferenceETY) {
		DocumentReferenceDTO output = new DocumentReferenceDTO(); 
		
		if(!ObjectUtils.isEmpty(documentReferenceETY.getIdentifier())) {
			output.setIdentifier(documentReferenceETY.getIdentifier());
		} 
		if(!ObjectUtils.isEmpty(documentReferenceETY.getOperation())) {
			output.setOperation(documentReferenceETY.getOperation());
		}
		if(!ObjectUtils.isEmpty(documentReferenceETY.getDocument()) ) {
			output.setJsonString(documentReferenceETY.getDocument().toJson());
		}
		if(!ObjectUtils.isEmpty(documentReferenceETY.getInsertionDate()) ) {
			output.setInsertionDate(documentReferenceETY.getInsertionDate());
		}
		
		return output;
	} 
	
	public DocumentReferenceETY parseDtoToEty(DocumentReferenceDTO documentReferenceDTO) {
		DocumentReferenceETY output = new DocumentReferenceETY(); 
		
		if(!ObjectUtils.isEmpty(documentReferenceDTO.getIdentifier())) {
			output.setIdentifier(documentReferenceDTO.getIdentifier());
		} 
		if(!ObjectUtils.isEmpty(documentReferenceDTO.getOperation())) {
			output.setOperation(documentReferenceDTO.getOperation());
		} 
		if(!ObjectUtils.isEmpty(documentReferenceDTO.getJsonString())) {
			output.setDocument(Document.parse(documentReferenceDTO.getJsonString()));
		}
		if(!ObjectUtils.isEmpty(documentReferenceDTO.getInsertionDate())) {
			output.setInsertionDate(documentReferenceDTO.getInsertionDate());
		}
			
		return output;
	} 
	
	public List<DocumentReferenceDTO> buildDtoFromEty(List<DocumentReferenceETY> documentEtyList) {
		List<DocumentReferenceDTO> output = new ArrayList<>();
		
		for(DocumentReferenceETY document : documentEtyList) {
			output.add(parseEtyToDto(document));
		}
		
		return output;
	}
	

}
