package com.example.demo.controller;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/authc")
public class AuthController {
  Logger logger = LoggerFactory.getLogger(AuthController.class);
  // 로그인 화면 이지만 스프링을 경유하도록 한다.
  // 왜냐면 뭔가 인터셉트 하여 전처리를 해야 하는 경우라면 단순한 화면을 부르는 과정도 스프링을 태운다
  @GetMapping("loginForm")
  public String loginForm(){
    logger.info("loginForm");

    return "redirect:/auth/loginForm.jsp"; // 배포위치가 webapp이다.
  }

  // jsp페이지를 요청해서 처리해도 결과는 같을 것 이다.
  // 다만 스프링 컨테이너를 굳이 경유하도록 학습해 본다.
  @GetMapping("logout")
  public String logout(HttpSession session){
    logger.info("logout");
    session.invalidate();
    return "redirect:/index.jsp"; // 배포위치가 webapp이다.
  }

  @PostMapping("login")
  public String login(String mem_email, String mem_pw2, boolean remember, HttpServletResponse res)
  throws Exception
  {
    logger.info("login");
    logger.info(mem_email+", "+mem_pw2+", "+remember);
    if (!loginCheck(mem_email, mem_pw2)) {
      String msg = URLEncoder.encode("이메일 또는 비번이 일치하지 않습니다.", "utf-8");
      return "redirect:./loginForm?msg="+msg;
    }

    if(remember) {
      jakarta.servlet.http.Cookie c = new jakarta.servlet.http.Cookie("mem_email", mem_email);
      // 현재 바라보는 곳 - auth => loginForm.jsp가 있는 위치
      // 성공하면 http://localhost:8000/index.jsp
      // 이런 경우 쿠키가 생성되는 도메인과 달라서 반드시 setPath추가할 것
      c.setPath("/");
      c.setMaxAge(60*60);
      res.addCookie(c);      
    } else{
      jakarta.servlet.http.Cookie c = new jakarta.servlet.http.Cookie("mem_email", mem_email);
      c.setPath("/");
      c.setMaxAge(0);
      res.addCookie(c);      
    }
    // 기억하기를 체크하면 이메일을 기억해줄게
    return "redirect:/index.jsp"; // 배포위치가 webapp이다.
  }
  private boolean loginCheck(String mem_email, String mem_pw2) {
    return "kiwi@hot.com".equals(mem_email) && "123".equals(mem_pw2);
  }
}
