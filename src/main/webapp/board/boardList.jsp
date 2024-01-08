<%@ page language="java"	contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map, java.util.ArrayList" %>        
<%@ page import="com.util.BSPageBar" %>        
<%
	//스크립틀릿 - 지변, 메소드선언 불가함, 인스턴스화 가능함
  int size = 0;//전체 레코드 수
  List<Map<String,Object>> nList = (List)request.getAttribute("nList");

  if(nList !=null){
    size = nList.size();
  }
  //out.print(size);
  // 한 페이지에 몇개씩 뿌릴거야??
      int numPerPage = 3;
      int nowPage = 0;
      if(request.getParameter("nowPage") != null) {
      nowPage = Integer.parseInt(request.getParameter("nowPage"));
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<%@include file="/common/bootstrap_common.jsp"%>
<%@include file="/common/quill_common.jsp"%>
<link rel="stylesheet" href="/css/board.css">
<script type="text/javascript">
  const boardDetail = (b_no) => {
    console.log(b_no);
    location.href ="/board/boardDetail?b_no="+b_no;
  } 
	const boardInsert = () => {
		document.querySelector("#f_board").submit();
	}

	// <!-- ================================ [[ quill editor ]] ================================ -->
	//함수는 기본적으로 호이스팅이 일어난다
	const  selectLocalImage = (event) =>  {
		  console.log('selectLocalImage');
		  //e.preventDefault();이벤트 버블링 방어 코드 작성 - submit이슈, <button type=submit|button>
		  //quill에디터에서 이미지를 클릭했을 때 실제 화면에서는 <input type=file>생성해 주자
		  //이미지를 선택하면 선택하자마자 백엔드에 요청을 post방식으로 넘긴다 - 8000번 서버의 pds폴더에 선택한 이미지를 업로드 처리함
		  //파일을 (바이너리코드) 전송할땐 무조건 post방식으로 해야 함
		  //<input type=file/>
		    const fileInput = document.createElement('input');//<input> - DOM API의 createElement를 사용하여 태그 생성하는 코드
		    fileInput.setAttribute('type', 'file');//<input type=file>
		    console.log("input.type " + fileInput.type);
		    //이미지 파일만 선택가능하도록 제한
		    fileInput.setAttribute("accept", "image/*");//* -> image/png , image/gif, image/jpg
		    fileInput.setAttribute("name", "image");//req.getParameter("image"); -> <input name="image"/>
			//사람이 눌러야 할 이벤트를  코드에서 대신 클릭해줌
		    fileInput.click();

		    fileInput.addEventListener("change", function () {  // change 이벤트로 input 값이 바뀌면 실행
		        const formData = new FormData();
		        const file = fileInput.files[0];//여러개 파일 처리가 가능함
		        formData.append('image', file);
// 글쓰기(insert문이 처리되기 전에)가 처리되기 전에 먼저 미리보기 처리를 해야 한다
		        $.ajax({//<form method=post enctype=multipart/form-data/> 이럴경우 req.getParameter사용이 불가함 -> cos.jar
		            type: 'post',
		            enctype: 'multipart/form-data',
		            url: '/board/imageUpload', //백엔드에서 요청을 처리 한 후 파일명을 반환받기 - @RestController사용함
		            data: formData,
		            processData: false,
		            contentType: false,
		            success: function (response) {
						console.log('avatar.png'+response);//avartar.png
						//imageUpload요청을 처리하면 리턴값으로 아바타.png가 온다
						//demo서버에서 pds폴더에 업로드(56번URL이 요청)되어 있는 이미지 정보를 읽어온다
						//서버에 있는 이미지를 읽어오는거니까 get방식도 괜찮아 
						//request.getParameter("imageName"), @RequestParam("imageName") String imageName
						const url = "/board/imageGet?imageName="+response;//
						//선택된 이미지가 들어갈 위치 정보를 쥔다
						const range = quill.getSelection().index;
						quill.setSelection(range, 1);
						quill.clipboard.dangerouslyPasteHTML(range,
								'<img src='+url+' style="width:100%;height: auto;" alt="image" />');
		            },
		            error: function (err) {
		                console.log(err);
		            }
		        });//////////////////// end of ajax

		    });////////////////////// end of onchange 이벤트 핸들러
		}////////////////////////// end of selectLocalImage	  	
	// <!-- ================================ [[ quill editor ]] ================================ -->

</script>
</head>
<body>
	<!-- header start -->
	<%@include file="/include/gym_header.jsp"%>
	<!-- header end    -->
	<!-- body start    -->
	<div class="container">
		<div class="page-header">
			<h2>게시판 <small>게시글목록</small></h2>
			<hr />
		</div>
		<!-- 검색기 시작 -->
		<div class="row">
			<div class="col-3">
		    	<select id="gubun" class="form-select" aria-label="분류선택">
		      		<option value="none">분류선택</option>
		      		<option value="b_title">제목</option>
		      		<option value="b_writer">작성자</option>
		      		<option value="b_content">내용</option>
		    	</select>			
		  	</div>
			<div class="col-6">
		 		<input type="text" id="keyword" class="form-control" placeholder="검색어를 입력하세요" 
		           aria-label="검색어를 입력하세요" aria-describedby="btn_search" onkeyup="searchEnter()"/>
			</div>
			<div class="col-3">
		 		<button id="btn_search" class="btn btn-danger" onClick="boardSearch()">검색</button>
		 	</div>
		</div>		
		<!-- 검색기 끝 -->
		
		<!-- 회원목록 시작 -->
		<div class='board-list'>
			<table class="table table-hover">
		    	<thead>
		      		<tr>
		        		<th width="10%">#</th>
		        		<th width="40%">제목</th>
		        		<th width="20%">첨부파일</th>
		        		<th width="15%">작성자</th>
		        		<th width="15%">조회수</th>
		      		</tr>
		    	</thead>
		    	<tbody>
<%
	// for(int i=0;i<size;i++){
	for(int i=nowPage*numPerPage;i<(nowPage*numPerPage)+numPerPage;i++){
		if(size == i) break; // NullPointerException 방어
		Map<String,Object> rmap = nList.get(i);
%>					
					<tr>
						<td><%=rmap.get("B_NO") %></td>
						<td>
							<a href="javascript:boardDetail('<%=rmap.get("B_NO") %>')">
								<%=rmap.get("B_TITLE") %>
							</a>
              <td>
                <%=rmap.get("B_FILE") %>
              </td>
						</td>
						<td>
              <%=rmap.get("B_WRITER") %>
            </td>
						<td>
              <%=rmap.get("B_HIT") %>
            </td>
					</tr>					
<%
	}
%>
		    	</tbody>
		    	</tbody>
			</table> 
<hr />  
<!-- [[ Bootstrap 페이징 처리  구간  ]] -->
	<div style="display:flex;justify-content:center;">
	<ul class="pagination">

	</ul>
	</div>
<!-- [[ Bootstrap 페이징 처리  구간  ]] -->		  
		  	<div class='board-footer'>
		    	<button class="btn btn-warning" onclick="boardList()">
		      		전체조회
		    	</button>&nbsp; 
			    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#boardForm">
			    글쓰기
			    </button>
		    </div>
		</div>		
		<!-- 회원목록   끝  -->		
		
	</div>
	<!-- body end       -->
	<!-- footer start -->
	<%@include file="/include/gym_footer.jsp"%>
	<!-- footer end    -->	
	<!-- ========================== [[ 게시판 Modal ]] ========================== -->
	<div class="modal" id="boardForm">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">게시판</h4>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>
	      <!-- Modal body -->
	      <div class="modal-body">
	      	<!-- <form id="f_board" method="get" action="./boardInsert"> -->
	      	<form id="f_board" method="post" enctype="multipart/form-data" action="./boardInsert">
	      	  <input type="hidden" id="b_content" name="b_content">
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="b_title" name="b_title" placeholder="Enter 제목" />
	            <label for="b_title">제목</label>
	          </div>	      	
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="b_writer" name="b_writer" placeholder="Enter 작성자" />
	            <label for="b_writer">작성자</label>
	          </div>	      	
	          <!-- <div class="form-floating mb-3 mt-3"  id="editor"> -->        
	          <div style="height: 250px;" id="editor">    
						</div>

<!-- ================================ [[ quill editor ]] ================================ -->
		<script>
			const toolbarOptions = [
		  ['bold', 'italic', 'underline'],        // toggled buttons
		  ['blockquote', 'code-block'],
		  [{ 'list': 'ordered'}, { 'list': 'bullet' }],
		  [{ 'indent': '-1'}, { 'indent': '+1' }],          // outdent/indent
		  [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
		  [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
		  [{ 'font': [] }],
		  [{ 'align': [] }],
		  ['link', 'image']
		];				
	  const quill = new Quill('#editor', {
		  modules: { toolbar: toolbarOptions },				    
		  theme: 'snow',
		  placeholder: '글 작성하기',
	  });

		//html 가져오기
		const html = quill.root.innerHTML;//<div>123</div>
		console.log(html);
		//에디터에 변경(텍스트, 이미지, 첨부파일 처리)이 발생하면 그때 이벤트처리
		//새글쓰기에 필요한 api와 수정하기에 필요한 api가 다르다
		quill.on('text-change', (delta, oldDelta, source) => {
			  console.log('글자가 입력될때 마다 호출');
			  console.log(quill.root.innerHTML);
			  //에디터에 들어온 내용이 div에 삽입되어야 함
			  //b_content안에 들어갈 정보이다 -> pmap.put("b_content","<p><br>머시기</p>")
			  //innerHTML 을 사용하면 태그가 포함된 문자열이나 이미지 태그 정보가 포함됨<img src=아바타.png>
			  document.querySelector("#editor").value = quill.root.innerHTML;//
			  //insert here - textarea대신에 사용자가 작성한 내용을 서버에 전송해야 하니까...
			  document.querySelector("#b_content").value = quill.root.innerHTML;//
			  //console.log(source);//user
			  //console.log(delta);
			  //console.log(oldDelta);
		}); ////////////// end of onchage - 텍스트 내용이 변경되었을 때 발동
		quill.getModule('toolbar').addHandler('image',  () => {
			console.log('image가 변경되었을때');
			//1)플젝 바라보는 pds폴더에 선택한 이미지를 업로드 한다
			//2)<img src="이미지경로와 이름">
			selectLocalImage();
		})
		</script>
<!-- ================================ [[ quill editor ]] ================================ -->

		      <div class="input-group mb-3">
				  <input type="file" class="form-control" id="b_file" name="b_file">
				  <label class="input-group-text" for="b_file">Upload</label>
			  </div>      	
	      	</form>
	      </div>	
	      <div class="modal-footer">
	        <input type="button" class="btn btn-warning" data-bs-dismiss="modal" onclick="boardInsert()"  value="저장">
	        <input type="button" class="btn btn-danger" data-bs-dismiss="modal" value="닫기">
	      </div>
	
	    </div>
	  </div>
	</div>
    <!-- ========================== [[ 게시판 Modal ]] ========================== -->				
</body>
</html>