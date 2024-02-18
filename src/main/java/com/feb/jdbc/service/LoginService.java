package com.feb.jdbc.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.feb.jdbc.dao.LoginDao;
import com.feb.jdbc.entity.Member;
import com.feb.jdbc.util.Sha512Encoder;

@Service
public class LoginService {

	@Autowired
	LoginDao loginDao;
	
//	public LoginService() {}
//	public LoginService(LoginDao loginDao) {
//		this.loginDao = loginDao;
//	}
	

	public boolean login(HashMap<String,String> params) {
		String memberId = params.get("memberId");
		
		
		Member member  = loginDao.login(memberId);
		
		// 바꼈다 원래는 없을경우 exception이 떴는데 mybatis는 그냥 null을 리턴해준다 
		//서로 mybatis springjdbc랑 보는 객체가 1개일 때에 대한 처리기준이 서로 다르다   
		if(ObjectUtils.isEmpty(member)) {
			return false;
		}
		String memberPw = member.getPasswd(); 
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//		Member member = null;
//		try{
//		 member = loginDao.login(memberId);
//		}catch(EmptyResultDataAccessException e) {
//			return false;
//		}
//		String memberPw = member.getPasswd(); 
		
		
		Sha512Encoder encoder = Sha512Encoder.getInstance();
		String passwd = params.get("passwd");
		// 사용자가 입력한 값을 암호화한 거다 
		String encodeTxt = encoder.getSecurePassword(passwd);			
		
		System.out.println(member);
		return 		StringUtils.pathEquals(memberPw, encodeTxt);

		
	}

	
}
