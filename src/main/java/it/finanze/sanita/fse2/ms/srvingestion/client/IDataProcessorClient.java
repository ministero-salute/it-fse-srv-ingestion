package it.finanze.sanita.fse2.ms.srvingestion.client;

import java.io.Serializable;

import it.finanze.sanita.fse2.ms.srvingestion.dto.DocumentReferenceDTO;

public interface IDataProcessorClient extends Serializable {

    Boolean sendRequestToDataProcessor(final DocumentReferenceDTO reqDTO); 

}
