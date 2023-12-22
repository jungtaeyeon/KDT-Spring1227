<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<%@include file="/common/bootstrap_common.jsp"%>
<link rel="stylesheet" href="/css/main.css?1">
</head>
<body>
	<!-- header start -->
	<%@include file="/include/gym_header.jsp"%>
	<!-- header end    -->
	<!-- body start    -->
	<div class="container">
	
		<form id="f_login" action="/auth2/login.pj3" method="post">
			<div class="mb-3 mt-3">
				<label for="mem_email" class="form-label">Email:</label> <input
					type="text" class="form-control" id="mem_email"
					placeholder="Enter Email" name="mem_email">
			</div>
			<div class="mb-3">
				<label for="pwd" class="form-label">Password:</label> <input
					type="password" class="form-control" id="mem_pw"
					placeholder="Enter password" name="mem_pw">
			</div>
			<button type="button" id="btn-login" class="btn btn-primary">로그인</button>
			<script>
				const btnLogin = document.querySelector("#btn-login")
				btnLogin.addEventListener('click', (e) => {
					//alert('11');
					document.querySelector("#f_login").submit();
				})
			</script>			
			<a href="https://kauth.kakao.com/oauth/authorize?client_id=dab7a2fa14e99d854cd5a5757db20c79&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code">
        <img src="/images/ko/kakao_login_medium_narrow.png" alt="카카오로그인">
      </a>			
		    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#memberForm">
		    회원가입
		    </button>
		</form>	
	
	</div>
	<!-- body end    -->	
	<!-- footer start -->
	<%@include file="/include/gym_footer.jsp"%>
	<!-- footer end    -->		
	<!-- ========================== [[ 회원가입 Modal - 우편번호검색기 ]] ========================== -->
	<div class="modal" id="memberForm">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">회원가입[POJO3]</h4>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">
	      	<form id="f_member" method="get" action="./memberInsert.pj3">
	      	  <input type="hidden" name="method" value="memberInsert">
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="mem_id" name="mem_id" placeholder="Enter 아이디" />
	            <label for="mem_id">아이디</label>
	          </div>	      	
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="mem_pw" name="mem_pw" placeholder="Enter 비밀번호" />
	            <label for="mem_pw">비밀번호</label>
	          </div>	      	
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="mem_name" name="mem_name" placeholder="Enter 이름" />
	            <label for="mem_name">이름</label>
	          </div>	      	
	          <div class="input-group">
	            <input type="text"  class="form-control" id="mem_zipcode" name="mem_zipcode" placeholder="우편번호" />
	            &nbsp;
	            <input type="button" class="btn btn-success" onclick="zipcodeForm()" value="우편번호찾기">
	          </div>	    
	          <div style="margin-bottom:5px;"></div>  	
	          <div style="display:flex; flex-wrap:wrap;">
	            <input type="text"  class="form-control" id="mem_address" name="mem_address" placeholder="주소" />
	          </div>	  
	      	</form>
	      </div>
	
	      <!-- Modal footer -->
	      <div class="modal-footer">
	        <input type="button" class="btn btn-warning" data-bs-dismiss="modal" onclick="memberInsert()"  value="저장">
	        <input type="button" class="btn btn-danger" data-bs-dismiss="modal" value="닫기">
	      </div>
	
	    </div>
	  </div>
	</div>
    <!-- ========================== [[ 회원가입 Modal ]] ========================== -->				
</body>
</html>