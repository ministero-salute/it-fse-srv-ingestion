package it.finanze.sanita.fse2.ms.srvingestion.dto;


import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MAX_SIZE;
import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MIN_SIZE;

import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.finanze.sanita.fse2.ms.srvingestion.enums.PriorityTypeEnum;
import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentReferenceDTO {


	@JsonProperty("identifier")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private String identifier; 
	
	@JsonProperty("operation")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private ProcessorOperationEnum operation;    

	@JsonProperty("jsonString")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private String jsonString;

	@JsonProperty("priorityType")
	private PriorityTypeEnum priorityTypeEnum;
	
	@JsonProperty("insertionDate")
	private Date insertionDate; 
	
}
