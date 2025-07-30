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
package it.finanze.sanita.fse2.ms.srvingestion.dto;

import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MAX_SIZE;
import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MIN_SIZE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Document DTO class
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UdpDocumentDTO {

    public UdpDocumentDTO(StagingDocumentETY entity) {
        this.identifier = entity.getIdentifier();
        this.operation = entity.getOperation();
        this.jsonString = entity.getDocument().toJson();
        this.rde = entity.getRde();
        this.insertionDate = entity.getInsertionDate();
    }

    /**
     * The doc identifier
     */
    @JsonProperty("identifier")
    @Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
    private String identifier;

    /**
     * The operation to be executed (CREATE, UPDATE, REPLACE or DELETE)
     */
    @JsonProperty("operation")
    @Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
    private ProcessorOperationEnum operation;

    /**
     * The JSON string of the document
     */
    @JsonProperty("jsonString")
    @Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
    private String jsonString;

    /**
     * The Insertion Date
     */
    @JsonProperty("insertionDate")
    private Date insertionDate;

    /**
     * The Insertion Date
     */
    @JsonProperty("rde")
    private String rde;

    public static List<UdpDocumentDTO> buildListFromEty(List<StagingDocumentETY> documentEtyList) {
        List<UdpDocumentDTO> output = new ArrayList<>();

        for (StagingDocumentETY document : documentEtyList) {
            output.add(new UdpDocumentDTO(document));
        }

        return output;
    }
}
