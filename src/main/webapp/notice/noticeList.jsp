<%@ page language="java"	contentType="text/html;charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>공지사항 - {webapp}</title>
</head>
<body>
  <h2>공지사항 목록</h2>
</body>
</html>
<!-- 
  공지사항 목록을 요청하는 두 가지 방법
  서버를 경유하는 것은?? 2번
  1. http://localhost:8000/notice/noticeList.jsp
    : 오라클 서버를 경유하지 않는다. - 불만
    : 조회 버튼을 누르면 그 때 서버를 경유하게 설계하자.
  2. http://localhost:8000/notice/noticeList
    : 오라클 서버를 경유한다. - 화면이 열릴 때 목록이 출력된다.
 -->