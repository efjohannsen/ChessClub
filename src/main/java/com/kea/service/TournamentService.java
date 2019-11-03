package com.kea.service;

import com.kea.model.Member;
import com.kea.model.Tournament;
import com.kea.model.TournamentEnrollment;
import com.kea.repository.MemberRepository;
import com.kea.repository.TournamentEnrollmentRepository;
import com.kea.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TournamentService {

    private final MemberRepository memberRepository;
    private final TournamentRepository tournamentRepository;
    private final TournamentEnrollmentRepository tournamentEnrollmentRepository;

    public TournamentService(MemberRepository memberRepository, TournamentRepository tournamentRepository, TournamentEnrollmentRepository tournamentEnrollmentRepository) {
        this.memberRepository = memberRepository;
        this.tournamentRepository = tournamentRepository;
        this.tournamentEnrollmentRepository = tournamentEnrollmentRepository;
    }

    public void createTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    public List<Tournament> listTournaments() {
        return tournamentRepository.findAll();
    }

    public void deleteTournament(Integer id) {
        Optional<Tournament> optional = tournamentRepository.findById(id);
        if (optional.isPresent()) {
            tournamentRepository.delete(optional.get());
        }
    }

    public void updateTournament(Integer id, Tournament updated) {
        Optional<Tournament> optional = tournamentRepository.findById(id);
        if (optional.isPresent()) {
            Tournament existing = optional.get();
            existing.setName(updated.getName());
            existing.setDate(updated.getDate());
            existing.setLocation((updated.getLocation()));
            tournamentRepository.save(existing);
        }
    }

    public Tournament findTournament(Integer id) {
        Optional<Tournament> optional = tournamentRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new IllegalArgumentException("No tournament with id: " + id);
        }
    }

    public List<Member> getEnrolledMembers(Integer id) {
        List<Member> members = tournamentEnrollmentRepository.enroledInTounament(id);
        return members;
    }

    public List<Member> getNotEnrolledMembers(Integer id) {
        List<Member> all = memberRepository.findAll();
        List<Member> enrolled = tournamentEnrollmentRepository.enroledInTounament(id);

        all.removeAll(enrolled);
        return all;
    }

    public void enrollMember(Integer tournamentId, Integer memberId) {
        Tournament tournament = tournamentRepository.getOne(tournamentId);
        Member member = memberRepository.getOne(memberId);
        TournamentEnrollment tournamentEnrollment = new TournamentEnrollment(member, tournament);
        tournamentEnrollmentRepository.save(tournamentEnrollment);
    }

    public void delistMember(Integer tournamentId, Integer memberId) {
        TournamentEnrollment tournamentEnrollment = tournamentEnrollmentRepository.findByTournamentIdAndMemberId(tournamentId, memberId);
        tournamentEnrollmentRepository.delete(tournamentEnrollment);
    }
}
