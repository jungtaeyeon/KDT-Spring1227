package com.example.demo.logic;

import java.util.List;
import java.util.Map;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.NoticeDao;

@Service // 모델 계층
public class NoticeLogic {
  Logger logger = LoggerFactory.getLogger(NoticeLogic.class);
  @Autowired
  NoticeDao noticeDao = null;

  public List<Map<String, Object>> noticeList(Map<String, Object> pmap) {
    logger.info("NoticeLogic -> noticeList()");
    List<Map<String, Object>> list = null;
    list = noticeDao.noticeList(pmap);

    return list;
  }
  
}
