package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.logic.BoardLogic;

import jakarta.servlet.http.HttpServletRequest;

@RestController //@Controller + @ResponseBody
@RequestMapping("/board/*")
public class RestBoardController {//카카오로그인처리해보기
    Logger logger = LoggerFactory.getLogger(RestBoardController.class);
    @Autowired
    private BoardLogic boardLogic = null;
    @PostMapping("imageUpload")//url: '/board/imageUpload',
    public String imageUpload(@RequestParam(value="image") MultipartFile image, HttpServletRequest req) {
        String savePath = req.getSession().getServletContext().getRealPath("/pds");
        String filename = "";
        filename = boardLogic.imageUpload(image, savePath);
        //return "아바타.png";//imageGet요청을 보내서 image url정보를 받아와야 함
        return filename;
    }

  //먼저 이미지를 업로드하고 호출된 메소드의 반환값으로 이미지이름을 받아서 
  //그 다음에 그 이미지이름을 가지고 아래 메소드 다시 요청한다
  //const url = "/board/imageGet?imageName="+response;//
  @GetMapping("imageGet")//url: '/board/imageUpload',
  public byte[] imageGet(@RequestParam(value="imageName") String imageName, HttpServletRequest req) {
      String savePath = req.getSession().getServletContext().getRealPath("/pds");
      String filename = "";
      //boardLogic.imageUpload(image, savePath);
      //return "아바타.png";//imageGet요청을 보내서 image url정보를 받아와야 함
      byte[] fileArray = boardLogic.imageDownload(imageName, savePath);
      return fileArray;
  }

  // @GetMapping("imageGet") // url: '/board/imageUpload'
  // public ResponseEntity<Resource> imageGet(@RequestParam(value="image") MultipartFile image, HttpServletRequest req) {
  //   String savePath = req.getSession().getServletContext().getRealPath("/pds");
  //   String filename = "";
  //   // return 아바타.png;
  //   return filename;
  // }
  
}
