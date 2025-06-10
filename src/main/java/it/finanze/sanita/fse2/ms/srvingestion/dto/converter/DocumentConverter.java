package it.finanze.sanita.fse2.ms.srvingestion.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;

@Component
public class DocumentConverter {

    public List<DocumentDTO> toDtoList(List<StagingDocumentETY> documentEtyList) {
        List<DocumentDTO> output = new ArrayList<>();

        for (StagingDocumentETY document : documentEtyList) {
            output.add(new DocumentDTO(document));
        }

        return output;
    }

}
