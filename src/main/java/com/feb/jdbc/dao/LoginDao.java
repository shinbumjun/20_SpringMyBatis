package com.feb.jdbc.dao;


import org.springframework.stereotype.Repository;

import com.feb.jdbc.entity.Member;

@Repository
public interface LoginDao {

	public Member login(String memberId);
}
