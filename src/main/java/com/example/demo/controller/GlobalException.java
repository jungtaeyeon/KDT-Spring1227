package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // 모든 패키지에 적용됨
// @ControllerAdvice("com.example.demo") // -> com.example.demo 패키지에 있는 것 만 적용 한다.
public class GlobalException {
  @ExceptionHandler(Exception.class)
  public String typeA(Exception ex, Model model){
    model.addAttribute("ex", ex);
    return "error/errorPage2";
  }
  
  @ExceptionHandler({RuntimeException.class, NullPointerException.class})
  public String typeB(Exception ex, Model model){
     model.addAttribute("ex", ex);
    return "error/errorPage2";
  }
}
