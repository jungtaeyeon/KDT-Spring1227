package com.example.demo.dao;

import java.util.Map;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberDao {
  Logger logger = LoggerFactory.getLogger(MemberDao.class);

  @Autowired
  private SqlSessionTemplate sqlSessionTemplate;

  // 한번에 여러 건을 등록하는 실습 예제 이다.
  public int fileInsert(List<Map<String, Object>> picList){
    int result = 0;
    result = sqlSessionTemplate.insert("fileInsert", picList);
    logger.info(String.valueOf(result)); // 등록 요청 결과 출력해보기
    return result;
  }

  public int memberInsert(Map<String, Object> pmap) {
    int result = 0;

    //관전포인트 - 시퀀스로 채번된 번호를 파라미터 변수인 map에 담겨있는지 확인하기
    result = sqlSessionTemplate.insert("memberInsert", pmap);
    // mem_no는 화면에서 입력받는 값이 아니다.
    // 오라클에서는 자동채번을 해주는 시퀀스 오브젝트가 제공된다.
    // 시퀀스는 에러가 발동하더라도 무조건 1증가된 값이 채번된다.
    // 문제제기 - 채번된 숫자를 유지해야 한다. - 왜냐면 테이블이 board_master테이블에 사용된 번호를
    // 첨부파일이 등록되는 board_sub테이블에 한번 더 사용해야 합니다 - 동일한 번호를 사용해야 된다.
    // 결과 - insert메소드의 리턴타입은 성공유무를 반환하는 1 또는 0만 반환되는 함수 이다.
    // 그런데 우리는 오라클 서버에 채번된 숫자를 자바로 꺼내와야 한다. -> 리턴타입이 아니라 파라미터에 담아준다. myBatis에서
    // 이 옵션이 useGeneratedKeys="true"

    logger.info(pmap.get("mem_no").toString());
    return result; // 1이면 입력 성공 0이면 실패
  }

  public List<Map<String, Object>> memberList(Map<String, Object> rmap) {
    List<Map<String, Object>> mList = null;
    mList = sqlSessionTemplate.selectList("memberList", rmap);
    return mList;
  }
  // 댓글 처리
  public List<Map<String, Object>> comentList(Map<String, Object> rmap) {
    List<Map<String, Object>> cList = null;
    cList = sqlSessionTemplate.selectList("comentList", rmap);
    return cList;
  }

  public int memberDelete(int mem_no) {
    int result = 0;
    result = sqlSessionTemplate.delete("memberDelete", mem_no);
    return result;
  }

  public int memberUpdate(Map<String, Object> pmap) {
    int result = 0;
    result = sqlSessionTemplate.update("memberUpdate", pmap);
    return result;
  }
  
}
