/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** 
 * Document Response DTO 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentResponseDTO extends ResponseDTO { 


	public DocumentResponseDTO() {
		super();
	}

	public DocumentResponseDTO(final LogTraceInfoDTO traceInfo) {
		super(traceInfo);
	}
	
}