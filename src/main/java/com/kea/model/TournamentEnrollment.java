package com.kea.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TournamentEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Tournament tournament;

    protected TournamentEnrollment() {
    }

    public TournamentEnrollment(Member member, Tournament tournament) {
        this.member = member;
        this.tournament = tournament;
    }
}
