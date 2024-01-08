package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
 * 첨부파일, 이미지 파일, 다운로드 처리 추가 예정
 * 응답이 페이지가 아닐 때 선택한다.
 * 응답이 어떤 mime타입일 때 인가??
 * 1) text/plain - 단순한 문자열
 * 2) application/json - JSON포맷
 * @RsetController에 선언하는 메소드의 리턴타입은 String이다.
 * !!! 주의사항 - 절대로 redirect나 forward를 붙이지 말 것 !!!
 * 세션을 주입받을 수 있다. - 상태관리(쿠키-로컬, 세션-서버-cash메모리-그러니까 로컬에서 볼 수 없다.)
 */
@RestController
@RequestMapping("/member/*")
public class RestMemberController {
  Logger logger = LoggerFactory.getLogger(RestMemberController.class);
  
  @GetMapping("imageGet")
  public String imageGet(){
    logger.info("imageGet 호출");
    return "imageGet";
  }
  @GetMapping("imageUpload")
  public String imageUpload(){
    logger.info("imageUpload 호출");
    return "imageUpload";
  }
  @GetMapping("imageDownload")
  public String imageDownload(){
    logger.info("imageDownload 호출");
    return "imageDownLoad";
  }
  @GetMapping("fileUpload")
  public String fileUpload(){
    logger.info("fileUpload 호출");
    return "fileUpload";
  }
}
