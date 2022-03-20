package org.brittlawson.dao;

import org.brittlawson.model.Dropzone;

import java.util.List;

public interface DropzoneDao {

    List<Dropzone> getAllDropzonesJumpedAt ();
    List<Dropzone> getDropzonesByState (String stateAbbreviation);
    Dropzone getMostFrequentDropzone ();
    Dropzone getMostRecentDropzone ();
    int getNumberOfJumpsAtGivenDz (int dzId);
    void updateDropzone (Dropzone dropzone);
    void deleteDropzone (Dropzone dropzone);

}
