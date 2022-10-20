/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.dto.response.error;

import static it.finanze.sanita.fse2.ms.srvingestion.dto.response.error.ErrorInstance.*;
import static org.apache.http.HttpStatus.*;

import it.finanze.sanita.fse2.ms.srvingestion.exceptions.ConnectionRefusedException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentAlreadyExistsException;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.LogTraceInfoDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.error.base.ErrorResponseDTO;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.DocumentNotFoundException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.EmptyDocumentException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.OperationException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.UnsupportedOperationException;


/** 
 * This class contains all the methods for building error responses
 * according to the given exception
 *
 */
public final class ErrorBuilderDTO {

    public static final String GENERIC_ERROR = "Generic error";
    public static final String OPERATION_ERROR = "MongoDB: Generic error";
    public static final String UNSUPPORTED_OPERATION_ERROR = "Unsupported operation"; 
    public static final String DOCUMENT_NOT_FOUND_ERROR = "Error: document not found in staging database";
    public static final String DOCUMENT_ALREADY_EXISTS_ERROR = "Error: document already exists on Log"; 
    public static final String CONNECTION_REFUSED_ERROR = "Error: connection refused by Host";
    public static final String EMPTY_DOCUMENT_ERROR = "Error: bundle is empty"; 


    public static final String GENERIC_TITLE = "Generic error";
    public static final String UNSUPPORTED_OPERATION_TITLE = "Unsupported operation"; 
    public static final String OPERATION_TITLE = "MongoDB Error"; 
    public static final String DOCUMENT_NOT_FOUND_TITLE = "Document Not Found";
    public static final String DOCUMENT_ALREADY_EXISTS_TITLE = "Document Already Exists";
    public static final String CONNECTION_REFUSED_TITLE = "Connection Refused";
    public static final String EMPTY_DOCUMENT_TITLE = "Empty Bundle"; 



    /**
     * Private constructor to disallow to access from other classes
     */
    private ErrorBuilderDTO() {}


    /** 
     * 
     * @param trace  The LogInfoDTO Object, with Trace and Span ID 
     * @param ex  The exception 
     * @return ErrorResponseDTO  The error response DTO 
     */
    public static ErrorResponseDTO createGenericError(LogTraceInfoDTO trace, Exception ex) {
        return new ErrorResponseDTO(
            trace,
            ErrorType.SERVER.getType(),
            ErrorType.SERVER.getTitle(),
            ex.getMessage(),
            SC_INTERNAL_SERVER_ERROR,
            ErrorType.SERVER.toInstance(Server.INTERNAL)
        );
    }

    /** 
     * 
     * @param trace  The LogInfoDTO Object, with Trace and Span ID 
     * @param ex  The exception 
     * @return ErrorResponseDTO  The error response DTO 
     */
    public static ErrorResponseDTO createOperationError(LogTraceInfoDTO trace, OperationException ex) {
        return new ErrorResponseDTO(
            trace,
            ErrorType.SERVER.getType(),
            ErrorType.SERVER.getTitle(),
            ex.getMessage(),
            SC_INTERNAL_SERVER_ERROR,
            ErrorType.SERVER.toInstance(Server.INTERNAL)
        );
    } 
    
    /** 
     * 
     * @param trace  The LogInfoDTO Object, with Trace and Span ID 
     * @param ex  The exception 
     * @return ErrorResponseDTO  The error response DTO 
     */
    public static ErrorResponseDTO createUnsupportedOperationError(LogTraceInfoDTO trace, UnsupportedOperationException ex) {
        return new ErrorResponseDTO(
            trace,
            ErrorType.CLIENT.getType(),
            ErrorType.CLIENT.getTitle(),
            ex.getMessage(),
            SC_BAD_REQUEST,
            ErrorType.CLIENT.toInstance(ErrorInstance.Client.UNSUPPORTED)
        );
    } 
    
    /** 
     * 
     * @param trace  The LogInfoDTO Object, with Trace and Span ID 
     * @param ex  The exception 
     * @return ErrorResponseDTO  The error response DTO 
     */
    public static ErrorResponseDTO createDocumentNotFoundError(LogTraceInfoDTO trace, DocumentNotFoundException ex) {
        return new ErrorResponseDTO(
            trace,
            ErrorType.RESOURCE.getType(),
            ErrorType.RESOURCE.getTitle(),
            ex.getMessage(),
            SC_NOT_FOUND,
            ErrorType.RESOURCE.toInstance(Resource.NOT_FOUND)
        );
    } 
    
    /** 
     * 
     * @param trace  The LogInfoDTO Object, with Trace and Span ID 
     * @param ex  The exception 
     * @return ErrorResponseDTO  The error response DTO 
     */
    public static ErrorResponseDTO createDocumentAlreadyExistsError(LogTraceInfoDTO trace, DocumentAlreadyExistsException ex) {
        return new ErrorResponseDTO(
            trace,
            ErrorType.RESOURCE.getType(),
            ErrorType.RESOURCE.getTitle(),
            ex.getMessage(),
            SC_BAD_REQUEST,
            ErrorType.RESOURCE.toInstance(Resource.DOCUMENT_ALREADY_EXISTS)
        );
    } 
    
    /** 
     * 
     * @param trace  The LogInfoDTO Object, with Trace and Span ID 
     * @param ex  The exception 
     * @return ErrorResponseDTO  The error response DTO 
     */
    public static ErrorResponseDTO createConnectionRefusedError(LogTraceInfoDTO trace, ConnectionRefusedException ex) {
        return new ErrorResponseDTO(
            trace,
            ErrorType.SERVER.getType(),
            ErrorType.SERVER.getTitle(),
            ex.getMessage(),
            SC_BAD_GATEWAY,
            ErrorType.SERVER.toInstance(Server.CONNECTION_REFUSED)
        );
    } 
    
    /** 
     * 
     * @param trace  The LogInfoDTO Object, with Trace and Span ID 
     * @param ex  The exception 
     * @return ErrorResponseDTO  The error response DTO 
     */
    public static ErrorResponseDTO createEmptyDocumentError(LogTraceInfoDTO trace, EmptyDocumentException ex) {
        return new ErrorResponseDTO(
            trace,
            ErrorType.RESOURCE.getType(),
            ErrorType.RESOURCE.getTitle(),
            ex.getMessage(),
            SC_NOT_FOUND,
            ErrorType.RESOURCE.toInstance(Resource.EMPTY)
        );
    }
}
