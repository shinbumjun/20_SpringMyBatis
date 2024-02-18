package com.feb.jdbc.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import com.feb.jdbc.entity.*;
import com.feb.jdbc.dao.LoginDao;


public class LoginDaoImpl extends JdbcDaoSupport implements LoginDao{
	
	
	
	@Override
	public Member login(String memberId) throws EmptyResultDataAccessException{
		String sql = "select * from lecture.member where member_id = ?";
		Member member = null; 
		try {
		member = getJdbcTemplate().queryForObject(sql, new Object[] {memberId}, new MemberRowMapper());
		}catch(EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException(1);
		}
		return member;
	}


}
