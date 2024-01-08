<%@ page language="java"	contentType="text/html;charset=UTF-8"	pageEncoding="UTF-8"%>
<%
  // 스크립틀릿
  // 쿠키를 삭제하는 별도의 메소드는 없다.
  // 쿠키를 생성하고 시간을 0으로 설정하면 파기된다. - 개발자센터(F12)에서 확인이 가능하다.
  Cookie c_name = new Cookie("ename","smith");
  c_name.setMaxAge(0); // 60초 동안

  Cookie c_name2 = new Cookie("ename2","");
  c_name2.setMaxAge(0); // 60초 동안

  response.addCookie(c_name); // 쿠키를 클라이언트에 내려줌
  response.addCookie(c_name2); // 쿠키를 클라이언트에 내려줌
%>