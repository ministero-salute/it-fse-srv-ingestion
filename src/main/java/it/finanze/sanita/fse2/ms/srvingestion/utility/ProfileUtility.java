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
    
    /**
     * Returns true if we are in test environment 
     * 
     * @return boolean  True if we are running with test profile 
     */
    public boolean isDevProfile() {
    	
        if (environment != null && environment.getActiveProfiles().length > 0) {
            return environment.getActiveProfiles()[0].contains(Constants.Profile.DEV);
        }
        return false;
    }
    
} 
