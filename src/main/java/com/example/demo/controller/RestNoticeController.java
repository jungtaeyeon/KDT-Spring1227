package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.example.demo.logic.NoticeLogic;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;

@RestController // 화면없이 조회 결과를 문자열 포맷으로 처리할 때 사용 -> @ResponseBody대체로 제공됨
@RequestMapping("/notice/*")
public class RestNoticeController {
   Logger logger = LoggerFactory.getLogger(RestNoticeController.class);
  @Autowired // -> 서버가 올라갈 때 자동으로 인스턴스화를 해줌
  NoticeLogic noticeLogic = null;

  // 전체조회 및 조건 검색일 때
  // SELECT * FROM notice WHERE gubun=? AND keyword=?
  @GetMapping("jsonNoticeList")
   public String noticeList(@RequestParam Map<String, Object> pMap, HttpServletRequest req){ 
    logger.info(pMap.get("gubun").toString()+", "+ pMap.get("keyword").toString());
    logger.info(req.getParameter("gubun"));
    logger.info(pMap.get("keyword").toString());
    List<Map<String, Object>> list = null;
    // list = noticeLogic.noticeList(); // 전체조회
    list = noticeLogic.noticeList(pMap); // 조건검색
    Gson g = new Gson();
    String temp = g.toJson(list);
    return temp; // forward: 가 붙어있기 때문에 webapp폴더 아래에서 찾는다.
  }
}
