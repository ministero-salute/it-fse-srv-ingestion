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
import org.springframework.web.bind.annotation.RestController;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.controller.AbstractCTL;
import it.finanze.sanita.fse2.ms.srvingestion.controller.IDocumentCTL;
import it.finanze.sanita.fse2.ms.srvingestion.dto.UdpDocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.request.SendToUdpDocumentRequestDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.DocumentResponseDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.LogTraceInfoDTO;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentAlreadyExistsException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.KafkaException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.UnsupportedOperationException;
import it.finanze.sanita.fse2.ms.srvingestion.service.impl.DocumentSRV;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Document Controller
 */
@RestController
@Slf4j
public class DocumentCTL extends AbstractCTL implements IDocumentCTL {

    @Autowired
    private transient DocumentSRV documentService;

    @Override
    public ResponseEntity<DocumentResponseDTO> publishDocument(HttpServletRequest request,
            SendToUdpDocumentRequestDTO document,
            String workflowInstanceId) throws OperationException, EmptyDocumentException, KafkaException {
        final LogTraceInfoDTO traceInfoDTO = getLogTraceInfo();

        log.debug(Constants.Logs.CALLED_API_POST_DOCUMENT);
        log.info("[START] {}() with arguments {}={}, {}={}", "create", "traceId", traceInfoDTO.getTraceID(), "wif",
                workflowInstanceId);

        boolean result = documentService.publish(document, workflowInstanceId);
        DocumentResponseDTO out = new DocumentResponseDTO(getLogTraceInfo());
        out.setResult(result);

        log.info("[EXIT] {}() with arguments {}={}, {}={}", "create", "traceId", traceInfoDTO.getTraceID(), "wif",
                workflowInstanceId);
        return new ResponseEntity<>(out, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DocumentResponseDTO> replaceDocument(HttpServletRequest request,
            SendToUdpDocumentRequestDTO body, String wii)
            throws OperationException, KafkaException, EmptyDocumentException,
            UnsupportedOperationException, DocumentAlreadyExistsException, DocumentNotFoundException {

        final LogTraceInfoDTO traceInfoDTO = getLogTraceInfo();
        log.debug(Constants.Logs.CALLED_API_PUT_DOCUMENT);
        log.info("[START] {}() with arguments {}={}, {}={}", "replace", "traceId",
                traceInfoDTO.getTraceID(), "wif", wii);

        boolean result = documentService.replace(body, wii);
        DocumentResponseDTO out = new DocumentResponseDTO(getLogTraceInfo());
        out.setResult(result);

        log.info("[EXIT] {}() with arguments {}={}, {}={}", "replace", "traceId",
                traceInfoDTO.getTraceID(), "wif", wii);
        return new ResponseEntity<>(out, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DocumentResponseDTO> updateDocument(HttpServletRequest request,
            SendToUdpDocumentRequestDTO document) throws OperationException, KafkaException, EmptyDocumentException,
            UnsupportedOperationException, DocumentAlreadyExistsException, DocumentNotFoundException {
        final LogTraceInfoDTO traceInfoDTO = getLogTraceInfo();
        log.debug(Constants.Logs.CALLED_API_UPDATE_DOCUMENT);
        log.info("[START] {}() with arguments {}={}", "update", "traceId", traceInfoDTO.getTraceID());

        boolean result = documentService.update(document);
        DocumentResponseDTO out = new DocumentResponseDTO(getLogTraceInfo());
        out.setResult(result);

        log.info("[EXIT] {}() with arguments {}={}", "update", "traceId", traceInfoDTO.getTraceID());
        return new ResponseEntity<>(out, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DocumentResponseDTO> deleteDocument(HttpServletRequest request, String identifier)
            throws OperationException, KafkaException, EmptyDocumentException, DocumentNotFoundException {
        final LogTraceInfoDTO traceInfoDTO = getLogTraceInfo();
        log.debug(Constants.Logs.CALLED_API_DELETE_DOCUMENT);
        log.info("[START] {}() with arguments {}={}", "delete", "traceId", traceInfoDTO.getTraceID());

        boolean result = documentService.delete(identifier);
        DocumentResponseDTO out = new DocumentResponseDTO(getLogTraceInfo());
        out.setResult(result);

        log.info("[EXIT] {}() with arguments {}={}", "delete", "traceId", traceInfoDTO.getTraceID());
        return new ResponseEntity<>(out, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UdpDocumentDTO> getDocumentById(HttpServletRequest request, String id)
            throws DocumentNotFoundException {
        log.info(Constants.Logs.CALLED_API_GET_DOCUMENT_BY_IDENTIFIER);

        UdpDocumentDTO document = documentService.getDocumentById(id);

        return ResponseEntity.status(HttpStatus.OK).body(document);
    }

    @Override
    public ResponseEntity<List<UdpDocumentDTO>> getDocuments(HttpServletRequest request) {

        log.info(Constants.Logs.CALLED_API_GET_DOCUMENTS);

        List<UdpDocumentDTO> response = documentService.getDocuments();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
