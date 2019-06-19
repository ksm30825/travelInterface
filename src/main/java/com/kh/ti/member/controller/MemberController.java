package com.kh.ti.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {

	//로그인화면 보여주기 전용(forward loginForm.jsp)
	@RequestMapping("loginForm.me")
	public String showLoginForm() {
		return "member/loginForm";
	}
	
	//회원정보수정화면 보여주기 전용(forward updateMemberInfo.jsp)
	@RequestMapping("updateMemberForm.me")
	public String showMemberInfo() {
		return "member/updateMemberInfo";
	}
	
	//로그인용메소드
	@RequestMapping("login.me")
	public String loginCheck() {
		return null;
	}
	
	//로그아웃용메소드
	@RequestMapping("logout.me")
	public String logoutMember() {
		return null;
	}
	
	//회원가입용메소드
	@RequestMapping("insert.me")
	public String insertMember() {
		return null;
	}
	
	//비밀번호수정용메소드
	@RequestMapping("updateUserPwd.me")
	public void updateUserPwd() {
		
	}
	
	//계좌정보수정용메소드
	@RequestMapping("updateUserAcc.me")
	public void updateUserAcc() {
		
	}
	
	//회원정보수정용메소드
	@RequestMapping("updateUserInfo.me")
	public String updateUserInfo() {
		return null;
	}
	
	//회원탈퇴용메소드
	@RequestMapping("dropOutUser.me")
	public String dropOutUserInfo() {
		return null;
	}
	
	//회원전체조회용메소드
	@RequestMapping("selectAll.me")
	public String selectAllMemberList() {
		return null;
	}
	
	//회원조건조회용메소드
	@RequestMapping("selectCondition.me")
	public String selectConditionMemberList() {
		return null;
	}
	
	//회원게시글내역화면전환용메소드
	@RequestMapping("selectUserBoard.me")
	public String selectUserBoardList() {
		return null;
	}
	
	//회원신고내역화면전환용메소드
	@RequestMapping("selectUserNotify.me")
	public String selectUserNotifyList() {
		return null;
	}
	
	//회원결제내역화면전환용메소드
	@RequestMapping("selectUserPay.me")
	public String selectUserPayList() {
		return null;
	}
	
	//회원문의내역화면전환용메소드
	@RequestMapping("selectUserInquiry.me")
	public String selectUserInquiryList() {
		return null;
	}
	
} //end class
