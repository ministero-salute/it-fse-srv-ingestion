package it.finanze.sanita.fse2.ms.srvingestion.dto.response;

import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MAX_SIZE;
import static it.finanze.sanita.fse2.ms.srvingestion.utility.ValidationUtility.DEFAULT_STRING_MIN_SIZE;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentExistsResponseDTO extends ResponseDTO { 

	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = 21641554325694264L; 
	
	
	@Size(min = DEFAULT_STRING_MIN_SIZE, max = DEFAULT_STRING_MAX_SIZE)
	private String transactionId; 
	
	private Boolean result; 
	

	public DocumentExistsResponseDTO() {
		super();
	}

	public DocumentExistsResponseDTO(final LogTraceInfoDTO traceInfo, Boolean _result) {
		super(traceInfo);
		result = _result; 
	}
	
}