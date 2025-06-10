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
package it.finanze.sanita.fse2.ms.srvingestion.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.config.kafka.KafkaTopicCFG;
import it.finanze.sanita.fse2.ms.srvingestion.controller.AbstractCTL;
import it.finanze.sanita.fse2.ms.srvingestion.controller.IDocumentCTL;
import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.DocumentResponseDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.LogTraceInfoDTO;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;

/**
 * Document Controller
 */
@RestController
@Slf4j
public class DocumentCTL extends AbstractCTL implements IDocumentCTL {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -8298415975725845794L;

    @Autowired
    private transient DocumentSRV documentService;

    @Autowired
    private KafkaSRV kafkaService;

    @Autowired
    private transient KafkaTopicCFG kafkaTopicCFG;

    @Override
    public ResponseEntity<DocumentResponseDTO> create(HttpServletRequest request, DocumentDTO documentDTO,
            String wii)
            throws OperationException, KafkaException, EmptyDocumentException, DocumentAlreadyExistsException {

        log.debug(Constants.Logs.CALLED_API_POST_DOCUMENT);
        final LogTraceInfoDTO traceInfoDTO = getLogTraceInfo();
        log.info("[START] {}() with arguments {}={}, {}={}", "create", "traceId", traceInfoDTO.getTraceID(), "wif",
                wii);

        StagingDocumentETY ety = documentService.create(documentDTO, wii);

        // After saving in Staging Area, notify Processor for processing the document
        String mongoId = ety.getId();
        String topic = kafkaTopicCFG.getIngestionDataProcessorPublicationTopic();
        kafkaService.notifyDataProcessor(topic, mongoId, ProcessorOperationEnum.PUBLISH);

        log.info("[EXIT] {}() with arguments {}={}, {}={}", "create", "traceId", traceInfoDTO.getTraceID(), "wif", wii);
        return new ResponseEntity<>(new DocumentResponseDTO(getLogTraceInfo()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DocumentResponseDTO> replace(DocumentDTO documentDTO, String wii, HttpServletRequest request)
            throws OperationException, KafkaException, EmptyDocumentException, UnsupportedOperationException,
            DocumentNotFoundException {

        final LogTraceInfoDTO traceInfoDTO = getLogTraceInfo();
        log.info("[START] {}() with arguments {}={}, {}={}", "replace", "traceId", traceInfoDTO.getTraceID(), "wif",
                wii);

        StagingDocumentETY ety = documentService.replace(documentDTO, wii);

        // After saving in Staging Area, notify Processor for processing the document
        String mongoId = ety.getId();
        String topic = kafkaTopicCFG.getIngestionDataProcessorGenericTopic();
        kafkaService.notifyDataProcessor(topic, mongoId, ProcessorOperationEnum.REPLACE);

        log.info("[EXIT] {}() with arguments {}={}, {}={}", "replace", "traceId", traceInfoDTO.getTraceID(), "wif",
                wii);
        return new ResponseEntity<>(new DocumentResponseDTO(getLogTraceInfo()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DocumentResponseDTO> update(HttpServletRequest request, DocumentDTO documentDTO)
            throws OperationException, KafkaException, EmptyDocumentException, UnsupportedOperationException,
            DocumentAlreadyExistsException, DocumentNotFoundException {
        final LogTraceInfoDTO traceInfoDTO = getLogTraceInfo();

        log.info("[START] {}() with arguments {}={}", "update", "traceId", traceInfoDTO.getTraceID());

        Boolean result = documentService.update(documentDTO);
        if (result == false) {
            return new ResponseEntity<>(new DocumentResponseDTO(getLogTraceInfo()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("[EXIT] {}() with arguments {}={}", "update", "traceId", traceInfoDTO.getTraceID());
        return new ResponseEntity<>(new DocumentResponseDTO(getLogTraceInfo()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DocumentResponseDTO> delete(HttpServletRequest request, @PathVariable String identifier)
            throws OperationException, KafkaException, EmptyDocumentException, DocumentNotFoundException {

        log.debug(Constants.Logs.CALLED_API_DELETE_DOCUMENT);
        final LogTraceInfoDTO traceInfoDTO = getLogTraceInfo();
        log.info("[START] {}() with arguments {}={}", "delete", "traceId", traceInfoDTO.getTraceID());

        Boolean result = documentService.delete(identifier);
        if (result == false) {
            return new ResponseEntity<>(new DocumentResponseDTO(getLogTraceInfo()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("[EXIT] {}() with arguments {}={}", "delete", "traceId", traceInfoDTO.getTraceID());
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
