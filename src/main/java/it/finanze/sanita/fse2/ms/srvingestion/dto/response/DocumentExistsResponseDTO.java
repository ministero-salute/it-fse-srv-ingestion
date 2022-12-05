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
	 * True if the document exists on the FHIR server 
	 */
	private Boolean result; 
	

	public DocumentExistsResponseDTO() {
		super();
	}

	public DocumentExistsResponseDTO(final LogTraceInfoDTO traceInfo, Boolean inResult) {
		super(traceInfo);
		result = inResult; 
	}
	
}