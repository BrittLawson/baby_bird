package org.brittlawson.dao;

import org.brittlawson.model.Canopy;

import java.util.List;

public interface CanopyDao {

    List<Canopy> getAllCanopiesJumped ();
    List<Canopy> getCanopiesBySizeRange (int minSize, int maxSize);
    Canopy getMostRecentCanopyJumped ();
    void updateCanopy (Canopy canopy);

}
