package com.kh.ti.travel.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.ti.common.CommonUtils;
import com.kh.ti.member.model.vo.Member;
import com.kh.ti.travel.model.service.TravelService;
import com.kh.ti.travel.model.vo.City;
import com.kh.ti.travel.model.vo.Country;
import com.kh.ti.travel.model.vo.SchFile;
import com.kh.ti.travel.model.vo.Tag;
import com.kh.ti.travel.model.vo.Travel;
import com.kh.ti.travel.model.vo.TrvCity;
import com.kh.ti.travel.model.vo.TrvCost;
import com.kh.ti.travel.model.vo.TrvDay;
import com.kh.ti.travel.model.vo.TrvSchedule;
import com.kh.ti.travel.model.vo.TrvTag;

@Controller
public class TravelController {

	@Autowired
	private TravelService ts;

	@RequestMapping("showMyTravel.trv")
	public String showMyTravel(HttpSession session, Model model) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		ArrayList<Travel> trvList = ts.selectTrvList(loginUser.getMemberId());
		ArrayList<Travel> privateTrvList = new ArrayList<Travel>();
		ArrayList<Travel> publicTrvList = new ArrayList<Travel>();
		for (int i = 0; i < trvList.size(); i++) {
			if (trvList.get(i).getCompleteDate() == null) {
				privateTrvList.add(trvList.get(i));
			} else {
				publicTrvList.add(trvList.get(i));
			}
		}
		model.addAttribute("privateTrvList", privateTrvList);
		model.addAttribute("publicTrvList", publicTrvList);
		return "travel/myTravel";
	}

	@RequestMapping("selectCountryList.trv")
	public ModelAndView selectCountryList(ModelAndView mv) {
		ArrayList<Country> countryList = ts.selectCountryList();
		mv.addObject("countryList", countryList);
		mv.setViewName("jsonView");
		return mv;
	}

	@RequestMapping("selectCityList.trv")
	public ModelAndView selectCityList(ModelAndView mv, int countryId) {
		ArrayList<City> cityList = ts.selectCityList(countryId);
		mv.addObject("cityList", cityList);
		mv.setViewName("jsonView");
		return mv;
	}

	// 새일정작성-민지
	@RequestMapping("insertTravel.trv")
	public String insertTravel(Travel trv, String startDate, String endDate, String trvCity, Model model) {

		trv.setStartDate(Date.valueOf(startDate));
		trv.setEndDate(Date.valueOf(endDate));
		String[] strArr = trvCity.split(",");
		int[] cityArr = new int[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			cityArr[i] = Integer.parseInt(strArr[i]);
		}
		trv.setTrvCities(cityArr);

		int trvId = ts.insertTravel(trv);
		if (trvId > 0) {
			return "redirect:/selectTravel.trv?trvId=" + trvId;
		} else {
			model.addAttribute("msg", "새 일정 만들기 실패");
			return "common/errorPage";
		}
	}

	// 일정조회-민지
	@RequestMapping("selectTravel.trv")
	public String showTrvEditor(int trvId, Model model) {
		HashMap trvMap = ts.selectTravel(trvId);

		if (!trvMap.isEmpty()) {
			model.addAttribute("trv", trvMap.get("trv"));
			model.addAttribute("trvCityList", trvMap.get("trvCityList"));
			model.addAttribute("trvDayList", trvMap.get("trvDayList"));
			model.addAttribute("allTagList", trvMap.get("allTagList"));
			model.addAttribute("trvTagList", trvMap.get("trvTagList"));
			return "travel/travelEditor";
		} else {
			model.addAttribute("msg", "일정 정보 불러오기 실패");
			return "common/errorPage";
		}
	}

	// 전체일정정보 수정-민지
	@RequestMapping("updateTravel.trv")
	public String updateTravel(Travel trv, String trvCity, Model model) {
		String[] strArr = trvCity.split(",");
		int[] cityArr = new int[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			cityArr[i] = Integer.parseInt(strArr[i]);
		}
		trv.setTrvCities(cityArr);

		int result = ts.updateTravel(trv);
		if (result > 0) {
			return "redirect:/selectTravel.trv?trvId=" + trv.getTrvId();
		} else {
			model.addAttribute("msg", "여행 정보 수정 실패!");
			return "common/errorPage";
		}
	}

	// 작성완료처리-민지
	@RequestMapping("completeTravel.trv")
	public String completeTravel(int trvId, int memberId, Model model) {
		System.out.println("trvId : " + trvId);
		System.out.println("memberId : " + memberId);
		int result = ts.completeTravel(trvId);

		if (result > 0) {
			model.addAttribute("memberId", memberId);
			model.addAttribute("reserveType", 10);
			model.addAttribute("code", trvId);
			model.addAttribute("rPoint", 300);
			return "redirect:/pointReserve.po";
		} else {
			model.addAttribute("msg", "일정 작성완료처리 실패!");
			return "common/errorPage";
		}
	}

	// 여행일정 삭제-민지
	@RequestMapping("deleteTravel.trv")
	public String deleteTravel(int trvId, Model model) {
		int result = ts.deleteTravel(trvId);
		if (result > 0) {
			return "redirect:/showMyTravel.trv";
		} else {
			model.addAttribute("msg", "일정 삭제 실패");
			return "common/errorPage";
		}
	}

	// 상세일정추가-민지
	@RequestMapping("insertSch.trv")
	public String insertTrvSchedule(TrvSchedule sch, TrvCost cost, int trvId, Model model) {
		System.out.println("SCH : " + sch);
		System.out.println("COST : " + cost);

		cost.setCostContent(sch.getSchTitle());

		int result = ts.insertTrvSchedule(sch, cost);
		if (result > 0) {
			return "redirect:/selectTravel.trv?trvId=" + trvId;
		} else {
			model.addAttribute("msg", "상세일정 등록 실패!");
			return "common/errorPage";
		}

	}

	// 상세일정업데이트-민지
	@RequestMapping("updateSch.trv")
	public String updateTrvSchedule(TrvSchedule sch, TrvCost cost, int trvId, Model model) {
		System.out.println("sch: " + sch);
		System.out.println("cost: " + cost);
		System.out.println("trvId: " + trvId);

		cost.setCostContent(sch.getSchTitle());

		int result = ts.updateTrvSchedule(sch, cost);
		if (result > 0) {
			return "redirect:/selectTravel.trv?trvId=" + trvId;
		} else {
			model.addAttribute("msg", "상세일정 업데이트 실패");
			return "common/errorPage";
		}
	}

	// 상세일정 순서변경-민지
	@RequestMapping("updateSchNumber.trv")
	public ModelAndView updateSchNumber(int dayId, int[] sch, ModelAndView mv) {
		int result = ts.updateSchNumber(dayId, sch);

		ArrayList<TrvSchedule> updList = ts.selectSchList(dayId);
		mv.addObject("updList", updList);
		mv.setViewName("jsonView");
		return mv;
	}

	// 상세일정 날짜 변경-민지
	@RequestMapping("deleteSchNumber.trv")
	public ModelAndView deleteSchNumber(int originDayId, int[] sch, ModelAndView mv) {
		int result = ts.deleteSchNumber(originDayId, sch);
		mv.setViewName("jsonView");
		return mv;
	}

	// 상세일정 날짜 변경-민지
	@RequestMapping("changeSchDay.trv")
	public ModelAndView changeSchDay(TrvSchedule trvSch, int[] sch, ModelAndView mv) {

		System.out.println("sch" + sch);
		int result = ts.updateSchDay(trvSch, sch);
		TrvCost cost = ts.selectSchCost(trvSch.getSchId());
		if (cost != null) {
			cost.setDayId(trvSch.getDayId());
			int result1 = ts.updateTrvCost(cost);
			mv.addObject("costId", cost.getCostId());
		}

		ArrayList<TrvSchedule> updList = ts.selectSchList(trvSch.getDayId());
		mv.addObject("updList", updList);
		mv.setViewName("jsonView");
		return mv;
	}

	// 상세일정삭제-민지
	@RequestMapping("deleteSch.trv")
	public String deleteSchedule(int schId, int trvId, Model model) {

		int result = ts.deleteTrvSchedule(schId);
		if (result > 0) {
			return "redirect:/selectTravel.trv?trvId=" + trvId;
		} else {
			model.addAttribute("msg", "일정 삭제 실패");
			return "common/errorPage";
		}
	}

	// 상세일정조회 - 민지
	@RequestMapping("selectSch.trv")
	public ModelAndView selectSchedule(int schId, ModelAndView mv) {

		return mv;
	}

	// 여행테마추가-민지
	@RequestMapping("insertTrvTag.trv")
	public ModelAndView insertTrvTag(TrvTag trvTag, ModelAndView mv) {
		int result = ts.insertTrvTag(trvTag);
		mv.setViewName("jsonView");
		return mv;
	}

	// 여행테마삭제-민지
	@RequestMapping("deleteTrvTag.trv")
	public ModelAndView deleteTrvTag(TrvTag trvTag, ModelAndView mv) {
		int result = ts.deleteTrvTag(trvTag);
		mv.setViewName("jsonView");
		return mv;
	}

	// dayMemo업데이트 - 민지
	@RequestMapping("updateDayMemo.trv")
	public ModelAndView updateDayMemo(TrvDay trvDay, ModelAndView mv) {

		int result = ts.updateDayMemo(trvDay);

		mv.setViewName("jsonView");
		return mv;
	}

	// 상세글 작성-민지
	@RequestMapping("updateSchContent.trv")
	public ModelAndView updateSchContent(TrvSchedule sch, ModelAndView mv) {
		System.out.println(sch);
		int result = ts.updateSchContent(sch);
		TrvSchedule schedule = ts.selectTrvSchedule(sch.getSchId());
		mv.addObject("sch", schedule);
		mv.setViewName("jsonView");
		return mv;
	}

	// 상세글 조회-민지
	@RequestMapping("selectSchContent.trv")
	public ModelAndView selectSchContent(int schId, ModelAndView mv) {

		TrvSchedule sch = ts.selectTrvSchedule(schId);
		if (sch.getSchContent() != null) {
			mv.addObject("content", sch.getSchContent());
		}
		mv.setViewName("jsonView");
		return mv;
	}

	
	public void saveFile(HttpServletRequest request) {
		


	}
	
	
	// 사진업로드-민지
	@RequestMapping("insertSchFile.trv")
	public ModelAndView insertSchFile(HttpServletRequest request, ModelAndView mv,
			@RequestParam(name = "image", required = false) MultipartFile image, int schId) {

		String root = request.getSession().getServletContext().getRealPath("resources");
		String filePath = root + "\\uploadFiles";
		String originName = image.getOriginalFilename();
		String ext = originName.substring(originName.lastIndexOf("."));
		String changeName = CommonUtils.getRandomString();
		System.out.println("image: " + image.getOriginalFilename());
		System.out.println("schId : " + schId);

		try {
			image.transferTo(new File(filePath + "\\" + changeName + ext));

			SchFile file = new SchFile();
			file.setOriginName(originName);
			file.setChangeName(changeName + ext);
			file.setFilePath(filePath);
			file.setSchId(schId);

			int fileId = ts.insertSchFile(file);

			SchFile schFile = ts.selectSchFile(fileId);
			mv.addObject("changeName", schFile.getChangeName());
			mv.addObject("fileLevel", schFile.getFileLevel());
			mv.addObject("fileId", schFile.getFileId());
			// mv.addObject("schFile", schFile);

		} catch (Exception e) {
			new File(filePath + "\\" + changeName + ext).delete();
			e.printStackTrace();
		}

		mv.setViewName("jsonView");
		return mv;
	}

	// 대표사진변경-민지
	@RequestMapping("changeFileLevel.trv")
	public ModelAndView changeFileLevel(int fileId, ModelAndView mv) {

		System.out.println("fileId : " + fileId);
		int result = ts.updateSchFileId(fileId);
		mv.setViewName("jsonView");
		return mv;
	}

	// 날씨정보업로드-민지
	@RequestMapping("updateDayWeather.trv")
	public ModelAndView updateDayWeather(TrvDay trvDay, ModelAndView mv) {

		System.out.println("trvDay : " + trvDay);
		int result = ts.updateDayWeather(trvDay);
		mv.setViewName("jsonView");
		return mv;
	}

	// 전체예산수정-민지
	@RequestMapping("updateBudget.trv")
	public ModelAndView updateBudget(Travel trv, ModelAndView mv) {
		int result = ts.updateBudget(trv);

		mv.setViewName("jsonView");
		return mv;
	}

	// 가계부 작성-민지
	@RequestMapping("insertCost.trv")
	public ModelAndView insertTrvCost(TrvCost cost, ModelAndView mv) {
		System.out.println(cost);
		int costId = ts.insertTrvCost(cost);
		if (costId > 0) {
			TrvCost trvCost = ts.selectTrvCost(costId);
			mv.addObject("trvCost", trvCost);
		}
		mv.setViewName("jsonView");
		return mv;
	}

	// 가계부수정-민지
	@RequestMapping("updateCost.trv")
	public ModelAndView updateTrvCost(TrvCost cost, ModelAndView mv) {

		// dayId가 0으로 넘온건 일정에 연결된 cost를 수정한 경우
		System.out.println(cost);
		if (cost.getDayId() == 0) {
			TrvCost originCost = ts.selectTrvCost(cost.getCostId());
			cost.setDayId(originCost.getDayId());
		}

		int result = ts.updateTrvCost(cost);
		TrvCost trvCost = ts.selectTrvCost(cost.getCostId());
		mv.addObject("trvCost", trvCost);
		mv.setViewName("jsonView");
		return mv;
	}

	// 가계부삭제-민지
	@RequestMapping("deleteCost.trv")
	public ModelAndView deleteTrvCost(int costId, ModelAndView mv) {
		int result = ts.deleteTrvCost(costId);

		mv.setViewName("jsonView");
		return mv;
	}

	// 동행추가-민지
	@RequestMapping("insertCompany.trv")
	public String insertTrvCompany(Travel trv, Member m) {
		int result = ts.insertTrvCompany(trv, m);
		return "";
	}

	// 여행동행삭제-민지
	@RequestMapping("deleteComp.trv")
	public String deleteTrvComp(Travel trv, int memberId) {
		int result = ts.deleteTrvComp(trv, memberId);
		return "";
	}

	// 인기명소 불러오기-민지
	@RequestMapping("selectSpotList.trv")
	public String selectSpotList(Travel trv) {
		HashMap spotMap = ts.selectSpotList(trv);
		return "";
	}

	// 여행사진추가-민지
	@RequestMapping("/insertFile.trv")
	public String insertSchFile(SchFile schFile) {
		int result = ts.insertSchFile(schFile);
		return "";
	}

	// 사진삭제-민지
	@RequestMapping("deleteFile.trv")
	public String deleteSchFile(SchFile file) {
		int result = ts.deleteSchFile(file);
		return "";
	}

	// 일정엑셀다운-민지
	@RequestMapping("downloadSch.trv")
	public void downloadSch(int trvId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ts.selectDownloadSch(trvId, request, response);
	}
	
	//가계부 엑셀다운-민지
	@RequestMapping("downloadCost.trv")
	public void downloadCost(int trvId, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ts.selectDownloadCost(trvId, request, response);
	}

	// 구매한 일정으로 새일정 작성
	@RequestMapping("overrideTravel.trv")
	public String overrideTravel(int trvId, Model model) {

		HashMap trvMap = ts.selectTravel(trvId);

		return "";
	}

}
