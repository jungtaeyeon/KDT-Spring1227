package com.example.demo.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberDao;

import java.util.*;

@Service
public class MemberLogic {
  Logger logger = LoggerFactory.getLogger(MemberLogic.class);

  @Autowired
  private MemberDao memberDao;

  // 한번에 여러 건을 등록하는 실습 예제 이다.
  public int fileInsert(){
    int result = 0;
    List<Map<String, Object>> picList = new ArrayList<>();
    Map<String, Object> pmap = null;
    pmap = new HashMap<>();
    pmap.put("file_no", "1");
    pmap.put("file_name", "a.png");
    pmap.put("file_size", 1.5);
    picList.add(pmap);
    pmap = new HashMap<>();
    pmap.put("file_no", "2");
    pmap.put("file_name", "b.png");
    pmap.put("file_size", 2.5);
    picList.add(pmap);
    pmap = new HashMap<>();
    pmap.put("file_no", "3");
    pmap.put("file_name", "c.png");
    pmap.put("file_size", 3.5);
    picList.add(pmap);
    result = memberDao.fileInsert(picList);
    logger.info(String.valueOf(result));
    return result;
  }

  public int memberInsert(Map<String, Object> pmap) {
    int result = memberDao.memberInsert(pmap);
    Map<String, Object> pmap2 = new HashMap<>();
    if (result == 1) {
      int mem_no = 0;
      if (pmap.containsKey("mem_no")) { // 시퀀스에서 담아왔다면
        mem_no = Integer.parseInt(pmap.get("mem_no").toString());
        System.out.println("mem_no =======> "+ mem_no);
        pmap2.put("mem_no", mem_no);
      }
      // 파일 추가 테이블에 insert 하기
      // memberDao.fileInsert(pamp); // mem_no
      // fileDao.fileInsert(pmap2);
    }
    return result;
  } ////////////// end of 회원등록

  public int memberDelete(int mem_no) {
    logger.info("MemberLogic => memberDelete 호출");
    int result = 0;
    result = memberDao.memberDelete(mem_no);
    return result;
  }
  // Logic클래스를 꼭 구현해야 하나?? -> 업무적인 복잡도가 낮아서 지금 그렇게 보이는 것 이다.
  // 여기가 pointcut -> * com.example.demo.*Logic{클래스}.cud*{변수}(...) -> 간섭해서 자동으로 코드 추가 - 일괄처리
  // -> @Transactional을 걸어야 하는 곳
  public List<Map<String, Object>> memberList(Map<String, Object> rmap) {
    List<Map<String, Object>> mList = null;
    // before advice con.setAutoCommit(false);
    mList = memberDao.memberList(rmap);
    // after advice con.commit();
    // con.setAutoCommit(true);
    return mList;
  }

  public List<Map<String, Object>> memberDetail(Map<String, Object> rmap) {
    List<Map<String, Object>> mList = null;
// before advice con.setAutoCommit(false);
    mList = memberDao.memberList(rmap);
// 댓글 처리 !!!!!!!!!!!!!!!!!!
    // List<Map<String, Object>> cList = null;
    // cList = memberDao.memberList(rmap);
    // // [{1건},{comments:[{1건},{1건},{1건}]}]  이런 구조
    // // Map<String, Object> cmap = new HashMap<>();
    // // cmap.put("1",cList);
    // // mList.add(cmap);

// after advice con.commit();
// con.setAutoCommit(true);

    return mList;
  }

  public int memberUpdate(Map<String, Object> pmap) {
    int result = 0;
    result = memberDao.memberUpdate(pmap);
    return result;
  }

}
