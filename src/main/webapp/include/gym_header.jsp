<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    // 스크립틀릿의 위치는 html태그의 위치와 의존관계가 있나?? -> 없다 -> 누가 처리하는 코드?? - 톰캣
    String nickname = (String)session.getAttribute("nickname");
    // 로그인을 하지 않았다면 null이 출력
    //out.print(s_nickname); // 로그인을 했다면 카카오 계정에 등록된 이름이 출력된다.
    %>
<nav class="navbar navbar-expand-sm bg-light navbar-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="">여기내GYM</a>
    <div class="collapse navbar-collapse">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" href="/auth/loginForm.jsp">로그인</a>
        </li>
        <li class="nav-item">
        <!-- 
        확장자가 jsp이면 서블릿을 경유하지 않는다. - 목록에 보여줄 데이터가 없다?
        조회버튼 -> /notice/noticeList.gd요청하자 -  오라클 서버를 경유함
        확장자가 .gd이면 오라클 서버를 경유하니까 조회결과를 쥐고 있다.
        쥔다 - request.setAttribute() - 화면 출력하기
         -->
          <a class="nav-link active" href="/notice/noticeList">공지사항</a>
        </li>
        <li class="nav-item">
           <a class="nav-link" href="/board/boardList">게시판</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/member/memberList.jsp">회원관리</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">QnA게시판</a>
        </li>
      </ul>
      <!-- 
        세션이 있는지 비교한다 - 스크립틀릿 - 1번
        로그아웃 디자인코드를 삽입한다 - 2번
        이벤트 처리 한다 - 3번 ->자바스크립트
       -->
       <%
       // 세션값을 가지고 있을때만 보여질 화면과 이벤트 처리코드들을 가둔다.
        if(nickname != null){
       %>
      <form class="d-flex mb-2 mb-lg-0" role="search" id="logout1">
        <div class="me-auto mt-2 mb-lg-0"><%=nickname %>님 &nbsp;</div>
        <input type="button" class="btn btn-outline-success" onclick="logout()" value="로그아웃">
      </form>
      <%
        }
      %>

      <script>
       const logout = () => {
        location.href = "/auth/logout.jsp";
       }
      </script>

    </div>
  </div>
</nav>