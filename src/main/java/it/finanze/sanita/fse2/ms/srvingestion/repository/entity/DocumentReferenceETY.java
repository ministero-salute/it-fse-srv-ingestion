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


/** 
 * Document Reference Entity 
 *
 */
@Document(collection = "ingestion-staging")
@Data
@NoArgsConstructor
public class DocumentReferenceETY {

	/** 
	 * Mongo ID 
	 */
	@Id
	private String id; 
	
	/** 
	 * The document identifier 
	 */
	@Field("identifier")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private String identifier;      
	
	/** 
	 * The operation to be executed on the document 
	 */
	@Field("operation")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private ProcessorOperationEnum operation;  

	/** 
	 * The stringified JSON of the document 
	 */
	@Field("json_string")
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private String jsonString;
	
	/** 
	 * The insertion date of the document 
	 */
	@Field("insertion_date")
	private Date insertionDate;
	
}
