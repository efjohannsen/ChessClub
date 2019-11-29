package com.kea.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@NotBlank
    @Column(nullable = false)
    private String firstname;
    //@NotBlank
    @Column(nullable = false)
    private String lastname;
    //@NotBlank
    @Column(nullable = false)
    private String birthday;
    //@NotBlank
    @Column(nullable = false)
    private String phonenumber;
    //@NotBlank
    @Column(nullable = false)
    private int rating;
    //@NotBlank
    @Column(nullable = false)
    private MemberRole role;
    @ManyToMany(mappedBy = "members")
    private Set<Tournament> tournaments;

    protected Member() {
    }

    public Member(String firstname, String lastname, String birthday, String phonenumber, int rating, MemberRole role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.phonenumber = phonenumber;
        this.rating = rating;
        this.role = role;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<Tournament> tournament) {
        this.tournaments = tournament;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public MemberRole getRole() {
        return role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
