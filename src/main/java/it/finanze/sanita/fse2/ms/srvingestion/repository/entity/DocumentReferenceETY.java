package it.finanze.sanita.fse2.ms.srvingestion.repository.entity;

import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MAX_SIZE;
import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MIN_SIZE;

import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import it.finanze.sanita.fse2.ms.srvingestion.enums.ProcessorOperationEnum;
import lombok.Data;
import lombok.NoArgsConstructor; 


@Document(collection = "ingestion-staging")
@Data
@NoArgsConstructor
public class DocumentReferenceETY {

	@Id
	private String id; 
	
	@Field("identifier")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private String identifier;      
	
	@Field("operation")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private ProcessorOperationEnum operation;  

	@Field("json_string")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private String jsonString;
	
	@Field("insertion_date")
	private Date insertionDate;
	
}
