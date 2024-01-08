package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.logic.BoardLogic;

@Controller
@RequestMapping("/board/*")
public class BoardController {
  Logger logger = LoggerFactory.getLogger(NoticeController.class);
  @Autowired // -> 서버가 올라갈 때 자동으로 인스턴스화를 해줌
  BoardLogic boardLogic = null;

  @PostMapping("boardInsert")
  public String boardInsert(@RequestParam Map<String,Object> pMap){
    logger.info("boardInsert 호출");
    int result = 0;
    result = boardLogic.boardInsert(pMap);
    return "redirect:boardList";
  }



  // 전체조회 및 조건 검색일 때
  // SELECT * FROM notice WHERE gubun=? AND keyword=?
  @GetMapping("boardList")
  public String boardList(@RequestParam Map<String, Object> pMap, Model model){ 
    List<Map<String, Object>> nList = null;
    nList = boardLogic.boardList(pMap); // 전체조회 및 조건검색
    model.addAttribute("nList", nList);
    return "forward:boardList.jsp"; // forward: 가 붙어있기 때문에 webapp폴더 아래에서 찾는다.
  } 

  // b_hit 증가시킨다. update board230527 set b_hit
  @GetMapping("boardDetail")
  public String boardDetail(@RequestParam Map<String, Object> pMap, Model model){ 
    List<Map<String, Object>> bList = null;
    // 동일한 조회경우인데 왜 메소드를 나누어 할까요? - board230527_comment테이블의 정보도 추가로 가져와야 한다.
    // 컨트롤계층에 대한 경유 없이 처리해야 한다. - MVC패턴
    bList = boardLogic.boardDetail(pMap); // select2번, update1번 해야함
    model.addAttribute("bList", bList);
    logger.info("b_no: "+pMap.get("b_no")); // 2가 담겨야 한다.
    return "forward:boardDetail.jsp"; // forward: 가 붙어있기 때문에 webapp폴더 아래에서 찾는다.
  } 

  @PostMapping("boardUpdate")
  public String boardUpdate(@RequestParam Map<String, Object> pMap){ 
    int result = -1;
    String path = "";
    result = boardLogic.boardUpdate(pMap); // 전체조회 및 조건검색
    if (result == 1) {
      path = "redirect:boardDetail?b_no="+pMap.get("b_no");
    } else{
       path = "redirect:boardError.jsp";
    }
    return path; // forward: 가 붙어있기 때문에 webapp폴더 아래에서 찾는다.
  } 

  @GetMapping("boardDelete")
  public String boardDelete(@RequestParam Map<String, Object> pMap){ 
    logger.info("boardDelete => Controller");
    int result = -1;
    String path = "";
    result = boardLogic.boardDelete(pMap); // 전체조회 및 조건검색
    if (result == 1) {
      path = "redirect:boardList";
    } else{
       path = "redirect:boardError.jsp";
    }
    return path; // forward: 가 붙어있기 때문에 webapp폴더 아래에서 찾는다.
  } 

  @PostMapping("commentInsert")
  public String commentInsert(@RequestParam Map<String, Object> pMap){ 
    int result = -1;
    String path = "";
    result = boardLogic.commentInsert(pMap); // 전체조회 및 조건검색
    if (result == 1) {
      path = "redirect:boardDetail?b_no="+pMap.get("b_no");
    } else{
       path = "redirect:boardError.jsp";
    }
    return path; // forward: 가 붙어있기 때문에 webapp폴더 아래에서 찾는다.
  } 

  @GetMapping("commentDelete")
  public String commentDelete(@RequestParam Map<String, Object> pMap){ 
    int result = -1;
    String path = "";
    result = boardLogic.commentDelete(pMap); // 전체조회 및 조건검색
    if (result == 1) {
      path = "redirect:boardDetail?b_no="+pMap.get("b_no");
    } else{
       path = "redirect:boardError.jsp";
    }
    return path; // forward: 가 붙어있기 때문에 webapp폴더 아래에서 찾는다.
  } 
}
