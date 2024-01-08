package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error/*")
public class Exception1Controller {

  @GetMapping("test2")
  public String test2() throws Exception{
      throw new Exception("예외가 발생함");
  }

  @GetMapping("test3")
  public String test3() throws Exception{
      throw new RuntimeException("실행될 때 발생 하였습니다.");
  }
}
