package com.kea.service;

import com.kea.model.Member;
import com.kea.model.Tournament;
import com.kea.repository.MemberRepository;
import com.kea.repository.TournamentRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TournamentService {

    private final MemberRepository memberRepository;
    private final TournamentRepository tournamentRepository;

    public TournamentService(MemberRepository memberRepository, TournamentRepository tournamentRepository) {
        this.memberRepository = memberRepository;
        this.tournamentRepository = tournamentRepository;
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

    public Tournament findTournament(Integer tournamentId) {
        Optional<Tournament> optional = tournamentRepository.findById(tournamentId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new IllegalArgumentException("No tournament with tournamentId: " + tournamentId);
        }
    }

    @Transactional
    public Set<Member> getEnrolledMembers(Integer tournamentId) {
        Optional<Tournament> optional = tournamentRepository.findById(tournamentId);
        if (optional.isPresent()) {
            Set<Member> members = optional.get().getMembers();
            Hibernate.initialize(members);
            return members;
        } else {
            throw new IllegalArgumentException("No tournament with tournamentId: " + tournamentId);
        }
    }

    @Transactional
    public Set<Member> getNotEnrolledMembers(Integer tournamentId) {
        Set<Member> notEnrolled = new HashSet<>(memberRepository.findAll());
        Set<Member> enrolled = getEnrolledMembers(tournamentId);
        notEnrolled.removeAll(enrolled);
        return notEnrolled;
    }

    @Transactional
    public void enrollMember(Integer tournamentId, Integer memberId) {
        Tournament tournament = tournamentRepository.getOne(tournamentId);
        Member member = memberRepository.getOne(memberId);
        tournament.getMembers().add(member);
        tournamentRepository.save(tournament);
    }

    @Transactional
    public void delistMember(Integer tournamentId, Integer memberId) {
        Tournament tournament = tournamentRepository.getOne(tournamentId);
        Member member = memberRepository.getOne(memberId);
        tournament.getMembers().remove(member);
        tournamentRepository.save(tournament);
    }
}
