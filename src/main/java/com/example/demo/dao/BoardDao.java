package com.example.demo.dao;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.logic.BoardLogic;
// ApplicationContext의 부모 클래스가 BeanFactory이다. 그래서 아들이 더 많은 것을 쥐고있다.
// 빈 컨테이너 - BeanFactory(게으른), ApplicationContext(이른)
// Persistance 계층
// myBatis-spring.jar
@Repository
public class BoardDao {
  Logger logger = LoggerFactory.getLogger(BoardDao.class);
  // BoardController -> BoardLogic -> BoardDao{여기까지가 자바} -> SqlsessionTemplate
  // 주의사항은 스프링이 ComponentScan(com.example.demo 모든 클래스 - 하위폴더에 있는 것도 포함) - 이른 주입을 하기 위해!
  @Autowired
  private SqlSessionTemplate sqlSessionTemplate; // myBatis-spring.jar가 지원하는 자바 클래스

  public List<Map<String, Object>> boardList(Map<String, Object> pMap) {
    List<Map<String, Object>> bList = null;
    bList = sqlSessionTemplate.selectList("boardList", pMap);
    logger.info(bList.toString());
    return bList;
  }
  
  // public List<Map<String, Object>> boardDetail(Map<String, Object> pMap) {
  //   List<Map<String, Object>> bList = null;
  //   bList = sqlSessionTemplate.selectList("boardDetail", pMap);
  //   logger.info(bList.toString());
  //   return bList;
  // }

  public int boardUpdate(Map<String, Object> pMap) {
    int result = -1;
    result = sqlSessionTemplate.update("boardUpdate", pMap);
    return result;
  }

  public int hitCount(Map<String, Object> pMap) {
    logger.info("hitCount");
    int result = 0;
    result = sqlSessionTemplate.update("hitCount", pMap);
    return result;
  }

  public List<Map<String, Object>> commentList(Map<String, Object> pMap) {
    List<Map<String, Object>> commentList = sqlSessionTemplate.selectList("commentList", pMap);
    logger.info(commentList.toString());
    return commentList;
  }

  public int commentInsert(Map<String, Object> pMap) {
    logger.info("commentInsert");
    int result = 0;
    result = sqlSessionTemplate.insert("commentInsert", pMap);
    return result;
  }

  public int commentDelete(Map<String, Object> pMap) {
    logger.info("commentDelete");
    int result = 0;
    result = sqlSessionTemplate.delete("commentDelete", pMap);
    return result;
  }

  public int boardDelete(Map<String, Object> pMap) {
    logger.info("boardDelete => Dao");
    int result = 0;
    result = sqlSessionTemplate.delete("boardDelete", pMap);
    return result;
  }

  public int boardInsert(Map<String, Object> pMap) {
    logger.info("Dao => boardInsert"+pMap);
    int result = 0;
    result = sqlSessionTemplate.insert("boardInsert", pMap);
    return result;
  }
}
