/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */
package it.finanze.sanita.fse2.ms.srvingestion.enums;

/** 
 * Enum for the priority type when inserting a new document. Determines 
 * if the document will go into the high, medium or low priority topic 
 *
 */
public enum PriorityTypeEnum {
	LOW("LOW", "_LOW"),
	MEDIUM("MEDIUM", "_MEDIUM"),
	HIGH("HIGH", "_HIGH");

	private final String description;

	private final String queue;

	PriorityTypeEnum(String inDescription, String inQueue) {
		description = inDescription;
		queue = inQueue;
	}

	public String getDescription() {
		return description;
	}

	public String getQueue() {
		return queue;
	}

}
