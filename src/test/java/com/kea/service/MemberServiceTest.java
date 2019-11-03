package com.kea.service;

import com.kea.model.Member;
import com.kea.model.MemberRole;
import com.kea.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void cleanDB() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    public void createMemberTest() {

        Member member = new Member("Esben", "Johannsen", "1999-03-04", "23232323", 1234, MemberRole.DIRECTOR);
        memberService.createMember(member);

        List<Member> list = memberService.list();
        assertEquals(1, list.size());
        Member first = list.get(0);

        assertEquals("Esben", first.getFirstname());
        assertEquals("Johannsen", first.getLastname());
        assertEquals("23232323", first.getPhonenumber());
        assertEquals(1234, first.getRating());
        assertEquals(MemberRole.DIRECTOR, first.getRole());
        assertEquals("1999-03-04", first.getBirthday());
        assertNotNull(first.getId());
    }


    @Test
    public void memberNoFound() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                memberService.findMember(-1);
            }
        });
    }

    @Test
    public void testUpdate() {
        Member member = new Member("Esben", "Johannsen", "1999-03-04", "23232323", 1234, MemberRole.DIRECTOR);
        int memberId = memberService.createMember(member);

        Member found = memberService.findMember(memberId);
        found.setFirstname("Peter");
        memberService.updateMember(found.getId(), found);

        List<Member> list = memberService.list();
        assertEquals(1, list.size());
        Member first = list.get(0);

        assertEquals("Peter", first.getFirstname());
        assertEquals("Johannsen", first.getLastname());
        assertEquals("23232323", first.getPhonenumber());
        assertEquals(1234, first.getRating());
        assertEquals(MemberRole.DIRECTOR, first.getRole());
        assertEquals("1999-03-04", first.getBirthday());
        assertEquals(found.getId(), first.getId());

    }

    @Test
    public void testDelete() {
        Member member = new Member("Esben", "Johannsen", "1999-03-04", "23232323", 1234, MemberRole.DIRECTOR);
        int memberId = memberService.createMember(member);

        List<Member> list = memberService.list();
        assertEquals(1, list.size());

        memberService.deleteMember(memberId);

        list = memberService.list();
        assertEquals(0, list.size());
    }
}
