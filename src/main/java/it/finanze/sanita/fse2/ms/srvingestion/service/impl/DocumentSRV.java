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

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import it.finanze.sanita.fse2.ms.srvingestion.client.impl.DataProcessorClient;
import it.finanze.sanita.fse2.ms.srvingestion.client.impl.SrvQueryClient;
import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.converter.DocumentConverter;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.BusinessException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.ConnectionRefusedException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;
import it.finanze.sanita.fse2.ms.srvingestion.repository.mongo.impl.DocumentStagingRepo;
import it.finanze.sanita.fse2.ms.srvingestion.service.IDocumentSRV;

/** 
 * Document Service Implementation 
 *
 */
@Service
public class DocumentSRV implements IDocumentSRV {
	
	
	@Autowired
	private DocumentStagingRepo documentStagingRepo; 
	
	@Autowired
	private transient SrvQueryClient srvQueryClient;
	
	@Autowired
	private transient DataProcessorClient dataProcessorClient;

	@Autowired
	DocumentConverter documentConverter;
	
	@Override
	public StagingDocumentETY insert(final DocumentDTO dto, final String wii) throws OperationException, EmptyDocumentException {
				
		if (ObjectUtils.isEmpty(dto.getJsonString())) {
				throw new EmptyDocumentException(Constants.Logs.ERROR_EMPTY_DOCUMENT); 
		} 
		StagingDocumentETY document = documentConverter.toEntity(dto);
		document.setWorkflowInstanceId(wii);
		document.setOperation(ProcessorOperationEnum.PUBLISH);
		return documentStagingRepo.insert(document); 
	} 
	
	@Override
	public StagingDocumentETY replace(final DocumentDTO dto, final String wii) throws OperationException, EmptyDocumentException, DocumentNotFoundException  {
		
		boolean exist = srvQueryClient.checkExists(dto.getIdentifier());
		if (Boolean.FALSE.equals(exist)) {
			throw new DocumentNotFoundException("Error: document not found!");
		}

		if (ObjectUtils.isEmpty(dto.getJsonString())) {
			throw new EmptyDocumentException(Constants.Logs.ERROR_EMPTY_DOCUMENT); 
		} 
		
		StagingDocumentETY document = documentConverter.toEntity(dto);
		document.setWorkflowInstanceId(wii);
		document.setOperation(ProcessorOperationEnum.REPLACE);
		return documentStagingRepo.insert(document); 
	}
	
	@Override
	public Boolean update(final DocumentDTO dto) throws EmptyDocumentException, DocumentNotFoundException, ConnectionRefusedException, BusinessException {
		
		boolean exist = srvQueryClient.checkExists(dto.getIdentifier());
		if (Boolean.FALSE.equals(exist)) {
			throw new DocumentNotFoundException("Error: document not found!");
		}

		if (ObjectUtils.isEmpty(dto.getJsonString())) {
			throw new EmptyDocumentException(Constants.Logs.ERROR_EMPTY_DOCUMENT); 
		} 
	
		dto.setOperation(ProcessorOperationEnum.UPDATE);
		return dataProcessorClient.sendRequestToDataProcessor(dto);

	}
	
	@Override
	public Boolean delete(final String id) throws DocumentNotFoundException, ConnectionRefusedException, BusinessException {
		
		boolean exist = srvQueryClient.checkExists(id);
		if (Boolean.FALSE.equals(exist)) {
			throw new DocumentNotFoundException("Error: document not found!");
		}

		DocumentDTO dto = new DocumentDTO();
		dto.setIdentifier(id);
		dto.setOperation(ProcessorOperationEnum.DELETE);
		dto.setJsonString(null);
		dto.setInsertionDate(new Date());
		return dataProcessorClient.sendRequestToDataProcessor(dto);

	}

	
	@Override
	public DocumentDTO getDocumentById(String id) throws DocumentNotFoundException {
		StagingDocumentETY ety =  documentStagingRepo.findById(id);
		
		if(ObjectUtils.isEmpty(ety.getId())) {
			throw new DocumentNotFoundException(Constants.Logs.ERROR_DOCUMENT_NOT_FOUND); 
		} 
		
		return documentConverter.toDto(ety); 
	} 
	
	
	@Override
	public List<DocumentDTO> getDocuments() {
		List<StagingDocumentETY> etyList = documentStagingRepo.findAll();
		return documentConverter.toDtoList(etyList); 	
	}
	

}
