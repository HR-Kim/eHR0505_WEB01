package kr.co.ehr.user.web;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.google.gson.Gson;

import kr.co.ehr.cmn.Message;
import kr.co.ehr.cmn.StringUtil;
import kr.co.ehr.code.service.Code;
import kr.co.ehr.code.service.CodeService;
import kr.co.ehr.user.service.Search;
import kr.co.ehr.user.service.User;
import kr.co.ehr.user.service.UserService;
import kr.co.ehr.user.service.UserVO;

@Controller
public class UserController {
	Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="downloadView")
	private View download;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CodeService codeService;
	
	//View
	private final String VIEW_NM = "user/user_mng";
	//http://localhost:8080/ehr/user/do_user_view.do
	
	//ExcelDown
	@RequestMapping(value="user/exceldown.do",method=RequestMethod.GET)
	public ModelAndView excelDown(HttpServletRequest req,ModelAndView mView) {
		Search search=new Search();
		/** 페이지 사이즈 */
		String pageSize = StringUtil.nvl(req.getParameter("pageSize"),"10");
		/** 페이지 번호 */
		String pageNum = StringUtil.nvl(req.getParameter("pageNum"),"1");
		/** 검색조건 */
		String searchDiv = StringUtil.nvl(req.getParameter("searchDiv"));
		/** 검색어 */
		String searchWord = StringUtil.nvl(req.getParameter("searchWord"));
		
		/** 확장자 */
		String ext = StringUtil.nvl(req.getParameter("ext"));		
		
		search.setPageSize(Integer.valueOf(pageSize));
		search.setPageNum(Integer.valueOf(pageNum));
		search.setSearchDiv(searchDiv);
		search.setSearchWord(searchWord);
		search.setExt(ext);
		
		String saveFileNm = this.userService.get_excelDown(search);//저장파일명
		String orgFileNm  = "사용자관리_"+StringUtil.cureDate("yyyyMMdd")+"."+ext;
		
		mView.setView(download);
		
		File  downloadFile=new File(saveFileNm);
		mView.addObject("downloadFile", downloadFile);
		mView.addObject("orgFileNm", orgFileNm);
		
		return mView;
	}
	
	//검색:user/get_retrieve.do
	@RequestMapping(value="user/get_retrieve.do",method=RequestMethod.GET)
	public String getRetrieve(HttpServletRequest req,Search search, Model model) {
		LOG.debug("1=========================");
		LOG.debug("1=param="+search);
		LOG.debug("1=========================");
		
		/** 확장자 */
		String ext = StringUtil.nvl(req.getParameter("ext"));	
		//페이지 사이즈:10
		//페이지 번호:1
		if(search.getPageSize()==0) {
			search.setPageSize(10);
		}

		if(search.getPageNum()==0) {
			search.setPageNum(1);
		}		
		
		search.setSearchDiv(StringUtil.nvl(search.getSearchDiv()));
		search.setSearchWord(StringUtil.nvl(search.getSearchWord()));
		LOG.debug("2=========================");
		LOG.debug("2=param="+search);
		LOG.debug("2=========================");
		model.addAttribute("vo", search);
		model.addAttribute("ext", ext);
		//Code:PAGE_SIZE
		Code code=new Code();
		code.setCodeId("PAGE_SIZE");
		//Code정보조회
		List<Code> codeList = (List<Code>) codeService.get_retrieve(code);
		model.addAttribute("codeList", codeList);
		
		code.setCodeId("USER_SEARCH");
		//Code정보조회
		List<Code> codeSearchList = (List<Code>) codeService.get_retrieve(code);
		model.addAttribute("codeSearchList", codeSearchList);
		
		code.setCodeId("EXCEL_TYPE");
		//엑셀유형Code정보조회
		List<Code> excelList = (List<Code>) codeService.get_retrieve(code);
		model.addAttribute("excelList", excelList);
		
		List<User> list = (List<User>) this.userService.get_retrieve(search);
		model.addAttribute("list", list);
		
		
		//총건수
		int totalCnt = 0;
		if(null !=list && list.size()>0) {
			totalCnt = list.get(0).getTotalCnt();
		}
		model.addAttribute("totalCnt", totalCnt);
		
		return VIEW_NM;
	}
	
	
	
	@RequestMapping(value="user/do_user_view.do",method = RequestMethod.GET)
	public String doUserView() {
		LOG.debug("=========================");
		LOG.debug("=@Controller=doUserView==");
		LOG.debug("=========================");
		return VIEW_NM;
	}
	//수정
	@RequestMapping(value="user/do_update.do",method = RequestMethod.POST
			,produces = "application/json; charset=UTF-8")
	@ResponseBody		
	public String do_update(User user) {
		LOG.debug("1=========================");
		LOG.debug("=@Controller=user=="+user);
		LOG.debug("1=========================");
		
		//validation
		int flag = userService.do_update(user);
		Message message=new Message();
		if(flag>0) {
			message.setMsgId(flag+"");
			message.setMsgMsg(user.getU_id()+"님 수정 되었습니다.");
		}else {
			message.setMsgId(flag+"");
			message.setMsgMsg(user.getU_id()+"님 수정 실패.");			
		}
	
		//JSON
		Gson gson=new Gson();
		String json = gson.toJson(message);
		LOG.debug("2=========================");
		LOG.debug("=@Controller=json=="+json);
		LOG.debug("2=========================");
		return json;		
	}
	
	//등록
	@RequestMapping(value="user/do_insert.do",method = RequestMethod.POST
			,produces = "application/json; charset=UTF-8")
	@ResponseBody	
	public String do_insert(User user) { 
		LOG.debug("1=========================");
		LOG.debug("=@Controller=user=="+user);
		LOG.debug("=@Controller=gethLevel=="+user.gethLevel());
		LOG.debug("1=========================");
		
		//validation
		int flag = userService.do_save(user);
		Message message=new Message();
		if(flag>0) {
			message.setMsgId(flag+"");
			message.setMsgMsg(user.getU_id()+"님 등록 되었습니다.");
		}else {
			message.setMsgId(flag+"");
			message.setMsgMsg(user.getU_id()+"님 등록 실패.");			
		}
	
		//JSON
		Gson gson=new Gson();
		String json = gson.toJson(message);
		LOG.debug("1=========================");
		LOG.debug("=@Controller=json=="+json);
		LOG.debug("1=========================");
		return json;
	}
	
	
	//삭제
	@RequestMapping(value="user/do_delete.do",method = RequestMethod.POST
			,produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String do_delete(User user) {
		LOG.debug("=========================");
		LOG.debug("=@Controller=user=="+user);
		LOG.debug("=========================");
		
		//flag>0성공,실패
		int flag = userService.do_delete(user);
		Message message=new Message();
		
		if(flag>0) {
			message.setMsgId(flag+"");
			message.setMsgMsg("삭제 되었습니다.");
		}else {
			message.setMsgId(flag+"");
			message.setMsgMsg("삭제 실패.");			
		}
		
		//JSON변환
		Gson gson=new Gson();
		String json = gson.toJson(message);
		LOG.debug("=========================");
		LOG.debug("=@Controller삭제 gson=user=="+json);
		LOG.debug("=========================");		
		return json;
	}

	
	//단건조회
	@RequestMapping(value="user/get_select_one.do",method = RequestMethod.POST
			,produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String get_selectOne(User user,Model model) {
		LOG.debug("=========================");
		LOG.debug("=@Controller=user=="+user);
		LOG.debug("=========================");
		if(null == user.getU_id() || "".equals(user.getU_id())) {
			throw new IllegalArgumentException("ID를 입력 하세요.");
		}
		User outVO = (User) userService.get_selectOne(user);
		//BASIC->1
		outVO.setLevel(outVO.gethLevel().intValue());
		
		
		//model.addAttribute("vo", outVO);
		Gson gson=new Gson();
		String json = gson.toJson(outVO);
		LOG.debug("=========================");
		LOG.debug("=@Controller gson=user=="+json);
		LOG.debug("=@Controller outVO=="+outVO.toString());
		LOG.debug("=========================");		
		
		return json;
	}
	
	
	
	
}







