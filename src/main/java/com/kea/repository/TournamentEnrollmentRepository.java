package com.kea.repository;

import com.kea.model.Member;
import com.kea.model.TournamentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentEnrollmentRepository extends JpaRepository<TournamentEnrollment, Integer> {

    @Query("select te.member from TournamentEnrollment te where te.tournament.id = :tournamentId")
    List<Member> enroledInTounament(@Param("tournamentId") Integer tournamentId);

    @Query("select te from TournamentEnrollment te where te.tournament.id = :tournamentId and te.member.id = :memberId")
    TournamentEnrollment findByTournamentIdAndMemberId(@Param("tournamentId") Integer tournamentId, @Param("memberId") Integer memberId);

}
