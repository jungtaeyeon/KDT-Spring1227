<%@ page language="java"	contentType="text/html;charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>errorPage{WEB-INF}</title>
</head>
<body>
  <h2>에러가 발생했습니다.</h2>
  발생한 예외: ${ex} <br> <!-- GlobalException의 매개변수 Model에 추가된 정보를 읽어옴 -->
  예외 메시지: ${ex.message} <!-- exception.getMessage(), e.toString -->
</body>
</html>
<!-- 
  
 -->