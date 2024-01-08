package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error/*")
public class ExceptionController {

  @GetMapping("test1")
  public String test1() throws Exception{
    try {
      throw new Exception("예외가 발생함");
    } catch (Exception e) {
      return "redirect:/error/errorPage.jsp";
    }
  }
}
