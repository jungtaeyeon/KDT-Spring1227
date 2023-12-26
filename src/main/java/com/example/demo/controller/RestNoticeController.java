package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  // get방식으로 사용자가 입력한 값을 받을 땐 - @RequestParam을 사용함
  // post방식으로 받을 땐 - @RequestBody사용함 - 리액트
  @GetMapping("jsonNoticeList")
   public String jsonNoticeList(@RequestParam Map<String, Object> pMap, HttpServletRequest req){ 
    // logger.info(pMap.get("gubun").toString()+", "+ pMap.get("keyword").toString());
    // logger.info(req.getParameter("gubun"));
    // logger.info(pMap.get("keyword").toString());
    // -> http://localhost:8000/notice/jsonNoticeList?gubun=n_title&keyword=23
    logger.info(pMap.toString());
    List<Map<String, Object>> list = null;
    // list = noticeLogic.noticeList(); // 전체조회
    list = noticeLogic.noticeList(pMap); // 조건검색
    Gson g = new Gson();
    String temp = g.toJson(list);
    return temp; // forward: 가 붙어있기 때문에 webapp폴더 아래에서 찾는다.
  }

  @PostMapping("noticeInsert2")
  public String noticeInsert(@RequestBody Map<String, Object> pMap){ // 파라미터에 n_no, n_title, n_content, n_writer 이런식으로 모두 선언하기 보다는.. Map
    logger.info("noticeInsert2");
    logger.info(pMap.toString());
    int result = 0;
    result = noticeLogic.noticeInsert(pMap);
    return String.valueOf(result); // -> 성공 여부만 나타내는 result만 리턴. 성공: 1, 실패: 0 
  }
}
