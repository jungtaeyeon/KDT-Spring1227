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
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.logic.MemberLogic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
// @Controller("mCtl") -> 굳이 해당 클래스의 인스턴스 이름을 바꾸지 않는다. 
// 이 이름을 가지고 해당 클래스를 알아서 찾아주는데.. - 객체 주입이 실패할 수 있다.
@Controller("memberController")
@RequestMapping("/member/*")
public class MemberController {
  Logger logger = LoggerFactory.getLogger(MemberController.class);
  // 클래스의 관계 이어주는  - @Autowired
  @Autowired
  private MemberLogic memberLogic;

  // 한번에 여러 건을 등록하는 실습 예제 이다.
  @GetMapping("fileInsert")
  public String fileInsert(){
    memberLogic.fileInsert();
    return "redirect:/index.jsp";
  }

  /***********************************************************************************
  제목: 회원가입 구현
  작성자: 정태연
  작성일자: 2023년 12월 29일
  @param: Map
  @return: int(1:입력성공, 0:입력실패)
  참조: 화면정의서를 참고하여 구현해주세요.
  개발패턴: jsp - action(insert) - action(select) - jsp
  테이블이 1:n 관계 이면서 2개 이상의 테이블에 insert처리할 때 ? 
    INSERT INTO MEMBER231228(MEM_NO, MEM_ID, MEM_PW ,MEM_EMAIL
						, MEM_NAME, ZIPCODE, ADDRESS, GUBUN)
    VALUES(seq_member231228_no.nextval, 'kiwi', 123, 'kiwi@hot.com'
              , '키위', '12345', '서울시 마포구 공덕동', '0');
   ************************************************************************************/
  
   // 첨부파일 추가
  @PostMapping("memberInsert")
  public String memberInsert(HttpServletRequest req, @RequestParam("mem_picture") MultipartFile mem_picture, @RequestParam Map<String, Object> pmap){
    logger.info("memberInsert");
    // logger.info(mem_picture.toString());
    int result = -1;
    String path = "";
    String savePath = req.getSession().getServletContext().getRealPath("/pds");
    // HttpSession session = req.getSession();
    // savePath = session.getServletContext().getRealPath("/pds");
    String mempicture = null;
    if (mem_picture != null && mem_picture.toString().length() > 2) {
      mempicture = mem_picture.getOriginalFilename();
    }
    logger.info("mempicture: "+mempicture);

    // 첨부파일이 존재할 때 추가될 코드 - 첨부파일이 없다면 실행되지 않는 구간이다.
    if (mem_picture != null) {
      logger.info("mem_picture 널이 아닐 때"+mem_picture);
      String fullPath = savePath+"//"+mempicture;
      // 파일 이름을 객체로 만들어주는 클래스 이다. - 파일 이름만 생성되지 내용은 미포함
      File file = new File(fullPath);
      try {
        byte[] bytes = mem_picture.getBytes();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bos.write(bytes); // 이름만 생성된 파일에 내용을 쓰기 처리 !!
        bos.close();
        long size = file.length();
        double d_size = Math.floor(size/(1024.0));
        pmap.put("mem_picture", mempicture);
        // pmap.put("file_size", d_size); // 지금은 사이즈가 없지만, 다른 프로젝트에서 사이즈를 사용할 때 사용하자!
      } catch (Exception e) {
        e.printStackTrace();
      } /////// end of try catch
    } //////// end of if
    result = memberLogic.memberInsert(pmap);

    if(result ==1){
      path = "redirect:memberList";
    }else {
      path = "redirect:memberError.jsp"; 
    }
    return path;
  } //////////// end of memberInsert

  /***********************************************************************************
  제목: 회원 정보 수정 구현
  작성자: 정태연
  작성일자: 2024년 01월 03일
  @param: Map
  @return: int(1:수정성공, 0:수정실패)
  참조: 화면정의서를 참고하여 구현해주세요.
  개발패턴: jsp - action(insert) - action(select) - jsp
  테이블이 1:n 관계 이면서 2개 이상의 테이블에 insert처리할 때 ? 
    UPDATE MEMBER231228
      SET MEM_PW =:pw
      , MEM_NAME =:name
      , ZIPCODE =:zipcode
      , ADDRESS =:address
      , MEM_EMAIL =:email
    WHERE MEM_NO =:no;
   ************************************************************************************/
  
  @PostMapping("memberUpdate")
  public String memberUpdate(@RequestParam Map<String, Object> pmap){
    logger.info("memberUpdate");
    int result = -1;
    String path = "";
    result = memberLogic.memberUpdate(pmap);
    if(result ==1){
      path = "redirect:memberList";
    }else {
      path = "redirect:memberError.jsp"; 
    }
    return path;
  } //////////// end of memberUpdate
  
  /***********************************************************************************
  제목: 회원목록 구현
  작성자: 정태연
  작성일자: 2023년 12월 29일
  @param: 조건검색에 사용될 컬럼명과 키워드 받아오기
  @return: n건이 조회되면 List<Map>, List<Member231228>, 
  Map과 xxxVO의 사용 기준이 있나?? -> 타입 - 계산식
  1) Map
  : 사이즈에 제한이 없다.
  : 타입 NumberFormatException, CastException대상이다.
  2) VO
  : 사이즈에 제한이 있다. 컬럼을 매번 추가해줘야 한다.
  : 타입이 정해져 있어 형 전환코드가 필요 없다.

  참조: 화면정의서를 참고하여 구현해주세요.
    SELECT MEM_NO, MEM_ID, MEM_PW, MEM_NAME, ZIPCODE, ADDRESS
          , MEM_EMAIL, GUBUN 
      FROM MEMBER231228
    WHERE MEM_ID =:b
    AND MEM_NAME =:a
    AND ADDRESS LIKE '%'||:y||'%';
    데이터의 영속성을 활용한 결과값들은 viewModel에 꼭 필요하다. - 클래스 이름 추가할 때
    화면(<table:로우+컬럼>)과 데이터는 밀접한 관계가 있다.
   ************************************************************************************/
  @GetMapping("memberList")
  public String memberList(@RequestParam Map<String, Object> rmap, Model model){
    logger.info("memberList");
    List<Map<String, Object>> mList = null;
    mList = memberLogic.memberList(rmap);
    model.addAttribute("mList", mList);

    return "forward:./memberList.jsp";
  }
  /***********************************************************************************
  제목: 회원정보 삭제 구현
  작성자: 정태연
  작성일자: 2024년 01월 02일
  @param: int mem_no - number(5)
  @return: int(1:삭제성공, 0:삭제실패)
  참조: 화면정의서를 참고하여 구현해주세요.
  개발패턴: jsp - action(delete) - action(select) - jsp

    DELETE FROM MEMBER231228 
      WHERE mem_no = ?
   ************************************************************************************/
  
  @GetMapping("memberDelete")
  public String memberDelete(int mem_no){
    logger.info("memberDelete");
    int result = -1;
    String path = "";
    result = memberLogic.memberDelete(mem_no);

    if(result ==1){
      path = "redirect:memberList";
    }else {
      path = "redirect:memberError.jsp"; 
    }
    return path;
  } //////////// end of memberDelete
  
}
