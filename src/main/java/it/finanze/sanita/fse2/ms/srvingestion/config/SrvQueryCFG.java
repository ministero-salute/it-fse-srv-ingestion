/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/** 
 * The Srv Query Configuration Class 
 *
 */
@Data
@Component
public class SrvQueryCFG implements Serializable {

	
	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = -6217442923704906883L; 
		

	/**
	 *  EDS Srv Query Config
	 */
	@Value("${eds-srvquery.url.host}")
	private String edsSrvQueryHost;
	
}
