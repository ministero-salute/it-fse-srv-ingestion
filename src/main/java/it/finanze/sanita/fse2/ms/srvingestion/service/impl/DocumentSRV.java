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
package it.finanze.sanita.fse2.ms.srvingestion.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;
import it.finanze.sanita.fse2.ms.srvingestion.repository.mongo.impl.DocumentRepo;
import it.finanze.sanita.fse2.ms.srvingestion.service.IDocumentSRV;

/** 
 * Document Service Implementation 
 *
 */
@Service
public class DocumentSRV implements IDocumentSRV {
	
	
	/** 
	 * Document Repository 
	 */
	@Autowired
	private DocumentRepo documentRepo; 
	
	
	@Override
	public StagingDocumentETY insert(final DocumentDTO dto, final String wii) throws OperationException, EmptyDocumentException {
		
		// If the object does not have an OperationCode, it is a creation
		if (ObjectUtils.isEmpty(dto.getOperation())) {
			dto.setOperation(ProcessorOperationEnum.PUBLISH);
		} 
		
		if ((dto.getOperation().equals(ProcessorOperationEnum.PUBLISH)
				|| dto.getOperation().equals(ProcessorOperationEnum.UPDATE)) && ObjectUtils.isEmpty(dto.getJsonString())) {
				throw new EmptyDocumentException(Constants.Logs.ERROR_EMPTY_DOCUMENT); 
		} 
		
		StagingDocumentETY document = parseDtoToEty(dto);
		document.setWorkflowInstanceId(wii);
		return documentRepo.insert(document); 
	} 
	
	@Override
	public DocumentDTO getDocumentById(String id) throws DocumentNotFoundException {
		StagingDocumentETY ety =  documentRepo.findById(id);
		
		if(ObjectUtils.isEmpty(ety.getId())) {
			throw new DocumentNotFoundException(Constants.Logs.ERROR_DOCUMENT_NOT_FOUND); 
		} 
		
		return parseEtyToDto(ety); 
	} 
	
	
	@Override
	public List<DocumentDTO> getDocuments() {
		List<StagingDocumentETY> etyList = documentRepo.findAll();
		return buildDtoFromEty(etyList); 	
	}

	
	
	public DocumentDTO parseEtyToDto(StagingDocumentETY stagingDocumentETY) {
		DocumentDTO output = new DocumentDTO(); 
		
		if(!ObjectUtils.isEmpty(stagingDocumentETY.getIdentifier())) {
			output.setIdentifier(stagingDocumentETY.getIdentifier());
		} 
		if(!ObjectUtils.isEmpty(stagingDocumentETY.getOperation())) {
			output.setOperation(stagingDocumentETY.getOperation());
		}
		if(!ObjectUtils.isEmpty(stagingDocumentETY.getDocument()) ) {
			output.setJsonString(stagingDocumentETY.getDocument().toJson());
		}
		if(!ObjectUtils.isEmpty(stagingDocumentETY.getInsertionDate()) ) {
			output.setInsertionDate(stagingDocumentETY.getInsertionDate());
		}
		
		return output;
	} 
	
	public StagingDocumentETY parseDtoToEty(DocumentDTO documentReferenceDTO) {
		StagingDocumentETY output = new StagingDocumentETY();
		
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
	
	public List<DocumentDTO> buildDtoFromEty(List<StagingDocumentETY> documentEtyList) {
		List<DocumentDTO> output = new ArrayList<>();
		
		for(StagingDocumentETY document : documentEtyList) {
			output.add(parseEtyToDto(document));
		}
		
		return output;
	}
	

}
