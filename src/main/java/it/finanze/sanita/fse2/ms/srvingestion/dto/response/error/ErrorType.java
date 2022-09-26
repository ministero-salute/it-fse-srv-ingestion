package it.finanze.sanita.fse2.ms.srvingestion.dto.response.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    VALIDATION("/err/validation","Validation error"),
    RESOURCE("/err/resource", "Resource error"),
    CLIENT("/err/client", "Client error"),
    SERVER("/err/server", "Server error");

    private final String type;
    private final String title;

    public String toInstance(String instance) {
        return UriComponentsBuilder
            .fromUriString(instance)
            .build()
            .toUriString();
    }

    public String toInstance(String instance, String ...members) {
        return UriComponentsBuilder
            .fromUriString(instance)
            .pathSegment(members)
            .build()
            .toUriString();
    }

}
