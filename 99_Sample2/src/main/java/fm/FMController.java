package fm;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import member.model.MailVO;
import utility.Paging;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FMController {
	
	@Autowired
	private CatDao catDao;
	
	@Autowired
	ServletContext application;
	
	private static final Logger logger = LoggerFactory.getLogger(FMController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/blog.fm", method = RequestMethod.GET)
	public String blog() {
		return "blog";
	}
	@RequestMapping(value = "/blog_detail.fm", method = RequestMethod.GET)
	public String blog_detail() {
		return "blog-details";
	}
	@RequestMapping(value = "/checkout.fm", method = RequestMethod.GET)
	public String checkout() {
		return "checkout";
	}
	@RequestMapping(value = "/contact.fm", method = RequestMethod.GET)
	public String contact() {
		return "contact";
	}
	@RequestMapping(value = "/index.fm", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	@RequestMapping(value = "/main.fm", method = RequestMethod.GET)
	public String main3() {
		return "main";
	}
	@RequestMapping(value = "/shop_detail.fm", method = RequestMethod.GET)
	public String shop_detail() {
		return "shop-details";
	}
	@RequestMapping(value = "/shop_grid.fm", method = RequestMethod.GET)
	public String shop_grid() {
		return "shop-grid";
	}
	@RequestMapping(value = "/shopping_cart.fm", method = RequestMethod.GET)
	public String shopping_cart() {
		return "shopping-cart";
	}
	@RequestMapping(value = "/category.fm", method = RequestMethod.GET)
	public String category() {
		return "CATEGORY";
	}
	
	// 삽입proc
	@RequestMapping(value="/insert.fm", method=RequestMethod.POST)
	public ModelAndView doActionPost(CatBean cat,HttpSession session) throws IllegalStateException, IOException {
		cat.setStandard("1"); if(cat.getSmain().equals("")) {cat.setStandard("0");}
		cat.setDeluxe("1"); if(cat.getDmain().equals("")) {cat.setDeluxe("0");}
		cat.setPremium("1"); if(cat.getPmain().equals("")) {cat.setPremium("0");}
		if(cat.getSorigin()==null) {cat.setSorigin("0");} else {cat.setSorigin("1");}
		if(cat.getSdesign()==null) {cat.setSdesign("0");} else {cat.setSdesign("1");}
		if(cat.getSpossible()==null) {cat.setSpossible("0");} else {cat.setSpossible("1");}
		if(cat.getSregist()==null) {cat.setSregist("0");} else {cat.setSregist("1");}
		if(cat.getDorigin()==null) {cat.setDorigin("0");} else {cat.setDorigin("1");}
		if(cat.getDdesign()==null) {cat.setDdesign("0");} else {cat.setDdesign("1");}
		if(cat.getDpossible()==null) {cat.setDpossible("0");} else {cat.setDpossible("1");}
		if(cat.getDregist()==null) {cat.setDregist("0");} else {cat.setDregist("1");}
		if(cat.getPorigin()==null) {cat.setPorigin("0");} else {cat.setPorigin("1");}
		if(cat.getPdesign()==null) {cat.setPdesign("0");} else {cat.setPdesign("1");}
		if(cat.getPpossible()==null) {cat.setPpossible("0");} else {cat.setPpossible("1");}
		if(cat.getPregist()==null) {cat.setPregist("0");} else {cat.setPregist("1");}
		Timestamp regdate = new Timestamp(System.currentTimeMillis());
		cat.setInputdate(regdate);
		MailVO vo = (MailVO)session.getAttribute("loginInfo");
		cat.setEmail(vo.getEmail());
		cat.setNickname(vo.getNickname());
		String uploadPath = application.getRealPath("/resources");
		ModelAndView mav = new ModelAndView();
		int cnt = catDao.insertCat(cat);
		String[] imgname = {cat.getMimgname(),cat.getSimgname(),cat.getVideoname()};
		MultipartFile[] multi = {cat.getMupload(),cat.getSupload(),cat.getVupload()};
		if(cnt == 1) {
			for(int i=0; i<=imgname.length-1; i++) {
				try{
					File f = new File(uploadPath + "\\" + imgname[i]);
					multi[i].transferTo(f);
				}catch(IOException a){
					a.printStackTrace();
				}
			}
			mav.setViewName("redirect:/myService.mb");
		} else {
			mav.setViewName("/category.fm");
		}
		return mav;
	}
	
	//detail.fm
		// 전체조회 ,, 1차 카테고리만 조회
		@RequestMapping(value = "/result.fm")
		public ModelAndView powermain(@RequestParam(value="whatColumn",required=false) String whatColumn,
									  @RequestParam(value="keyword",required=false) String keyword,
									  @RequestParam(value="pageNumber",required=false) String pageNumber,
									  @RequestParam(value="pageSize",required=false) String pageSize,
									  HttpServletRequest request, HttpSession session) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("whatColumn",whatColumn);
			map.put("keyword","%"+keyword+"%");
			int totalCount = catDao.getTotalCount(map);
			String url = request.getContextPath() + "/main.fm";
			Paging pageInfo = new Paging(pageNumber,pageSize,totalCount,url,whatColumn,keyword);
			ModelAndView mav = new ModelAndView();
			List<CatBean> list1 = catDao.getCatList(pageInfo,map);
			List<RefBean> list2 = catDao.selectRef();
			
			mav.addObject("catList",list1); //전체
			session.setAttribute("list", list1); //전체
			
			
			mav.addObject("refList",list2); //카테고리 만
			session.setAttribute("refList",list2); //카테고리 만
			
			mav.addObject("pageInfo",pageInfo);
			mav.addObject("totalCount",totalCount);
			mav.setViewName("result"); //result.jsp
			return mav;
		}
	
	// 장바구니
	@RequestMapping(value = "/basket.od", method = RequestMethod.GET)
	public String generic(Model model) {
		model.addAttribute("aa",model);
		return "generic";
	}
	
	// 결제창
	@RequestMapping(value = "/payment.od", method = RequestMethod.GET)
	public String elements(Model model) {
		model.addAttribute("aa",model);
		return "elements";
	}
	//상세보기
		@RequestMapping(value = "/detail.fm")
		public ModelAndView detail(HttpServletRequest request, HttpSession session,
									@RequestParam(value = "num",required = true) int num
									  ) {
			CatBean detailList = catDao.detailCat(num);
			
			ModelAndView mav = new ModelAndView();
			
			mav.addObject("detailList",detailList);
			mav.setViewName("shop-details"); //shop-details.jsp
			return mav;
		}
}
