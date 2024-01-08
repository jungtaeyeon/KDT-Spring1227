<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
		<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<%@include file="/common/bootstrap_common.jsp"%>
<link rel="stylesheet" href="/css/main.css?1">
</head>
<script>
	const memberInsert = () => {
		document.querySelector("#f_member").submit();
	}
</script>
<body>
	<!-- header start -->
	<%@include file="/include/gym_header.jsp"%>
	<!-- header end    -->
	<!-- body start    -->
	<div class="container">
		<form id="f_login" action="/authc/login" method="post" onsubmit="return formCheck(this)">
			<h3>로그인</h3>
			<div class="msg">에러 메시지 자리 ${param.msg}</div>
			<div class="mb-3 mt-3">
				<label for="mem_email" class="form-label">Email:</label> <input
					type="text" value="kiwi@hot.com" class="form-control" id="mem_email"
					placeholder="Enter Email" name="mem_email" autofocus>
			</div>
			<div class="mb-3">
				<label for="pwd" class="form-label">Password:</label> <input
					type="password" class="form-control" id="mem_pw2"
					placeholder="Enter password" name="mem_pw2">
			</div>

			<div>
				<label for="remember">기억하기</label> <input
					type="checkbox" id="remember" name="remember" ${empty cookie.mem_email.value ? "":"checked"}/>
			</div>
			
			<button type="submit" id="btn-login" class="btn btn-primary">로그인</button>
			<script>
				const formCheck = (f_login) => {
					let msg = '';
					if(f_login.mem_email.value.length == 0){
						msgShow('이메일을 입력하세요.', f_login.mem_email)
						return false;
					}
					if(f_login.mem_pw2.value.length == 0){
						msgShow('비밀번호를 입력하세요.', f_login.mem_pw2)
						return false;
					}
					return true;
				}
				const msgShow = (msg, element) => {
					document.querySelector(".msg").innerHTML=msg;
					if (element) {
						element.select();
					}
				}
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
	        <h4 class="modal-title">회원가입</h4>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">
					<form id="f_member" method="post" action="/member/memberInsert" enctype="multipart/form-data">
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="mem_id" name="mem_id" placeholder="Enter 아이디" />
	            <label for="mem_id">아이디</label>
	          </div>	      	
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="mem_email" name="mem_email" placeholder="Enter 아이디" />
	            <label for="mem_id">이메일</label>
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
	            <input type="text"  class="form-control" id="zipcode" name="zipcode" placeholder="우편번호" />
	            &nbsp;
	            <input type="button" class="btn btn-success" onclick="openZipcode()" value="우편번호찾기">
	          </div>	    
	          <div style="margin-bottom:5px;"></div>  	
	          <div style="display:flex; flex-wrap:wrap;">
	            <input type="text"  class="form-control" id="address" name="address" placeholder="주소" />
						</div>
	          <div style="display:flex; flex-wrap:wrap;">
	            <input type="text"  class="form-control" id="gubun" name="gubun" placeholder="관리자" />
						</div>
						<div class="input-gruop mt-3">
							<input type="file" name="mem_picture" id="mem_picture">
							<label class="input-group-text" for="mem_picture">Upload</label>
						</div>
						<!-- <div>
							<label for="gunbun">관리자</label> 
							<input type="checkbox" id="gunbun" name="gunbun" />
						</div> -->
	      	</form>
	      </div>
				<!-- 카카오(우편번호 검색)제공 라이브러리 추가 {2023-12-29} -->
				<script>
					// 왜 하필 여기에 배포했나?? - head에 위치 - 호이스팅 이슈
					// 단 - DOM 읽혀진 이후에만 접근이 가능하다 - undefined - 배포위치 고려해본다 - 기준(커리어)
					const openZipcode = () => {
					new daum.Postcode({ // Postcode객체를 생성하기 - 생성하자마자 내부에 구현하기가 전개되고 있다.
						oncomplete: function(data) { // 완료되었을 때 - 요청에 대한 응답이 완료되었을 때 이벤트 처리
							let addr = ''; 
							if (data.userSelectedType === 'R') { 
								addr = data.roadAddress;//도로명
							} else { 
								addr = data.jibunAddress;//지번
							}
							console.log(data);
							console.log(addr);
							//console.log(post.postNum);
							//setPost({...post, zipcode:data.zonecode, addr:addr}) ;

							// document.getElementById("mem_zipcode").value = data.zonecode; // 우편번호
							// document.getElementById("mem_address").value = addr; // 주소
							document.querySelector("#zipcode").value = data.zonecode; // 우편번호
							document.querySelector("#address").value = addr; // 주소
							
							//document.getElementById("postDetail").focus();
						}
					}).open();
				}
				</script>
				<!-- Modal footer -->
				<div class="modal-footer">  
					<input type="button" class="btn btn-warning" data-bs-dismiss="modal" value="저장" onclick="memberInsert()">
					<input type="button" class="btn btn-danger" data-bs-dismiss="modal" value="닫기">
				</div>
				<!-- 카카오(우편번호 검색)제공 라이브러리 추가 {2023-12-29} -->
	      <!-- Modal footer -->
				<!-- <div class="modal-footer">  Get 방식
					<input type="button" class="btn btn-warning" data-bs-dismiss="modal" onclick="memberInsert()"  value="저장">
					<input type="button" class="btn btn-danger" data-bs-dismiss="modal" value="닫기">
				</div> -->
	    </div>
	  </div>
	</div>
    <!-- ========================== [[ 회원가입 Modal ]] ========================== -->				
</body>
</html>