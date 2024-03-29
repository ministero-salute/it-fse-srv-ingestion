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
package it.finanze.sanita.fse2.ms.srvingestion.enums;

import it.finanze.sanita.fse2.ms.srvingestion.enums.UIDModeEnum;
import lombok.Getter;

/** 
 * The Enum containing the different UUID generation modes 
 *
 */
public enum UIDModeEnum {
    
    IP_UUID(1, "IP_UUID"),
    HOSTNAME_UUID(2, "HOSTNAME_UUID"),
    UUID_UUID(3, "UUID_UUID");

	/** 
	 * The ID of the Enum 
	 */
    @Getter
    private Integer id;

    /** 
     * The Description of the Enum 
     */
    @Getter
    private String description;

    private UIDModeEnum(Integer inId, String inDescription) {
        id = inId;
        description = inDescription;
    }

    public static UIDModeEnum get(Integer inId) {
        UIDModeEnum out = null;
        for (UIDModeEnum v : UIDModeEnum.values()) {
            if (v.getId().equals(inId)) {
                out = v;
                break;
            }
        }
        return out;
    }
}