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

#table {
	border: 1px solid;
	border-collapse: collapse;
	padding: 10px;
	margin: auto;
	top: 100px;
	left: 150px;
	width: 400px;
	text-align: center;
}

#member {
	padding: 10px;
	z-index: 1;
	left: 20px;
}

#storemember {
	padding: 10px;
	z-index: 2;
}

#memberid, #memberpw, #storeid, #storepw {
	width: 350px;
	height: 50px;
}

#thOne, #thTwo {
	border-style: none;
	width: 160px;
	height: 50px;
	background-color: white;
	background-repeat: no-repeat;
}

#memberlogin, #storelogin {
	color: white;
	border-style: none;
	background-color: coral;
	width: 350px;
	height: 50px;
	margin: 6px;
}

.joinPage {
	border-style: solid;
	background-color: white;
}

input.error, textarea.error {
	border: 1px solid red;
}

label.error {
	display: block;
	color: red;
	font-size: 10px
}
</style>
</head>
<body>
<form action="login" name="login" id="login"
			method="post">
	<div id="table">
		<input type="button" id="thOne" value="일반회원 로그인"> <input
			type="button" id="thTwo" value="업체회원 로그인">
		<div id="member">
			<legend>일반회원 로그인</legend>
			<br> <input id="memberid" name="memberid" type="text"
				placeholder="일반회원 아이디" required="required"/><br /> <input id="memberpw"
				name="pw1" type="password" placeholder="일반회원 비밀번호" required="required"/><br /> <input
				id="memberlogin" type="submit" style="margin: 5px" value="일반 로그인" /><br />
			<hr>
			<input class="joinPage" type="button" value="회원가입">
		</div>	 
		<div id="storemember">
			<legend>업체회원 로그인</legend>
			<br> <input id="storeid" name="storeid" type="text"
				placeholder="업체회원 아이디" required="required"/><br /> <input id="storepw" name="pw2"
				type="password" placeholder="업체회원 비밀번호" required="required"/><br /> <input
				id="storelogin" type="submit" style="margin: 5px" value="업체 로그인" /><br />
			<hr>
			<input class="joinPage" type="button" value="회원가입">
		</div>
	</div>
	</form>
</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript"	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>
<script>
$(function() {
	$("#storemember").hide();
    
    $("#login").validate({
		rules : {
			memberid : {
				required : true
			},
			memberpw : {
				required : true
			},
			storeid : {
				required : true
			},
			storepw : {
				required : true
			}
		},
		messages : {
			memberid : {
				required : "아이디를 입력해주세요"
			},
			memberpw : {
				required : "비밀번호를 입력해주세요"
			},
			storeid : {
				required : "아이디를 입력해주세요"
			},
			storepw : {
				required : "비밀번호를 입력해주세요"
			}
		}

	});
});
	

	$("#thOne").click(function() {
		$("#member").show();
		$("#storemember").hide();
		$("#thTwo").css("color", "black");
		$("#thOne").css("color", "black");
	});

	$("#thTwo").click(function() {
		$("#storemember").show();
		$("#member").hide();
		$("#thTwo").css("color", "red");
	});


	$(".joinPage").click(function() {
		location.href = "joinkind.jsp";
	});
</script>
</html>