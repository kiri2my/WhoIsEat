<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
		body {
	text-align: center;
}

.table {
	border: 1px solid coral;
	background-color: coral;
	border-collapse: collapse;
	padding: 6px;
	margin: auto;
	top: 30px;
	left: 150px;
	width: 600px;
	text-align: center;
	
}

.btn{
	border-style: none;
	width: 180px;
	height: 20px;
	background-color: coral;
	background-repeat: no-repeat;
	color: white;
	font-size: 13px;
	
}
.btn:hover{
	text-shadow: 0px 0px 4px white;
	text-decoration: underline;
	
}
.span {
	border-style: none;
	width: 200px;
	height: 30px;
	background-repeat: no-repeat;
	color: white;
	margin: 5px;
}
	
</style>
</head>
<body>
	<div class="table">
	</div>
	<!-- <div class="table">
		<input type="button" class="thOne" id="updatestore" value="업체정보 수정"> 
		<input	type="button" class="thTwo" id="updatestoredetail" value="상세페이지 수정">
		<input	type="button" class="thThree" value="로그아웃">
	</div> -->
</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script>
$(function() {
	$.ajax({
	    url: 'menukind', 
		dataType:'json',
		success:(function(kind){
			console.log(kind);
			//console.log($('#header'));
			var data;
			if(kind =='N'){
				data="<span id='thOne' class='span'><button class='btn' id='reservetime' >밥친구 찾기</button></span>"
				+"<span id='thTwo' class='span'><button class='btn' id='updatemember' >내 정보 수정</button></span>"
				+"<span id='thThree' class='span'><button class='btn' id='logout'>로그아웃</button></span>";
				$(".table").html(data);
				
        	}else if(kind =='S'){
        		data="<span id='thOne2' class='span'><button class='btn' id='updatestore' >업체정보 수정</button></span>"
				+"<span id='thTwo2' class='span'><button class='btn' id='updatestoredetail' >상세페이지 수정</button></span>"
				+"<span id='thThree2' class='span'><button class='btn' id='logout'>로그아웃</button></span>";
        		$(".table").html(data);
				
        	}
			
			
			
			$("#thOne").on('click',$("#reservetime"),function() {
				location.href="toreservetime";				
			});
			
			$("#thOne2").on('click',$("#updatestore"),function() {
				location.href="updatestore.jsp";				
			});
			
			$("#thTwo").on('click',$("#updatemember"),function() {
				location.href="updatemember.jsp";			
			});
			
			$("#thTwo2").on('click',$("#updatestoredetail"),function() {
				location.href="updatestoredetail.jsp";			
			});
			
			$("#thThree").on('click',$("#logout"),function() {
				location.href="logout";		
			});
			
			$("#thThree2").on('click',$("#logout"),function() {
				location.href="logout";		
			});
			
			
			
		}),
		error:(function(error){
			console.log(error);
		})
	});//ajax menukind end

});//function end

	
	
	
	
</script>
</html>