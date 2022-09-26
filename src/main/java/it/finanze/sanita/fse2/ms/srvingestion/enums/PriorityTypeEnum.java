package it.finanze.sanita.fse2.ms.srvingestion.enums;

import lombok.Getter;

public enum PriorityTypeEnum {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    @Getter
    private final String description;

    PriorityTypeEnum(String description) {
        this.description = description;
    }

}
