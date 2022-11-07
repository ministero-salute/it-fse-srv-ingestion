/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.controller.handler;

import static it.finanze.sanita.fse2.ms.srvingestion.dto.response.error.ErrorBuilderDTO.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import brave.Tracer;
import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.error.base.ErrorResponseDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.LogTraceInfoDTO;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.*;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.UnsupportedOperationException;
import lombok.extern.slf4j.Slf4j;

/**
 *	Exceptions handler
 */
@ControllerAdvice
@Slf4j
public class ExceptionCTL extends ResponseEntityExceptionHandler {

    /**
     * Tracker log.
     */
    @Autowired
    private Tracer tracer;



    /**
     * Handle operation exception.
     *
     * @param ex		exception
     * @return ErrorResponseDTO  The Exception to be returned 
     */
    @ExceptionHandler(OperationException.class)
    protected ResponseEntity<ErrorResponseDTO> handleOperationException(OperationException ex) {
        // Log me
        log.warn(Constants.Logs.ERROR_HANDLER_OPERATION_EXCEPTION);
        log.error(Constants.Logs.ERROR_HANDLER_OPERATION_EXCEPTION, ex);
        // Create error DTO
        ErrorResponseDTO out = createOperationError(getLogTraceInfo(), ex);
        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        // Bye bye
        return new ResponseEntity<>(out, headers, out.getStatus());
    } 
    
    /**
     * Handle Document Not Found exception.
     *
     * @param ex		exception
     * @return ErrorResponseDTO  The Exception to be returned 
     */
    @ExceptionHandler(DocumentNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleDocumentNotFoundException(DocumentNotFoundException ex) {
        // Log me
        log.warn(Constants.Logs.ERROR_HANDLER_DOCUMENT_NOT_FOUND_EXCEPTION);
        log.error(Constants.Logs.ERROR_HANDLER_DOCUMENT_NOT_FOUND_EXCEPTION, ex);
        // Create error DTO
        ErrorResponseDTO out = createDocumentNotFoundError(getLogTraceInfo(), ex);
        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        // Bye bye
        return new ResponseEntity<>(out, headers, out.getStatus());
    } 
    
    /**
     * Handle Document Already Exists exception.
     *
     * @param ex		exception
     * @return ErrorResponseDTO  The Exception to be returned 
     */
    @ExceptionHandler(DocumentAlreadyExistsException.class)
    protected ResponseEntity<ErrorResponseDTO> handleDocumentAlreadyExistsException(DocumentAlreadyExistsException ex) {
        // Log me
        log.warn(Constants.Logs.ERROR_HANDLER_DOCUMENT_ALREADY_EXISTS_EXCEPTION);
        log.error(Constants.Logs.ERROR_HANDLER_DOCUMENT_ALREADY_EXISTS_EXCEPTION, ex);
        // Create error DTO
        ErrorResponseDTO out = createDocumentAlreadyExistsError(getLogTraceInfo(), ex);
        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        // Bye bye
        return new ResponseEntity<>(out, headers, out.getStatus());
    } 
    
    /**
     * Handle Empty Document exception.
     *
     * @param ex		exception
     * @return ErrorResponseDTO  The Exception to be returned 
     */
    @ExceptionHandler(EmptyDocumentException.class)
    protected ResponseEntity<ErrorResponseDTO> handleEmptyDocumentException(EmptyDocumentException ex) {
        // Log me
        log.warn(Constants.Logs.ERROR_HANDLER_EMPTY_DOCUMENT_EXCEPTION);
        log.error(Constants.Logs.ERROR_HANDLER_EMPTY_DOCUMENT_EXCEPTION, ex); 
        // Create error DTO
        ErrorResponseDTO out = createEmptyDocumentError(getLogTraceInfo(), ex);
        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        // Bye bye
        return new ResponseEntity<>(out, headers, out.getStatus());
    } 

    
    /**
     * Handle Connection Refused exception.
     *
     * @param ex		exception
     * @return ErrorResponseDTO  The Exception to be returned 
     */
    @ExceptionHandler(ConnectionRefusedException.class)
    protected ResponseEntity<ErrorResponseDTO> handleConnectionRefusedException(ConnectionRefusedException ex) {
        // Log me
        log.warn(Constants.Logs.ERROR_HANDLER_EMPTY_DOCUMENT_EXCEPTION);
        log.error(Constants.Logs.ERROR_HANDLER_EMPTY_DOCUMENT_EXCEPTION, ex); 
        // Create error DTO
        ErrorResponseDTO out = createConnectionRefusedError(getLogTraceInfo(), ex);
        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        // Bye bye
        return new ResponseEntity<>(out, headers, out.getStatus());
    } 


    /**
     * Handle Unsupported Operation exception.
     *
     * @param ex		exception
     * @return ErrorResponseDTO  The Exception to be returned 
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    protected ResponseEntity<ErrorResponseDTO> handleUnsupportedOperationException(UnsupportedOperationException ex) {
        // Log me
        log.warn(Constants.Logs.ERROR_HANDLER_UNSUPPORTED_OPERATION_EXCEPTION);
        log.error(Constants.Logs.ERROR_HANDLER_UNSUPPORTED_OPERATION_EXCEPTION, ex);
        // Create error DTO
        ErrorResponseDTO out = createUnsupportedOperationError(getLogTraceInfo(), ex);
        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        // Bye bye
        return new ResponseEntity<>(out, headers, out.getStatus());
    } 


    /**
     * Handle generic exception.
     *
     * @param ex		exception
     * @return ErrorResponseDTO  The Exception to be returned 
     */
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        // Log me
        log.warn(Constants.Logs.ERROR_HANDLER_GENERIC_EXCEPTION);
        log.error(Constants.Logs.ERROR_HANDLER_GENERIC_EXCEPTION, ex);
        // Create error DTO
        ErrorResponseDTO out = createGenericError(getLogTraceInfo(), ex);
        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        // Bye bye
        return new ResponseEntity<>(out, headers, out.getStatus());
    } 

    
    
    /**
     * Generate a new {@link LogTraceInfoDTO} instance
     * @return LogTraceInfoDTO The new instance
     */
    private LogTraceInfoDTO getLogTraceInfo() {
        // Create instance
        LogTraceInfoDTO out = new LogTraceInfoDTO(null, null);
        // Verify if context is available
        if (tracer.currentSpan() != null) {
            out = new LogTraceInfoDTO(
                tracer.currentSpan().context().spanIdString(),
                tracer.currentSpan().context().traceIdString());
        }
        // Return the log trace
        return out;
    }
}
