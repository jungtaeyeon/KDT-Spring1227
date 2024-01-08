<%@ page language="java" contentType="text/html;charset=UTF-8"
pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>500[webapp]</title>
  </head>
  <body>
    500에러 페이지
    <h2>500에러 발동[WEB-INF]</h2>
    <h3>error500.jsp가 에러페이지</h3>
    발생한 예외 : ${ex}, ${pageContext.exception}<br />
    예외 메시지 : ${ex.message}, ${pageContext.exception.message}
    <ol>
      <c:forEach items="${pageContext.exception.stackTrace}" var="i">
        <li>${i.toString()}</li>
      </c:forEach>
    </ol>
  </body>
</html>
