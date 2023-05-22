package hello.hellospring.controller;

import hello.hellospring.Domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService.getClass() = " + memberService.getClass());
    }
    //home.html을 확인해보면 members/new라는 url로 이동한다.
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    //이건 PostMapping으로 매핑을 해줘야 한다.
    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        //회원가입할 때 그 join함수다
        memberService.join(member);
        //홈화면으로 redirect하겠다.
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        //멤버의 리스트 자체를 그냥 모델에 담아서 화면에 넘길거다.
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
