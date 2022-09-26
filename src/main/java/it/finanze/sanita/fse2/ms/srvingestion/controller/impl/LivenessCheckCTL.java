package it.finanze.sanita.fse2.ms.srvingestion.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.finanze.sanita.fse2.ms.srvingestion.service.IKafkaSRV;

@RestController
@Tag(name = "Health check Status Actuator")
public class LivenessCheckCTL implements HealthIndicator {

	@Autowired 
	public IKafkaSRV kafkaService; 
	
    /**
     * Return system state.
     *
     * @return system state
     */
    @Override
    @GetMapping("/status")
    @Operation(
        summary = "Health check status",
        description = "Health check endpoint."
    )
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Health check OK", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)) })
	public Health health() {
    	
        return Health.up().build();
    }

}