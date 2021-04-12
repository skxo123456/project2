package member.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import member.model.MailVO;
import member.model.MemberDao;

@Controller
public class myPageController {

	@Autowired
	MemberDao memberDao;
	
	
	@RequestMapping(value="/myPage.mb",method = RequestMethod.GET)
	public String myPage() {
		return "mypage";
	}
	
	@RequestMapping(value="/change.mb",method = RequestMethod.GET)
	public String changeType(HttpSession session) {
		MailVO mv = (MailVO)session.getAttribute("loginInfo");
		int type = mv.getType();
		String email = mv.getEmail();
		System.out.println(mv.getType());
		if(type == 0) {
			mv.setType(1);
			memberDao.updateType(mv);
		}else {
			mv.setType(0);
			memberDao.updateType(mv);
		}
		
		return "mypage";
	}
	
	@RequestMapping(value="/myService.mb",method = RequestMethod.GET)
	public ModelAndView myService(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		MailVO mv = (MailVO)session.getAttribute("loginInfo");
		String email = mv.getEmail();
		List<MailVO> lists = memberDao.getList(email);
		mav.addObject("lists", lists);
		mav.setViewName("myService");
		
		return mav;
	}
}
