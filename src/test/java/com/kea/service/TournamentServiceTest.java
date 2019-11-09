package com.kea.service;

import com.kea.model.Member;
import com.kea.model.MemberRole;
import com.kea.model.Tournament;
import com.kea.repository.MemberRepository;
import com.kea.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TournamentServiceTest {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @BeforeEach
    public void cleanDB() {
        tournamentRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @Test
    public void testTournamentCRUD() {
        //create test
        Tournament tournament = new Tournament("CPH GRAND PRIX", "02031999", "KBH Hovedbanegård");
        tournamentService.createTournament(tournament);
        //read test
        List<Tournament> tournaments = tournamentService.listTournaments();
        assertEquals(1, tournaments.size());
        assertEquals("CPH GRAND PRIX", tournaments.get(0).getName());
        assertEquals("02031999", tournaments.get(0).getDate());
        assertEquals("KBH Hovedbanegård", tournaments.get(0).getLocation());
        //update test
        tournamentService.updateTournament(tournaments.get(0).getId(), new Tournament("ÅRHUS GRAND PRIX", "04041988", "Århus"));
        tournaments = tournamentService.listTournaments();
        assertEquals(1, tournaments.size());
        assertEquals("ÅRHUS GRAND PRIX", tournaments.get(0).getName());
        assertEquals("04041988", tournaments.get(0).getDate());
        assertEquals("Århus", tournaments.get(0).getLocation());
        //delete test
        tournamentService.deleteTournament(tournaments.get(0).getId());
        tournaments = tournamentService.listTournaments();
        assertEquals(0, tournaments.size());
    }

    @Test
    public void testTournamentEnrollmentAndFind() {
        Tournament tournament = new Tournament("CPH GRAND PRIX", "02031999", "KBH Hovedbanegård");
        tournamentService.createTournament(tournament);
        Integer tournamentId = tournamentService.listTournaments().get(0).getId();
        //find tournament test
        assertEquals(tournament, tournamentService.findTournament(tournamentId));

        Member member = new Member("Esben", "Johannsen", "1999-03-04", "23232323", 1234, MemberRole.DIRECTOR);
        memberService.createMember(member);
        Integer memberId = memberService.list().get(0).getId();

        assertEquals(0, tournamentService.getEnrolledMembers(tournamentId).size());
        assertEquals(1, tournamentService.getNotEnrolledMembers(tournamentId).size());

        tournamentService.enrollMember(tournamentId, memberId);

        assertEquals(1, tournamentService.getEnrolledMembers(tournamentId).size());
        assertEquals(0, tournamentService.getNotEnrolledMembers(tournamentId).size());

        tournamentService.delistMember(tournamentId, memberId);
        assertEquals(0, tournamentService.getEnrolledMembers(tournamentId).size());
        assertEquals(1, tournamentService.getNotEnrolledMembers(tournamentId).size());
    }

    @Test
    public void deleteEnrolledMember() {
        Tournament tournament = new Tournament("CPH GRAND PRIX", "02031999", "KBH Hovedbanegård");
        tournamentService.createTournament(tournament);
        Integer tournamentId = tournamentService.listTournaments().get(0).getId();
        //find tournament test
        assertEquals(tournament, tournamentService.findTournament(tournamentId));

        Member member = new Member("Esben", "Johannsen", "1999-03-04", "23232323", 1234, MemberRole.DIRECTOR);
        memberService.createMember(member);
        Integer memberId = memberService.list().get(0).getId();

        tournamentService.enrollMember(tournamentId, memberId);
        memberService.deleteMember(memberId);
    }
}
