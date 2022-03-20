package org.brittlawson.dao;

import org.brittlawson.model.Jump;

import java.time.LocalDate;
import java.util.List;

public interface JumpDao {

    Jump getJump (int jumpNumber);
    List<Jump> getAllJumps ();
    int getTotalNumberJumps ();
    List<Jump> getJumpsByDz (int dzId);
    List<Jump> getJumpsByDateRange (LocalDate startDate, LocalDate endDate);
    List<Jump> getJumpsByLandingAccuracy (int maxDistanceFromTarget);
    List<Jump> getJumpsByExitAltitude (int minAltitude, int maxAltitude);
    List<Jump> getJumpsByFormationSize (int minFormationSize);
    LocalDate getMostRecentJumpDate ();
    Jump createJump (Jump jump);
    void updateJump (Jump jump);
    void deleteJump (int jumpNumber);

}
