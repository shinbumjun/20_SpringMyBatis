package com.feb.jdbc.controller;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.feb.jdbc.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;

    @GetMapping("/loginPage.do")
    public String loginForm() {
        return "login"; // 뷰 이름을 반환합니다.
    }

    @PostMapping("/login.do")
    public ModelAndView login(@RequestParam HashMap<String, String> params,
		HttpServletResponse response){
    	ModelAndView mv = new ModelAndView();
    	
    	boolean result = loginService.login(params);
	
    	if(result) {
			mv.addObject("resultMsg", "로그 성공");
			mv.addObject("resultCode", "loginOk");
    	}else {
    		mv.addObject("resultMsg", "로그인실패");
			mv.addObject("resultCode", "loginFail");

    	}
    	return mv;
    }
    
    
    
}
