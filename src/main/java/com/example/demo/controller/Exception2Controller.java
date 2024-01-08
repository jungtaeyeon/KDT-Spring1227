package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error/*")
public class Exception2Controller {

  @GetMapping("test4")
  public String test4() throws Exception{
      throw new Exception("Exception2Controller 예외가 발생함");
  }
}
