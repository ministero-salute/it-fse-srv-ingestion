/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.dto.response;

import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MAX_SIZE;
import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MIN_SIZE;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** 
 * Response of the Srv Query call 
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentExistsResponseDTO extends ResponseDTO { 

	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = 21641554325694264L; 
	
	
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private String transactionId; 
	
	/** 
	 * True if the document exists on the FHIR server 
	 */
	private Boolean result; 
	

	public DocumentExistsResponseDTO() {
		super();
	}

	public DocumentExistsResponseDTO(final LogTraceInfoDTO traceInfo, Boolean _result) {
		super(traceInfo);
		result = _result; 
	}
	
}