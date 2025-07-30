package it.finanze.sanita.fse2.ms.srvingestion.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.finanze.sanita.fse2.ms.srvingestion.dto.UdpDocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;

@Component
public class DocumentConverter {

    public List<UdpDocumentDTO> toDtoList(List<StagingDocumentETY> documentEtyList) {
        List<UdpDocumentDTO> output = new ArrayList<>();

        for (StagingDocumentETY document : documentEtyList) {
            output.add(new UdpDocumentDTO(document));
        }

        return output;
    }

}
