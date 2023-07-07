package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// MemberController는 MemberService를 통해서 회원가입 및 조회가 가능해야 함!
// -> MemberController가 MemberService를 의존함
@Controller
public class MemberController {

    //    private final MemberService memberService = new MemberService();
    //    MemberService를 하나만 사용하기! (new 해서 사용 XX, MemberService를 주입 받는 기능은 동일하지만,
    //    의존성 주입 문제로 인해서 아래처럼 하는게 좋음!)
    //    이유: 객체 재사용성을 높이기 위해
    //    그리고 스프링의 경우 프레임워크에서 지원하는 기능(의존관계 자동주입 등)을 제공받기 위해
    //    빈(싱글톤 스코프)으로 등록하여 사용함
//    # 생성자 주입 방식
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {

        this.memberService = memberService;

        // aop 적용 후 memberController가 memberService를 직접 호출하는지, proxy를 호출하는지
        // 확인하기 위한 테스트 코드!
        System.out.println("memberService = " + memberService.getClass());
    }   // MemberController

//    # 필드 주입 방식
//    @Autowired private MemberService memberService;

//    # setter 주입 방식 -> 누군가 MemberController 호출했을 때, public으로 열려있어야 함
//    private MemberService memberService;
//
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    } // setMemberService()

//    ------------------------------------------

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    } // createForm()

    @PostMapping ("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        System.out.println("member = " + member.getName());

        memberService.join(member);

        // 회원 가입 끝났으니까 홈 화면으로 보내기
        return "redirect:/";
    } //  create()

    @GetMapping("/members")
    public String list(Model model) {
        // 회원 조회
        List<Member> members = memberService.findMembers();

        // 모델에 회원 리스트를 담아서 view로 넘기기
        model.addAttribute("members", members);

        return "members/memberList";
    } // list()


} // end class

