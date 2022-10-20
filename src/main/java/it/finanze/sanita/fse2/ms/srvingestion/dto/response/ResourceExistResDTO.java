/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Riccardo Bonesi
 *
 *	DTO used to return check exist result.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceExistResDTO extends ResponseDTO {


	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = -1550025571939901939L;

	/** 
	 * True if the document already exists on the FHIR Server 
	 */
	private boolean exist;

	public ResourceExistResDTO() {
		super();
		exist = false;
	}

	public ResourceExistResDTO(final LogTraceInfoDTO traceInfo, final boolean inExist) {
		super(traceInfo);
		exist = inExist;
	}
}
