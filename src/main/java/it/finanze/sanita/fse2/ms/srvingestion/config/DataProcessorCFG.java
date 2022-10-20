/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/** 
 * Data Processor Configuration Class 
 *
 */
@Data
@Component
public class DataProcessorCFG implements Serializable {

	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = -6129414342015913264L; 

	
	
	/**
	 *  EDS Data Processor Host 
	 */
	@Value("${eds-dataprocessor.url.host}")
	private String edsDataProcessorHost;
	
}
