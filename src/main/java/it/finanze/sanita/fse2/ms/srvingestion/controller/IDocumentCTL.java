/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.controller;

import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MAX_SIZE;
import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MIN_SIZE;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody; 

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.DocumentResponseDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.error.base.ErrorResponseDTO;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentAlreadyExistsException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.KafkaException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.UnsupportedOperationException; 



/**
 * Ingestion Controller.
 * 
 */
@RequestMapping(path = "/v1")
@Tag(name = "Document Ingestion Controller")
@Validated
public interface IDocumentCTL extends Serializable {

	/**
	 * Function to process a Creation Request for a document, ingested by the Gateway onto the EDS. 
	 * The function processes the document by inserting a new record on the staging database, and 
	 * sending a Kafka message on a topic for the Data Processor Microservice to process. 
	 * 
	 * @param request  The HTTP Servlet Request 
	 * @param document  The Document to be ingested onto the EDS 
	 * @return DocumentResponseDTO  A DTO representing the Document Response 
	 * @throws IOException  Generic IO Exception 
	 * @throws OperationException  Generic MongoDB Exception 
	 * @throws KafkaException  Kafka Exception 
	 * @throws EmptyDocumentException  An exception thrown when the document content is empty 
	 * @throws DocumentAlreadyExistsException  An exception thrown when the document already exists on MongoDB 
	 */
    @PostMapping(value = "/document/workflowinstanceid/{wii}", produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Add a document to the staging MongoDB", description = "Servizio che consente di aggiungere un documento alla base dati di staging.")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creazione Documento avvenuta con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
    ResponseEntity<DocumentResponseDTO> addDocument(HttpServletRequest request, @RequestBody DocumentDTO document,
   		 @PathVariable @Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE, message = "identifier does not match the expected size") String identifier) throws IOException, OperationException, KafkaException, EmptyDocumentException, DocumentAlreadyExistsException;

    /**
     * Function to process a Replace Request for a document, from the Gateway onto the EDS. 
     * The function takes in input the new Document DTO and, according to how the flow is set 
     * (synchronous or asynchronous, according to the properties file), processes it accordingly  
     * 
     * @param request  The HTTP Servlet Request 
     * @param body  The body of the request 
     * @return DocumentResponseDTO  A DTO representing the Document Response 
     * @throws OperationException  Generic MongoDB Exception 
     * @throws KafkaException  Kafka Exception 
     * @throws EmptyDocumentException  An exception thrown when the document content is empty
     * @throws UnsupportedOperationException  An exception thrown when the requested operation is not supported  
     * @throws DocumentAlreadyExistsException  An exception thrown when the document already exists on MongoDB 
     * @throws DocumentNotFoundException An exception thrown when the document is not found on MongoDB 
     */
    @PutMapping(value = "/document", produces = {
            MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Add a Document Reference for update to MongoDB", description = "Servizio che consente di inserire un record per replace sul MongoDB di staging.")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Creazione Document Reference per replace avvenuta con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dictionary non trovato sul database", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
    ResponseEntity<DocumentResponseDTO> insertReplaceDocument(HttpServletRequest request, @RequestBody DocumentDTO body) throws OperationException, KafkaException, EmptyDocumentException, UnsupportedOperationException, DocumentAlreadyExistsException, DocumentNotFoundException;

    /** 
     * Function to process an Update Request for a document, from the Gateway onto the EDS. 
     * The function takes in input the new Document DTO and, according to how the flow is set 
     * (synchronous or asynchronous, according to the properties file), processes it accordingly  
     * 
     * @param request  The HTTP Servlet Request 
     * @param body  The body of the request 
     * @return DocumentResponseDTO  A DTO representing the Document Response 
     * @throws OperationException  Generic MongoDB Exception 
     * @throws KafkaException  Kafka Exception 
     * @throws EmptyDocumentException  An exception thrown when the document content is empty
     * @throws UnsupportedOperationException  An exception thrown when the requested operation is not supported  
     * @throws DocumentAlreadyExistsException  An exception thrown when the document already exists on MongoDB 
     * @throws DocumentNotFoundException An exception thrown when the document is not found on MongoDB 
     */
    @PutMapping(value = "/document/metadata", produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Update metadata to EDS", description = "Servizio che consente di inserire un record per aggiornamento sul MongoDB di staging.")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Creazione Document Reference per update avvenuta con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dictionary non trovato sul database", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
    ResponseEntity<DocumentResponseDTO> insertUpdateDocument(HttpServletRequest request, @RequestBody DocumentDTO body) throws OperationException, KafkaException, EmptyDocumentException, UnsupportedOperationException, DocumentAlreadyExistsException, DocumentNotFoundException;
    
    /**
     * Function to process a Delete Request for a document, given its identifier. 
     * 
     * @param request  The HTTP Servlet Request 
     * @param identifier  The identifier of the document to delete 
     * @return DocumentResponseDTO  A DTO representing the Document Response 
     * @throws OperationException  Generic MongoDB Exception 
     * @throws KafkaException  Kafka Exception 
     * @throws EmptyDocumentException  An exception thrown when the document content is empty
     * @throws DocumentNotFoundException An exception thrown when the document is not found on MongoDB 
     */
    @DeleteMapping(value = "/document/identifier/{identifier}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Delete document from MongoDB given its identifier", description = "Servizio che consente di cancellare un documento dalla base dati dato il suo identifier.")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cancellazione Document reference avvenuta con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dizionario non trovato sul database", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
    ResponseEntity<DocumentResponseDTO> insertDeleteDocument(HttpServletRequest request, @PathVariable @Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE, message = "identifier does not match the expected size") String identifier) throws OperationException, KafkaException, EmptyDocumentException, DocumentNotFoundException;
    
  /**
   * Function to retrieve a document from the staging database given its Mongo ID. 
   * 
   * @param request  The HTTP Servlet Request 
   * @param id  The Mongo ID of the document 
   * @return DocumentReferenceDTO  A DTO representing the Document Response
   * @throws DocumentNotFoundException An exception thrown when the document is not found on MongoDB 
   */
    @GetMapping(value = "/document/{id}", produces = {
			MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Returns a document from the staging database given its identifier", description = "Servizio che consente di ritornare un documento dal database di staging dato il suo Mongo ID.")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ricerca Documento avvenuta con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Schematron non trovato sul database", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
    ResponseEntity<DocumentDTO> getDocumentById(HttpServletRequest request, @PathVariable @Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE, message = "identifier does not match the expected size") String id) throws DocumentNotFoundException; 
    
    /**
     * Function to retrieve the list of all documents from the staging database. 
     * 
     * @param request  The Http Servlet Request 
     * @return DocumentReferenceDTO  List of Document Reference DTO
     */
    @GetMapping(value = "/document", produces = {
 			MediaType.APPLICATION_JSON_VALUE })
     @Operation(summary = "Returns the list of all documents from the staging database", description = "Servizio che consente di ritornare la lista dei documenti dal database di staging.")
     @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = DocumentDTO.class)))
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Richiesta Documents avvenuta con successo", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DocumentDTO.class))),
             @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ErrorResponseDTO.class))) })
    ResponseEntity<List<DocumentDTO>> getDocuments(HttpServletRequest request); 
    
}
