/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import brave.Tracer;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.LogTraceInfoDTO; 

/**
 * 
 *
 *	Abstract controller.
 */
public abstract class AbstractCTL implements Serializable {


	
	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = 320718612495182423L; 
	
	
	@Autowired
	private transient Tracer tracer;


	protected LogTraceInfoDTO getLogTraceInfo() {
		LogTraceInfoDTO out = new LogTraceInfoDTO(null, null);
		if (tracer.currentSpan() != null) {
			out = new LogTraceInfoDTO(
					tracer.currentSpan().context().spanIdString(), 
					tracer.currentSpan().context().traceIdString());
		}
		return out;
	}

}

