/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import it.finanze.sanita.fse2.ms.srvingestion.config.Constants;

/** 
 * Profile Utility Class 
 *
 */
@Component
public class ProfileUtility {
	
    @Autowired
    private Environment environment; 
    
    /**
     * Returns true if we are in test environment 
     * 
     * @return boolean  True if we are running with test profile 
     */
    public boolean isTestProfile() {
    	
        if (environment != null && environment.getActiveProfiles().length > 0) {
            return environment.getActiveProfiles()[0].contains(Constants.Profile.TEST);
        }
        return false;
    }
    
} 
