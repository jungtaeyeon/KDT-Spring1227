package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.logic.NoticeLogic;

import java.util.*;

// 스프링 에서는 URL매핑이 4번을 디폴트로 하고 있다.
// 서블릿 컨테이너와는 다르게 메소드마다 URL을 지정할 수 있다.
// URL은 요청 - 요청방식에 차이가 있다 - get,post(바이너리처리-첨부파일처리 가능함),put,delete - 전송하는 방식에 대한 차이도 갖고있다.
// Controller는 클래스 선언 앞에 온다. -> 페이지 출력일 때 사용한다.
// 만일 @Controller가 있는 클래스에서 Json포맷을 예외적으로 사용하고 싶다면 @ResponseBody를 사용함
// @RestController도 Controller와 동일하게 컨트롤 계층을 지원하는 어노테이션이다. - 처리결과가 페이지가 아닌경우 사용
// 프론트계층(Reactjs)과 백엔드 계층 분리
// RequestMapping은 클래스 이름 앞에, 메소드 이름 앞에도 가능한데 최근에는 GetMapping과 PostMapping지원함에
// 따라서 대체로 클래스 선언 앞에서만 주로 사용한다.
// @RequestParam은 메소드의 파라미터 자리에 사용되는 어노테이션 이다.
// @Service 어노테이션은 모델계층(처리를 담당-기능 -> 비지니스로직+퍼시스턴스계층)
// DB연동을 위한 클래스를 따로 설계하는 것을 추천 -> myBatis(라이브러리), hibernate(프레임워크) 지원되므로 
@Controller // 따로 상속을 받지 않고 어노테이션을 붙이는 것 으로 충분
@RequestMapping("/notice/*")
public class NoticeController {
  Logger logger = LoggerFactory.getLogger(NoticeController.class);
  @Autowired // -> 서버가 올라갈 때 자동으로 인스턴스화를 해줌
  NoticeLogic noticeLogic = null;

  // 전체조회 및 조건 검색일 때
  // SELECT * FROM notice WHERE gubun=? AND keyword=?
  @GetMapping("noticeList")
  public String noticeList(@RequestParam Map<String, Object> pMap, Model model){ 
    List<Map<String, Object>> nList = null;
    nList = noticeLogic.noticeList(pMap); // 전체조회 및 조건검색
    model.addAttribute("nList", nList);
    return "forward:noticeList.jsp"; // forward: 가 붙어있기 때문에 webapp폴더 아래에서 찾는다.
  } 
  
  // insert into notice(n_no, n_title, n_content, n_writer) valuse(?,?,?,?)
  @PostMapping("noticeInsert")
  public String noticeInsert(@RequestParam Map<String, Object> pMap){ // 파라미터에 n_no, n_title, n_content, n_writer 이런식으로 모두 선언하기 보다는.. Map
    int result = 0;
    String path = "";
    result = noticeLogic.noticeInsert(pMap);
    if (result == 1) { // 입력이 성공했을 때
      path = "redirect:noticeList";
    } else {
      path = "redirect:noticeErroe.jsp";
    }
    logger.info(Integer.toString(result));
    return path;
    // return "redirect:noticeList.jsp";
  }

  @GetMapping("noticeUpdate")
  public String noticeUpdate(@RequestParam Map<String, Object> pMap){ // 파라미터에 n_no, n_title, n_content, n_writer 이런식으로 모두 선언하기 보다는.. Map
    logger.info("noticeUpdate 호출");
    logger.info(pMap.get("n_title").toString()+", "+pMap.get("n_content").toString()+", "+pMap.get("n_writer").toString());
    int result = 0;
    result = noticeLogic.noticeUpdate(pMap);
    logger.info(Integer.toString(result));
    return "redirect:noticeList"; // 이렇게 하면 화면을 호출하는게 아니라 URL을 호출하는 것 -> 그럼 noticeList() 메소드가 호출!
    // return "redirect:noticeList.jsp"; 
  }
  
  @GetMapping("noticeDelete")
  public String noticeDelete(@RequestParam Map<String, Object> pMap){ // 파라미터에 n_no, n_title, n_content, n_writer 이런식으로 모두 선언하기 보다는.. Map
    logger.info("noticeDelete 호출");
    int result = 0;
    result = noticeLogic.noticeDelete(pMap);
    logger.info(Integer.toString(result));
    return "redirect:noticeList"; // 이렇게 하면 화면을 호출하는게 아니라 URL을 호출하는 것 -> 그럼 noticeList() 메소드가 호출!
    // return "redirect:noticeList.jsp";
  }
}
