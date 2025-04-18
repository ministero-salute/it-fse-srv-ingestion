package it.finanze.sanita.fse2.ms.srvingestion.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentDTO;
import it.finanze.sanita.fse2.ms.srvingestion.repository.entity.StagingDocumentETY;

@Component
public class DocumentConverter {

    public DocumentDTO toDto(StagingDocumentETY entity) {
        DocumentDTO dto = new DocumentDTO();

        if (!ObjectUtils.isEmpty(entity.getIdentifier())) {
            dto.setIdentifier(entity.getIdentifier());
        }
        //if (!ObjectUtils.isEmpty(entity.getOperation())) {
        //    dto.setOperation(entity.getOperation());
        //}
        
        if (!ObjectUtils.isEmpty(entity.getDocument())) {
            dto.setJsonString(entity.getDocument().toJson());
        }
        if (!ObjectUtils.isEmpty(entity.getInsertionDate())) {
            dto.setInsertionDate(entity.getInsertionDate());
        }
        if (!ObjectUtils.isEmpty(entity.getRde())) {
            dto.setRde(dto.getRde());
        }

        return dto;
    }

    public StagingDocumentETY toEntity(DocumentDTO dto) {
        StagingDocumentETY entity = new StagingDocumentETY();

        if (!ObjectUtils.isEmpty(dto.getIdentifier())) {
            entity.setIdentifier(dto.getIdentifier());
        }
        //if (!ObjectUtils.isEmpty(dto.getOperation())) {
        //    entity.setOperation(dto.getOperation());
        //}
        if (!ObjectUtils.isEmpty(dto.getJsonString())) {
            entity.setDocument(Document.parse(dto.getJsonString()));
        }
        if (!ObjectUtils.isEmpty(dto.getInsertionDate())) {
            entity.setInsertionDate(dto.getInsertionDate());
        }
        if (!ObjectUtils.isEmpty(dto.getRde())) {
            entity.setRde(dto.getRde());
        }

        return entity;
    }

	
	public List<DocumentDTO> toDtoList(List<StagingDocumentETY> documentEtyList) {
		List<DocumentDTO> output = new ArrayList<>();
		
		for(StagingDocumentETY document : documentEtyList) {
			output.add(this.toDto(document));
		}
	
		return output;
	}


}
    

