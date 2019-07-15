<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
h1, b {
	color: coral;
}

#okbtn, #resetbtn, #backbtn {
	border-style: none;
	background-color: coral;
	color: white;
	width: 150px;
	height: 50px;
}

#duplbtn1,#duplbtn2 {
	border-style: none;
	background-color: coral;
	color: white;
	font-size: 11px;
	width: 60px;
	height: 25px;
}

input.error, textarea.error{
  border:1px dashed blue;
}
label.error{
  display:inline;
  color:blue;
  font-size: 10px
}
</style>
</head>

<body style="text-align: center">

	<div
		style="border: 10px solid coral; margin: auto; padding: 1%; width: 500px;">

		<h1>회원가입</h1>

		<form action="storejoin" name="storejoin" id="storejoin"
			method="post">
			<div style="margin: 1px; padding: 1%">
				<b>사업자 등록번호:</b> <input type="text" id="storenum" name="storenum"
					placeholder="('-'생략,10자)" style="margin: 1%" required/>
					<div id="doublecheckMsg1" style="display:inline; color:blue; font-size: 10px"></div>
					<input type="button" value="중복확인" id="duplbtn1"><br /> 
				<b>아이디:</b> <input type="text" id="id" name="id"
					placeholder="아이디 입력(5~20자)" style="margin: 1%" required/>
					<div id="doublecheckMsg2" style="display:inline; color:blue; font-size: 10px"></div>
					<input type="button" value="중복확인" id="duplbtn2"><br /> 
					<b>비밀번호:</b>
				<input type="password" id="pw" name="pw" placeholder="비밀번호 입력(5~20자)"
					style="margin: 1%" required="required" /><br /> <b>비밀번호 확인:</b> <input
					type="password" id="confirm_pw" name="confirm_pw"
					placeholder="비밀번호 확인" style="margin: 1%" /><br />
				<b>전화번호:</b> <input type="text" id="phone" name="phone"
					placeholder="번호 입력(-생략해주세요)" style="margin: 1%" /><br />
					
				<br />

			</div>
			${msg}
			<input type="submit" value="식당등록" id="okbtn"> <input
				type="reset" id="resetbtn" value="다시작성"> <input
				type="button" value="돌아가기" id="backbtn" onclick="goBack()" />

		</form>

	</div>

</body>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js">
	
</script>
<script type="text/javascript"
	src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>



<script>
	$(function() {
		$("#storejoin").validate({
			rules : {
				storenum : {
                    required : true,
                    length : 10,
                    digits : true
				},
				id : {
					required : true,
					minlength : 5,
					maxlength : 20
				},
				pw : {
					required : true,
					minlength : 5,
					maxlength : 20
				},
				confirm_pw : {
					required : true,
					minlength : 5,
					maxlength : 20,
					equalTo : "#pw"
				},
				phone : {
					required : true,
                    digits : true
				}
				

			//email: true	
			},//end rules
			messages : {
				storenum : {
                    required : "사업자 번호를 입력해주세요.",
                    length : "사업자번호 10자리를 입력해주세요.",
                    digits : "숫자입력만 가능합니다."
				},
				id : {
					required : "아이디를 입력해 주세요.",
					minlength : "아이디는 5자 이상이어야 합니다.",
					maxlength : "아이디는 최대 20자까지 가능합니다."
				},
				pw : {
					required : "암호를 입력해 주세요.",
					minlength : "암호는 5자 이상이어야 합니다.",
					maxlength : "암호는 최대 20자까지 가능합니다."
				},
				confirm_pw : {
					required : "암호를 한 번 더 입력해 주세요.",
					minlength : "암호는 8자 이상이어야 합니다.",
					maxlength : "암호는 최대 20자까지 가능합니다.",
					equalTo : "암호가 일치하지 않습니다."
				},
                phone : {
					required : "번호를 입력해 주세요.",
                    digits : "숫자입력만 가능합니다."
				}
				
			}//end messages

		});//end validate
		

	});//end function

	function goBack() {
		window.history.back();

	}
	
	$("#duplbtn1").click(function() {
		$.ajax({
			url : 'doublecheck', 
			type : 'get',
			data : {
				id : $("#storenum").val()
			}, 
			dataType : 'json',
			success : (function(data) {
				$("#doublecheckMsg1").html(data);
			}),
			error : (function(err) {
				console.log(err);
			})
		})
	});
	
	$("#duplbtn2").click(function() {
		$.ajax({
			url : 'doublecheck', 
			type : 'get',
			data : {
				id : $("#id").val()
			}, 
			dataType : 'json',
			success : (function(data) {
				$("#doublecheckMsg2").html(data);
			}),
			error : (function(err) {
				console.log(err);
			})
		})
	});
	
	
</script>

</html>
