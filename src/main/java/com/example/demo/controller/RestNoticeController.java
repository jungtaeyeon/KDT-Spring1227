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

// 마임타입 - text/plain 경우와 또는 application/json인 경우 사용한다.
// Front-End와 Back-End 완전히 분리하는 클래스 설계/아키텍쳐 설계
// @RestController 필요성 공감
// Back-End가 무엇을 제공하는가?? - 데이터셋(DataSet)-데이터집합 -> Json포맷을 포함하고 있다.
// [{키: 벨류}] -> []: Array, List계열이다 -> 한 개의 로우 -> 여러가지의 컬럼이 존재할 수 있따. -> 제네릭
// {} -> VO, Map 계열
//
@RestController // 화면없이 조회 결과를 문자열 포맷으로 처리할 때 사용 -> @ResponseBody대체로 제공됨
@RequestMapping("/notice/*")
public class RestNoticeController {
   Logger logger = LoggerFactory.getLogger(RestNoticeController.class);
  @Autowired // -> 서버가 올라갈 때 자동으로 인스턴스화를 해줌
  NoticeLogic noticeLogic = null;

  // get방식으로 사용자가 입력한 값을 받을 땐 - @RequestParam을 사용함
  // post방식으로 받을 땐 - @RequestBody사용함 - 리액트
  // 컨트롤 계층의 리턴타입에는 String이다.
  // redirect: ./XXXX.jsp, redirect: ./XXXX  -> 또 다른 메소드를 호출하는 것
  // forward: ./XXX.jsp(select일 때)
  // 주의: 메소드 안에서 forward방식으로 다른 메소드 호출하는 것 -> 불가능
  // redirect -> foward는 가능함 그러나 forward -> forward는 불가능 함
  // Model은 사용할 필요가 없다. -> 왜냐면 직접 문자열(text/plain)로 반환하거나 JSON(application/json)로 반환함
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
    // List, Map -> JSON으로 변경하기, JSON -> List, Map, JSON -> Array
    Gson g = new Gson(); // 오픈소스API(해커뉴스, 유튜브. 날씨 API) - JSON
    String temp = g.toJson(list); // 파라미터로 받은 List<Map<>>형태를 JSON형식으로 전환해줌
    return temp; 
  }

  @PostMapping("noticeInsert2")
  public String noticeInsert(@RequestBody Map<String, Object> pMap){ // 파라미터에 n_no, n_title, n_content, n_writer 이런식으로 모두 선언하기 보다는.. Map
    logger.info("noticeInsert2");
    logger.info(pMap.toString());
    int result = 0;
    result = noticeLogic.noticeInsert(pMap);
    return String.valueOf(result); // -> 성공 여부만 나타내는 result만 리턴. 성공: 1, 실패: 0 
  }

  // @Controller를 사용할때 와는 다르게(화면이 아니라) JSON 형식의 데이터셋으로 내보내야 할 때
  // 꼭 React뿐 아니라 다른 솔루션 이더라도 데이터셋을 json으로 사용하고 있다면 모두에 적용이 가능한 공통코드 이다.
  // @GetMapping("jsonNoticeDetail")
  // public String jsonNoticeDetail(@RequestBody Map<String, Object> pMap){ // 파라미터에 n_no, n_title, n_content, n_writer 이런식으로 모두 선언하기 보다는.. Map
  //   logger.info("jsonNoticeDetail");
  //   List<Map<String, Object>> nList = null;
  //   nList = noticeLogic.noticeList(pMap);
  //   Gson g = new Gson();
  //   String temp = g.toJson(nList);
  //   return temp;
  // }
  @GetMapping("jsonNoticeDetail")
   public String jsonNoticeDetail(@RequestParam Map<String, Object> pMap){ 
    logger.info(pMap.toString());
    List<Map<String, Object>> list = null;
    list = noticeLogic.noticeList(pMap); // 조건검색
    Gson g = new Gson();
    String temp = g.toJson(list);
    return temp; 
  }
}
