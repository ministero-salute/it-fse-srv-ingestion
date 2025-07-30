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

import it.finanze.sanita.fse2.ms.srvingestion.client.IDataProcessorClient;
import it.finanze.sanita.fse2.ms.srvingestion.client.ISrvQueryClient;
import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.config.kafka.KafkaTopicCFG;
import it.finanze.sanita.fse2.ms.srvingestion.dto.UdpDocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.request.SendToUdpDocumentRequestDTO;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.KafkaException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.repository.IDocumentRepo;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;
import it.finanze.sanita.fse2.ms.srvingestion.service.IDocumentSRV;
import it.finanze.sanita.fse2.ms.srvingestion.service.IKafkaSRV;

/**
 * Document Service Implementation
 *
 */
@Service
public class DocumentSRV implements IDocumentSRV {

    private IDocumentRepo documentRepo;
    private ISrvQueryClient srvQueryClient;
    private IKafkaSRV kafkaService;
    private IDataProcessorClient dataProcessorClient;
    private KafkaTopicCFG kafkaTopicCFG;

    public DocumentSRV(@Autowired IDocumentRepo documentRepo, @Autowired ISrvQueryClient srvQueryClient,
            @Autowired IKafkaSRV kafkaService,
            @Autowired IDataProcessorClient dataProcessorClient, @Autowired KafkaTopicCFG kafkaTopicCFG) {
        this.documentRepo = documentRepo;
        this.srvQueryClient = srvQueryClient;
        this.kafkaService = kafkaService;
        this.dataProcessorClient = dataProcessorClient;
        this.kafkaTopicCFG = kafkaTopicCFG;
    }

    @Override
    public boolean publish(final SendToUdpDocumentRequestDTO dto, final String wii)
            throws OperationException, EmptyDocumentException, KafkaException {

        if (ObjectUtils.isEmpty(dto.getJsonString())) {
            throw new EmptyDocumentException(Constants.Logs.ERROR_EMPTY_DOCUMENT);
        }

        StagingDocumentETY document = StagingDocumentETY.builder()
                .identifier(dto.getIdentifier())
                .operation(ProcessorOperationEnum.PUBLISH)
                .rde(dto.getRde())
                .document(org.bson.Document.parse(dto.getJsonString()))
                .insertionDate(new Date())
                .workflowInstanceId(wii)
                .build();

        StagingDocumentETY ety = documentRepo.save(document);

        kafkaService.notifyUdpDataProcessor(kafkaTopicCFG.getIngestionDataProcessorPublicationTopic(), ety.getId(),
                ProcessorOperationEnum.PUBLISH);

        return true;
    }

    @Override
    public boolean replace(SendToUdpDocumentRequestDTO dto, String wii)
            throws DocumentNotFoundException, EmptyDocumentException, OperationException, KafkaException {

        boolean exist = srvQueryClient.checkExists(dto.getIdentifier());

        if (Boolean.FALSE.equals(exist)) {
            throw new DocumentNotFoundException("Error: document not found!");
        }

        if (ObjectUtils.isEmpty(dto.getJsonString())) {
            throw new EmptyDocumentException(Constants.Logs.ERROR_EMPTY_DOCUMENT);
        }

        StagingDocumentETY document = StagingDocumentETY.builder()
                .identifier(dto.getIdentifier())
                .operation(ProcessorOperationEnum.REPLACE)
                .rde(dto.getRde())
                .document(org.bson.Document.parse(dto.getJsonString()))
                .insertionDate(new Date())
                .workflowInstanceId(wii)
                .build();

        StagingDocumentETY ety = documentRepo.save(document);

        kafkaService.notifyUdpDataProcessor(kafkaTopicCFG.getIngestionDataProcessorGenericTopic(), ety.getId(),
                ProcessorOperationEnum.REPLACE);

        return true;
    }

    @Override
    public boolean update(SendToUdpDocumentRequestDTO dto) {

        UdpDocumentDTO documentDTO = UdpDocumentDTO.builder()
                .identifier(dto.getIdentifier())
                .operation(ProcessorOperationEnum.UPDATE)
                .rde(dto.getRde())
                .jsonString(dto.getJsonString())
                .insertionDate(new Date())
                .build();

        return dataProcessorClient.sendRequestToDataProcessor(documentDTO);
    }

    @Override
    public boolean delete(String identifier) throws DocumentNotFoundException {

        UdpDocumentDTO documentDTO = UdpDocumentDTO.builder()
                .identifier(identifier)
                .operation(ProcessorOperationEnum.DELETE)
                .jsonString(null)
                .insertionDate(new Date())
                .build();

        boolean exist = srvQueryClient.checkExists(identifier);

        if (Boolean.FALSE.equals(exist)) {
            throw new DocumentNotFoundException("Error: document not found!");
        }

        return dataProcessorClient.sendRequestToDataProcessor(documentDTO);
    }

    @Override
    public UdpDocumentDTO getDocumentById(String id) throws DocumentNotFoundException {
        StagingDocumentETY ety = documentRepo.findById(id);

        if (ObjectUtils.isEmpty(ety.getId())) {
            throw new DocumentNotFoundException(Constants.Logs.ERROR_DOCUMENT_NOT_FOUND);
        }

        return new UdpDocumentDTO(ety);
    }

    @Override
    public List<UdpDocumentDTO> getDocuments() {
        List<StagingDocumentETY> etyList = documentRepo.findAll();
        return UdpDocumentDTO.buildListFromEty(etyList);
    }

}
