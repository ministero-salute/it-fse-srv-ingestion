package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

/** 
 * Enum for the priority type when inserting a new document. Determines 
 * if the document will go into the high, medium or low priority topic 
 *
 */
public enum PriorityTypeEnum {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    @Getter
    private final String description;

    PriorityTypeEnum(String description) {
        this.description = description;
    }

}
