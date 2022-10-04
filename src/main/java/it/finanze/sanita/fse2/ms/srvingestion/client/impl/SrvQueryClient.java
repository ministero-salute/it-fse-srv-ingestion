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

@Slf4j
@Component
public class SrvQueryClient implements ISrvQueryClient {
	
	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = 6601225503534618633L; 

	
	
    @Autowired
    private transient RestTemplate restTemplate; 

    @Autowired
    private SrvQueryCFG srvQueryConfig; 
	
	@Override
	public Boolean checkExists(String docId) {
        log.debug("Calling eds Srv Query ep - START"); 

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json"); 

        
        HttpEntity<?> entity = new HttpEntity<>(docId, headers);

        ResponseEntity<ResourceExistResDTO> response = null;
        String url = srvQueryConfig.getEdsSrvQueryHost() + "/v1/document/"  + docId;
        
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

