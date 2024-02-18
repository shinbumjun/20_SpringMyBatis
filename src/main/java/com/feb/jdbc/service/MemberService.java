package com.feb.jdbc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feb.jdbc.dao.MemberDao;
import com.feb.jdbc.dto.EmailDto;
import com.feb.jdbc.entity.Member;
import com.feb.jdbc.util.EmailUtil;
import com.feb.jdbc.util.Sha512Encoder;

@Service
public class MemberService {
	
	public MemberService() {}
	
	@Autowired
	private MemberDao memberDao;
	
//	public MemberService(MemberDao memberDao) {
//		System.out.println(memberDao);
//		this.memberDao = memberDao;
//	}

	private EmailUtil emailUtil;
	public void setEmailUtil(EmailUtil emailUtil) {
		this.emailUtil = emailUtil;
	}

	public HashMap<String, Object> findMember(String memberId) {
		return memberDao.findMember(memberId);
	}
	
	public Member findMember2(String memberId) {
		return memberDao.findMember2(memberId);
	}
	
	public int join(HashMap<String, String> params) {
		Sha512Encoder encoder = Sha512Encoder.getInstance();
		String passwd = params.get("passwd");
		String encodeTxt = encoder.getSecurePassword(passwd);
		params.put("passwd", encodeTxt); // 암호화한 패쓰워드 추가 
		
		return memberDao.join(params);
	}
	
	public ArrayList<Member> memberList(HashMap<String, Object> params) {
		return memberDao.memberList(params);
	}

	public boolean findPasswd(HashMap<String,String> params) {
		
		System.out.println("memberId : : " + params.get("memberId"));
		System.out.println("member : : " + params.get("email"));
		int result = memberDao.findMember(params.get("memberId"),params.get("email"));
		
		System.out.println("result ="+result);
		if(result ==1) {
			// 랜덤한 문자열 생
			String  uuid = UUID.randomUUID().toString();
			System.out.println("newPw1 :" + uuid);
			
			// 필요없는 문자 제거
			String newPw = uuid.replaceAll("-", "");
			System.out.println("newPw 2: " +newPw);
			
			//암호화 
			String encodePw = Sha512Encoder.getInstance().getSecurePassword(newPw);
			System.out.println("newPw 3:" + encodePw);
			
			int updateResult = memberDao.updatePasswd(newPw,params.get("memberId"), params.get("email"));
			
			EmailDto emailDto = new EmailDto();
			emailDto.setFrom("whdudgms321@naver.com");
			emailDto.setReceiver("whdudgms123@naver.com");
			emailDto.setSubject("임시 비밀번호를 전송해드립니다.");
			// 이메일로 먼저 비번 알려주고 바꿔야 데이터 수정은 가장 마지막에 해야한다 
			emailDto.setText("dkdkdkdk");
			
			emailUtil.sendMail(emailDto);
			try {
				// 이메일 발송 실패 시 예외처리 
				emailUtil.sendMail(emailDto);
			}catch (Exception e){
				e.printStackTrace();
			}
			
			//to-do 임시 비밀번호로 업데이트
		//사용자 테이블에 비밀번호 컬럼 수정하는 메서드 작성.
			// interface > impl > service 
			// update lecture.member set passwd =  ?
			// where memberId = ? and email = ? 
			
			
			return updateResult ==1;
			
		}else {
			//ID/email 맞는 사용자는 무조건 1명이어야 함.
			return false;
		}    

	}

	public int delete(String memberId) {
		System.out.println("memberId2222222222222222222222 :" + memberId);
		return memberDao.delete(memberId);
	}
}

/*
 * result =1
newPw1 :701f487c-9d17-4965-b909-8d3e0e9d19c3
newPw 2: 701f487c9d174965b9098d3e0e9d19c3newPw 3:0bbe4dbd7b5cae3d564a753107f03f64112364dcfeabc2035102841f7a1981dab827b74893ca1a17f866344a3240da7e73361bd773d996bb01cc5c726d4fecb3
 * */
 