package com.kea.controllers;

import com.kea.model.Member;
import com.kea.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("members")
    public String listMembers(Model model) {
        model.addAttribute("members", memberService.list());
        return "members/list";
    }

    @GetMapping("members/create")
    public String createMember() {
        return "members/create";
    }

    @PostMapping("members/create")
    public String createMember(@ModelAttribute Member member) {
        memberService.createMember(member);
        return "redirect:/members";
    }

    @GetMapping("members/update/{id}")
    public String updateMember(@PathVariable("id") int id, Model model) {
        model.addAttribute("member", memberService.findMember(id));
        return "members/update";
    }

    @PostMapping("members/update/{id}")
    public String updateMember(@PathVariable("id") int id, @ModelAttribute Member member) {
        memberService.updateMember(id, member);
        return "redirect:/members";
    }

    @GetMapping("members/delete/{id}")
    public String deleteMember(@PathVariable("id") int id) {
        memberService.deleteMember(id);
        return "redirect:/members";
    }
}
