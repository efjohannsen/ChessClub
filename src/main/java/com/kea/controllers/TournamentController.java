package com.kea.controllers;

import com.kea.model.Member;
import com.kea.model.Tournament;
import com.kea.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Controller
public class TournamentController {

    private TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("tournaments")
    public String listTournaments(Model model) {
        model.addAttribute("tournaments", tournamentService.listTournaments());
        return "tournaments/list";
    }

    @GetMapping("tournaments/create")
    public String createTournament() {
        return "tournaments/create";
    }

    @PostMapping("tournaments/create")
    public String createTournament(Tournament tournament) {
        tournamentService.createTournament(tournament);
        return "redirect:/tournaments";
    }

    @GetMapping("tournaments/delete/{id}")
    public String deleteTournament(@PathVariable("id") Integer id) {
        tournamentService.deleteTournament(id);
        return "redirect:/tournaments";
    }

    @GetMapping("tournaments/update/{id}")
    public String updateTournament(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("tournament",tournamentService.findTournament(id));
        return "tournaments/update";
    }

    @PostMapping("tournaments/update/{id}")
    public String updateTournament(@PathVariable("id") Integer id, Tournament tournament) {
        tournamentService.updateTournament(id, tournament);
        return "redirect:/tournaments";
    }

    @GetMapping("tournaments/view/{id}")
    public String viewTournament(@PathVariable("id") Integer id, Model model) {
        Tournament tournament = tournamentService.findTournament(id);
        Set<Member> enrolledMembers = tournamentService.getEnrolledMembers(id);
        Set<Member> notEnrolled = tournamentService.getNotEnrolledMembers(id);
        model.addAttribute("tournament",tournament);
        model.addAttribute("enrolled", enrolledMembers);
        model.addAttribute("notEnrolled", notEnrolled);

        return "tournaments/view";
    }

    @GetMapping("tournaments/{tournamentId}/enlist/{memberId}")
    public String enlist(@PathVariable("tournamentId") Integer tournamentId, @PathVariable("memberId") Integer memberId) {
        tournamentService.enrollMember(tournamentId, memberId);
        return "redirect:/tournaments/view/" + tournamentId;
    }

    @GetMapping("tournaments/{tournamentId}/delist/{memberId}")
    public String delist(@PathVariable("tournamentId") Integer tournamentId, @PathVariable("memberId") Integer memberId) {
        tournamentService.delistMember(tournamentId, memberId);
        return "redirect:/tournaments/view/" + tournamentId;
    }
}
