<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    
 b {
 	font-size:10px;
	color: coral;
}

#updatebtn, #resetbtn{
	border-style: solid;
	background-color: coral;
	color: white;
	width: 150px;
	height: 50px;
	margin: 10px;
	font-size: 16px;
}

#backbtn {
	border-style: solid;
	background-color: white;
	width: 110px;
	margin: 10px;
	font-size: 14px;
}


input.error, textarea.error{
  border:1px dashed blue;
}
label.error{
  display:inline;
  color:blue;
  font-size: 10px
}
    
.filebox label {
  margin: 1%;
    
  display: inline-block;
  padding: .5em .75em;
  color: #fff;
  font-size: inherit;
  line-height: normal;
  vertical-align: middle;
  background-color: coral;
  cursor: pointer;
  border: 1px solid #CA3D13;
  border-radius: .25em;
  -webkit-transition: background-color 0.2s;
  transition: background-color 0.2s;
}

.filebox label:hover {
  background-color: LightSalmon;
}

.filebox label:active {
  background-color: #CA3D13;
}

.filebox input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
}    
</style>
</head>
<body style="text-align: center">

	<div
		style="border: 1px solid black; margin: auto; padding: 3%; width: 500px;">

		<h1>업체 정보 수정</h1>
		${msg}
		<form action="updatestore" name="updatestore" id="updatestore"
			method="post">
			<div style="border:1px solid coral; margin: 20px; padding: 7%">
				<b>아이디:</b> <input type="hidden" name="id" id="id" value="${id}"/>${id}
					<br/><b>비밀번호:</b>
				<input type="password" id="pw" name="pw" placeholder="비밀번호 입력(5~20자)"
					style="margin: 1%" required="required" /><br /> <b>비밀번호 확인:</b> <input
					type="password" id="confirm_pw" name="confirm_pw"
					placeholder="비밀번호 확인" style="margin: 1%" /><br />
				<b>전화번호:</b> <input type="text" id="phone" name="phone"
					placeholder="번호 입력(-생략해주세요)" style="margin: 1%" /><br />
                

			</div>

			<input type="submit" value="정보수정" id="updatebtn"> <input
				type="reset" id="resetbtn" value="다시작성"><br/> <input
				type="button" value="돌아가기" id="backbtn" onclick="goBack()" />

		</form>

	</div>
</body>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.1.1.min.js"></script>

<script>
	$(function() {
		$("#updatestore").validate({
			rules : {
				
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
				},

			//email: true	
			},//end rules
			messages : {
				
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
				},
			}//end messages

		});//end validate

	});//end function

	function goBack() {
		window.history.back();
	}
	
	

</script>
</html>