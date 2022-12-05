/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/** 
 * The Srv Query Configuration Class 
 */
@Data
@Component
public class SrvQueryCFG {

	/**
	 *  EDS Srv Query Config
	 */
	@Value("${eds-srvquery.url.host}")
	private String edsSrvQueryHost;
	
}
