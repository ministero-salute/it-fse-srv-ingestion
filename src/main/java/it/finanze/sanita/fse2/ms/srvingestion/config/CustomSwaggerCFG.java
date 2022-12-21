/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

/** 
 * Custom configuration for Swagger 
 *
 */
@Configuration
@Getter
public class CustomSwaggerCFG {
	
	/** 
	 * API ID
	 */
    @Value("${docs.info.api-id}")
	private String apiId;

    /** 
     * API Summary 
     */
	@Value("${docs.info.summary}")
	private String apiSummary;

	/** 
	 * Title 
	 */
    @Value("${docs.info.title}")
    private String title;

	/** 
	 * Version 
	 */
    @Value("${info.app.version}")
    private String version;

	/** 
	 * Description 
	 */
    @Value("${docs.info.description}")
    private String description;

	/** 
	 * Terms of Service 
	 */
    @Value("${docs.info.termsOfService}")
    private String termsOfService;

	/** 
	 * Contact Name  
	 */
    @Value("${docs.info.contact.name}")
    private String contactName;

	/** 
	 * Contact URL  
	 */
    @Value("${docs.info.contact.url}")
    private String contactUrl;

	/** 
	 * Port 
	 */
    @Value("${server.port}")
    private Integer port;

	/** 
	 * File Max Length 
	 */
    @Value("${validation.file-max-size}")
    private Integer fileMaxLength;

} 

