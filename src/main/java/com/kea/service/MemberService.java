package com.kea.service;

import com.kea.model.Member;
import com.kea.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public int createMember(Member member ) {
        Member saved = memberRepository.save(member);
        return saved.getId();
    }

    public List<Member> list() {
        return memberRepository.findAll();
    }

    public void deleteMember(int id) {
        Optional<Member> optional = memberRepository.findById(id);
        if (optional.isPresent()) {
            memberRepository.delete(optional.get());
        }
    }

    public Member findMember(int id) {
        Optional<Member> optional = memberRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new IllegalArgumentException("No member with id: " + id);
        }
    }

    public void updateMember(int id, Member updated) {
        Optional<Member> optional = memberRepository.findById(id);
        if (optional.isPresent()) {
            Member existing = optional.get();
            existing.setFirstname(updated.getFirstname());
            existing.setLastname(updated.getLastname());
            existing.setBirthday(updated.getBirthday());
            existing.setPhonenumber(updated.getPhonenumber());
            existing.setRating(updated.getRating());
            existing.setRole(updated.getRole());
            memberRepository.save(existing);
        } else {
            throw new IllegalArgumentException("No member with id: " + id);
        }
    }
}
