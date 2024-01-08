<%@ page language="java"	contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, java.util.ArrayList, java.util.HashMap" %>        
<%
// 상세보기 한 건 size값 담기
	int size = 0;//지변이니까 초기화를 생략하면 에러발생함.
// b_no가 2인 글번에 해당하는 댓글 3건 size값 담기
	int size2 = 0;//지변이니까 초기화를 생략하면 에러발생함.

	// insert here - boardDetail(b_no=2) -> hitCount(b_no2) -> commentList: List
	// bList안에 키값이 comments인 bList.get(1) -> commentList가 들어있다.
	List<Map<String,Object>> bList = (List)request.getAttribute("bList");
		List<Map<String,Object>> commentList = null;
		Map<String,Object> rmap = new HashMap<>();
	if(bList != null){
		size = bList.size();
		rmap = bList.get(0); // 상세보기 내용

		Map<String,Object> comments = (Map)bList.get(1);
			if(comments.containsKey("comments")){
				commentList = (List)comments.get("comments");
				size2 = commentList.size(); // 3
			}
	}
	
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항상세</title>
<%@include file="/common/bootstrap_common.jsp"%>
<link rel="stylesheet" href="/css/board.css">
<script type="text/javascript">
	const commentInsert = () => {
		document.querySelector("#f_comment").submit();
	}
	const commentDelete = (bc_no, b_no) => {
		location.href="./commentDelete?bc_no="+bc_no+"&b_no="+b_no;
	}

	const boardUpdate = () => {
		console.log("update 호출")
		document.querySelector("#f_board").submit();
	}

  const boardList = () => {
    location.href = "/board/boardList";
  }

	const boardDelete = () => {
		location.href = "./boardDelete?b_no="+<%=rmap.get("B_NO")%>;
	}


</script>
</head>
<body>
	<!-- header start -->
	<%@include file="/include/gym_header.jsp"%>
	<!-- header end    -->
	<!-- body start    -->
	<div class="container">
		<div class="page-header">
			<h2>게시판 <small>글상세보기</small></h2>
			<hr />
		</div>
		
		<!-- 공지목록 시작 -->


	      <div style="width:58rem;">
	        <div class="card-body">
	          <div class='book-detail'>
	            <div class='book-header'>
	            
				<div class="input-group mb-3">
				<span class="input-group-text">제목</span>
				<div style="width:500px;">
					<input type="text" class="form-control"  value="<%=rmap.get("B_TITLE") %>">
				</div>
				</div>
				
				<div class="input-group mb-3">
				<span class="input-group-text">작성자</span>
				<div class="col-xs-3">
					<input type="text" class="form-control"  value="<%=rmap.get("B_WRITER") %>">
				</div>
				</div>
				
				<div class="input-group mb-3">
				<div class="col-xs-8">
					<%=rmap.get("B_CONTENT") %>
				</div>
				</div>

	            </div>
	          </div>
	        </div>
			<hr/>
	        <div class='detail-link'>
	          <button class="btn btn-info" data-bs-toggle="modal" data-bs-target="#boardUpdateForm">
	          	수정
	          </button>
	          &nbsp;
	          <button class="btn btn-warning" onclick="boardDelete()">
	            삭제
	          </button>
	          &nbsp;
	          <button class="btn btn-success" onclick="boardList()">
	          공지목록
	          </button>
	        </div>
	      </div>  
		<!-- 회원목록   끝  -->		
				<hr/>
		<!-- 댓글쓰기 시작  -->		
		<div class="card">
			<form id="f_comment" method="post" action="./commentInsert">
				<input type="hidden" name="b_no" value="<%=rmap.get("B_NO")%>" />
				<input type="hidden" name="bc_writer" value="나신입" /> 
				<div class="card-body"><textarea class="form-control" rows="1" name="bc_comment"></textarea></div>
				<div class="card-footer">
					<button id="btnAdd" class="btn btn-primary" type="button" onclick="commentInsert()">등록</button>
				</div>
			</form>		
		</div>		
		<!-- 댓글쓰기  끝   -->		
		<br />
		<!-- 댓글목록 시작  -->		
		<div class="card">
			<div class="card-header">댓글 리스트</div>
			<ul id="comment--items" class="list-group">
				<%
					if(size2>0) { // 댓글이 있나요?
					for(int i=0; i<size2; i++){
					Map<String,Object> cmap = new HashMap<>();
					cmap = (Map)commentList.get(i);
				%>
				<li class="list-group-item d-flex justify-content-between">
					<div><%=cmap.get("BC_COMMENT")%></div>
					 <div class="d-flex">
							<div><%=cmap.get("BC_WRITER")%></div>
							<button class="badge btn btn-danger" onclick="commentDelete('<%=cmap.get("BC_NO")%>', '<%=rmap.get("B_NO")%>')">삭제</button>
					 </div>
			</li>
				<%
						} // end of for
					} // end of if
				%>
			</ul>
		</div>		
		<!-- 댓글목록  끝  -->		
	</div>
	<!-- body end       -->
	<!-- footer start -->
	<%@include file="/include/gym_footer.jsp"%>
	<!-- footer end    -->	
	
	<!-- ========================== [[ 공지사항 수정 Modal ]] ========================== -->
	<div class="modal" id="boardUpdateForm">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">공지사항수정</h4>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">
	      	<form id="f_board" method="post" action="./boardUpdate">
	      	  <input type="hidden" name="method" value="boardUpdate">
	      	  <input type="hidden" name="b_no" value="<%=rmap.get("B_NO")%>">
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="b_title" name="b_title" placeholder="Enter 제목"  value="<%=rmap.get("B_TITLE")%>"/>
	            <label for="mem_id">제목</label>
	          </div>	      	
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="b_writer" name="b_writer" placeholder="Enter 작성자"   value="<%=rmap.get("B_WRITER")%>"/>
	            <label for="mem_pw">작성자</label>
	          </div>	      		  
	          <div class="form-floating mb-3 mt-3">
	            <textarea rows="5" class="form-control h-25" aria-label="With textarea" id="b_content" name="b_content"><%=rmap.get("B_CONTENT")%></textarea>
	          </div>	      	
	      	</form>
	      </div>
	
	      <!-- Modal footer -->
	      <div class="modal-footer">
	        <input type="button" class="btn btn-warning" data-bs-dismiss="modal" value="저장" onclick="boardUpdate()">
	        <input type="button" class="btn btn-danger" data-bs-dismiss="modal" value="닫기">
	      </div>
	
	    </div>
	  </div>
	</div>
    <!-- ========================== [[ 회원가입 Modal ]] ========================== -->		
	
</body>
</html>