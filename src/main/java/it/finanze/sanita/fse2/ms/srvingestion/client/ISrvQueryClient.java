package it.finanze.sanita.fse2.ms.srvingestion.client;

import java.io.Serializable;


public interface ISrvQueryClient extends Serializable {

    Boolean checkExists(String docId);

}
