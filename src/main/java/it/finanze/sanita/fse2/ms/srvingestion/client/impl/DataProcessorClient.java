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

import it.finanze.sanita.fse2.ms.srvingestion.client.IDataProcessorClient;
import it.finanze.sanita.fse2.ms.srvingestion.config.DataProcessorCFG;
import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.dto.response.DocumentResponseDTO;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.BusinessException;
import it.finanze.sanita.fse2.ms.srvingestion.exceptions.ConnectionRefusedException;
import lombok.extern.slf4j.Slf4j;

/**
 * The Data Processor Client 
 */
@Slf4j
@Component
public class DataProcessorClient implements IDataProcessorClient {

	/**
	 * Rest Template
	 */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * The Data Processor Configuration 
     */
    @Autowired
    private DataProcessorCFG dataProcessorCFG; 
    
	@Override
	public Boolean sendRequestToDataProcessor(DocumentDTO reqDTO) {
        log.debug("Calling eds Data Processor ep - START"); 
        log.debug("Operation: " + reqDTO.getOperation().getName()); 
                
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        
        HttpEntity<?> entity = new HttpEntity<>(reqDTO, headers);

        ResponseEntity<DocumentResponseDTO> response = null;
        String url = dataProcessorCFG.getEdsDataProcessorHost() + "/v1/process";
        
        try {
            response = restTemplate.exchange(url,
                    HttpMethod.POST, entity,
                    DocumentResponseDTO.class);
            log.debug("{} status returned from Data Processor", response.getStatusCode());
        } catch(ResourceAccessException cex) {
            log.error("Connect error while call eds ingestion ep :" + cex);
            throw new ConnectionRefusedException(dataProcessorCFG.getEdsDataProcessorHost(),"Connection refused"); 
        } catch(Exception ex) {
            log.error("Generic error while call eds ingestion ep :" + ex);
            throw new BusinessException("Generic error while call eds ingestion ep :" + ex);
        }

        return response.getStatusCode().is2xxSuccessful();
	} 

}
