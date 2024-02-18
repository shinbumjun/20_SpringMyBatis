package com.feb.jdbc.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.feb.jdbc.dao.NoticeDao;

@Service
@Repository
public class NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;
	
	public NoticeService() {}
	
	public NoticeService(NoticeDao noticeDao) {
		System.out.println(noticeDao);
		this.noticeDao = noticeDao;
	}
	

	public int insertNotice(HashMap<String, String> params) {
		return noticeDao.insertNotice(params);
	}


}
