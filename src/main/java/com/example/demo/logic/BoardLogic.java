package com.example.demo.logic;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.BoardDao;

@Service // @Component -> 빈을 등록해서 주입받아야 할 클래스
public class BoardLogic {
  Logger logger = LoggerFactory.getLogger(BoardLogic.class);
  @Autowired
  BoardDao boardDao = null;

  public List<Map<String, Object>> boardList(@RequestParam Map<String, Object> pMap) {
    logger.info("BoardLogic -> boardList()");
    List<Map<String, Object>> list = null;
    list = boardDao.boardList(pMap); 
    // 조회수를 수정하는 로직을 이 안에서 분리할 수 없다. - boardDetail따로 분리해야 하는 이유 4번째.
    return list;
  }
  
  // 화면과 로직은 분리되어야 한다. MVC
  // 여기가 바로 로직 - 다중처리가 필요하다 - select2번, update1번
  // dirty read 방지: 트랜잭션이 commit되어 확정된 데이터만을 읽도록 허용
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public List<Map<String, Object>> boardDetail(Map<String, Object> pMap) {
    // 상세보기 가져오기
    List<Map<String, Object>> list = null;
    list = boardDao.boardList(pMap); 

    // 상세보기에서 size가 1이면 update테이블 set b_hit = b_hit+1 where b_no=2
    if (list.size()==1) {
      // 만일 update를 하다가 SQLException(부적합한 식별자) 발동하면 에러페이지를 보여달라 - 요구사항
      
      try {
        boardDao.hitCount(pMap);
      } catch (Exception e) {
        logger.info(e.toString());
      }
    }

    // 댓글 가져오기
    /*
     SELECT
			BC_NO, BC_WRITER, BC_COMMENT
		FROM BOARD230527_COMMENT NATURAL JOIN BOARD230527  
	  WHERE B_NO = ${b_no}
	  ORDER BY BC_NO desc

    [
      {}, // bList.get(0) - 상세보기 내용을 담은 Map
      {
        comments: [
          {}, {}, {}
        ]
      }
    ]
     */
    List<Map<String, Object>> commentList = boardDao.commentList(pMap);
    // if(commentList != null && commentList.size()>0) { // && commentList.size()>0 => 댓글이 없는 게시글은 진입 불가능..
    if(commentList != null) { // 댓글이 있다면
      Map<String, Object> cMap = new HashMap<>();
      cMap.put("comments", commentList);
      // 댓글 결과 3건을 list안에 담아주자.
      list.add(1,cMap);
    }
    return list;
  }

  // // 화면과 로직은 분리되어야 한다. MVC
  // // 여기가 바로 로직 - 다중처리가 필요하다 - select2번, update1번
  // public List<Map<String, Object>> boardDetail(Map<String, Object> pMap) {
  //   List<Map<String, Object>> list1 = new ArrayList<>();
  //   List<Map<String, Object>> list2 = new ArrayList<>();
  //   List<Map<String, Object>> list3 = new ArrayList<>();
  //   Map<String, Object> map1 = new HashMap<>();
  //   Map<String, Object> map2 = new HashMap<>();

  //   list2 = boardDao.boardList(pMap); 
  //   list3 = boardDao.boardDetail(pMap);
  //   map1 = list2.get(0);
  //   list1.add(map1);

  //   for(int i=0; i<list3.size(); i++){
  //     map2 = list3.get(i);
  //     list1.add(map2);
  //   }
  //   logger.info(list1.toString());
  //   return list1;
  // }

  public int boardUpdate(Map<String, Object> pMap) {
    int result = 1;
    result = boardDao.boardUpdate(pMap);
    return result;
  }

  public int commentInsert(Map<String, Object> pMap) {
    int result = 0;
    result = boardDao.commentInsert(pMap);
    return result;
  }

  public int commentDelete(Map<String, Object> pMap) {
    int result = 0;
    result = boardDao.commentDelete(pMap);
    return result;
  }

  public int boardDelete(Map<String, Object> pMap) {
    logger.info("boardDelete => Logic");
    int result = 0;
    result = boardDao.boardDelete(pMap);
    return result;
  }
// ==================================== [[ quil editor ]] ==================================== 
public String imageUpload(MultipartFile image, String savePath) {
  Map<String, Object> pMap = new HashMap<String, Object>();
  logger.info("image:" + image);
  String filename = null;
  String fullPath = null;

  // 첨부파일이 존재하나?
  if (image != null && !image.isEmpty()) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    Calendar time = Calendar.getInstance();
    filename = sdf.format(time.getTime()) + '-' + image.getOriginalFilename().replaceAll(" ", "-");
    fullPath = savePath + "//" + filename;
    try {
      logger.info("fullPath : " + fullPath);
      File file = new File(fullPath);// 파일명만 존재하고 내용은 없는
      byte[] bytes = image.getBytes();
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
      bos.write(bytes);
      bos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  return filename;
}

public byte[] imageDownload(String imageName, String savePath) {
  String fname = null;
  try {
    fname = URLDecoder.decode(imageName, "UTF-8");
    logger.info(fname);
  } catch (UnsupportedEncodingException e1) {
    e1.printStackTrace();
  }
  File file = new File(savePath, fname.trim());

  // 해당 파일을 읽어오는 객체 생성해 줌. - 이 때 파일명을 객체화 한 주소번지가 필요함.
  FileInputStream fis = null;
  ByteArrayOutputStream baos = new ByteArrayOutputStream();

  try {
    fis = new FileInputStream(file);
  } catch (FileNotFoundException e) {
    e.printStackTrace();
  }

  int readCount = 0;
  byte[] buffer = new byte[1024];
  byte[] fileArray = null;

  try {
    while ((readCount = fis.read(buffer)) != -1) {
      baos.write(buffer, 0, readCount);
    }
    fileArray = baos.toByteArray();
    fis.close();
    baos.close();
  } catch (IOException e) {
    throw new RuntimeException("File Error");
  }
  return fileArray;
}////end of imageDownload
  // ==================================== [[ quil editor ]] ==================================== 

public int boardInsert(Map<String, Object> pMap) {
  logger.info("Logic => boardInsert"+pMap);
  int result = 0;
  result = boardDao.boardInsert(pMap);
  return result;
}
}
