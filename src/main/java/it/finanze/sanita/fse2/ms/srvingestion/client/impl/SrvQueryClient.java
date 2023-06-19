/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package it.finanze.sanita.fse2.ms.srvingestion.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import it.finanze.sanita.fse2.ms.srvingestion.client.ISrvQueryClient;
import it.finanze.sanita.fse2.ms.srvingestion.config.SrvQueryCFG;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.ResourceExistResDTO;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.BusinessException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.ConnectionRefusedException;
import lombok.extern.slf4j.Slf4j;

/** 
 * The Srv Query Client 
 *
 */
@Slf4j
@Component
public class SrvQueryClient implements ISrvQueryClient {
	
	/**
	 * The Rest Template 
	 */
    @Autowired
    private RestTemplate restTemplate;

    /** 
     * The Srv Query Configuration 
     */
    @Autowired
    private SrvQueryCFG srvQueryConfig; 
	
    /** 
     * Checks whether the document exists by calling the Srv Query Microservice 
     */
	@Override
	public boolean checkExists(String docId) {
        log.debug("Calling eds Srv Query ep - START"); 

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); 

        
        HttpEntity<?> entity = new HttpEntity<>(docId, headers);

        ResponseEntity<ResourceExistResDTO> response;
        String url = srvQueryConfig.getEdsSrvQueryHost() + "/v1/document/check-exist/"  + docId;
        
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, ResourceExistResDTO.class);
            log.debug("{} status returned from Srv Query", response.getStatusCode());
            final ResourceExistResDTO responseBody = response.getBody();
            return responseBody != null && responseBody.isExist();
        } catch(ResourceAccessException cex) {
            log.error("Connect error while call EDS Srv Query ep :" + cex);
            throw new ConnectionRefusedException(srvQueryConfig.getEdsSrvQueryHost(),"Connection refused by SRV Query Host"); 
        } catch(Exception ex) {
            log.error("Generic error while call EDS Srv Query ep :" + ex);
            throw new BusinessException("Generic error while call EDS Srv Query ep :" + ex);
        }
        
	}

} 

