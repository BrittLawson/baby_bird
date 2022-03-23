package org.brittlawson.dao;

import org.brittlawson.exception.DropzoneNotFoundException;
import org.brittlawson.model.Dropzone;

import java.util.List;

public interface DropzoneDao {

    Dropzone getDropzoneByDzId (int dzId);
    List<Dropzone> getAllDropzonesJumpedAt ();
    List<Dropzone> getDropzonesByState (String stateAbbreviation);
    Dropzone getMostFrequentDropzone ();
    Dropzone getMostRecentDropzone ();
    int getNumberOfJumpsAtGivenDz (int dzId) throws DropzoneNotFoundException;
    void updateDropzone (Dropzone dropzone);

}
