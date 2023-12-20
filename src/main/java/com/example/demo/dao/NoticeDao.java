package com.example.demo.dao;

import java.util.List;
import java.util.Map;
import java.util.*;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
// XXXDao 클래스는 MVC패턴에 영향을 주는 클래스는 아니다. - 디자인 패턴? 으로 이해하자.

@Service
// @Repository // @Service 를 사용해도 기능에 문제는 없다!
public class NoticeDao {
  Logger logger = LoggerFactory.getLogger(NoticeDao.class);
  @Autowired
  SqlSessionTemplate sqlSessionTemplate = null; // -> SqlSession


  public List<Map<String, Object>> noticeList(Map<String, Object> pmap) {
    logger.info("NoticeDao -> noticeList()");
    // List<Map<String, Object>> list = sqlSessionTemplate.selectList("noticeList"); // 전체조회
    List<Map<String, Object>> list = sqlSessionTemplate.selectList("noticeList", pmap); // gubun: n_title, keyword: 휴관
    logger.info(list.toString());
    return list;
  }

}
