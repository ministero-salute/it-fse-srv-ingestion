package it.finanze.sanita.fse2.ms.srvingestion.dto.response;

import javax.validation.constraints.Size;

import it.finanze.sanita.fse2.ms.srvingestion.dto.AbstractDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author CPIERASC
 *
 *         Base response.
 */
@Getter
@Setter
public class ResponseDTO implements AbstractDTO {



	/**
	 * Serial version UID 
	 */
	private static final long serialVersionUID = 5166650508311130280L; 
	

	/**
	 * Trace id log.
	 */
	@Size(min = 0, max = 100)
	private String traceID;

	/**
	 * Span id log.
	 */
	@Size(min = 0, max = 100)
	private String spanID;

	/**
	 * Instantiates a new response DTO.
	 */
	public ResponseDTO() {
	}

	/**
	 * Instantiates a new response DTO.
	 *
	 * @param traceInfo the trace info
	 */
	public ResponseDTO(final LogTraceInfoDTO traceInfo) {
		traceID = traceInfo.getTraceID();
		spanID = traceInfo.getSpanID();
	} 

}