<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#main3{
    border: 1px solid white;
    width: 890px;
    height: 300px;
    float: left;
    margin: 3px;
    text-align: center;
    padding-top: 80px;
}

#locationbox{
	border-radius:10px;
	border: 3px solid coral;
	width: 470px;
	height: 25px;
	margin: auto;
	padding: 3px;
}

input{
	width: 70px;
}
#si_do,#gu,#dong{
	border-style: none;
}


#locationgo{
	color: white;
	border-style: none;
	background-color: coral;
	border-radius:5px;
	height: 25px;
	
}
#locationgo:hover{
	background-color: lightsalmon;
}

#button{
	padding : 5px;
	margin : 20px;
	color: white;
	border-style:none;
	background-color: coral;
	width: 100px;
	border-radius:5px;
}
#button:hover{
	background-color: lightsalmon;
}

</style>
</head>
<body>

<div id="main3">
<h1>어디서 드실건가요?</h1>
	<form action="showstorelist" name="showstorelist" method="get">
	<div id=locationbox>	
      시/도 <input type="text" id="si_do" name="si_do"/> 
      구 <input type="text" id="gu" name="gu"/>  	
      동 <input type="text" id="dong" name="dong"/> &nbsp;	
   	 <input type="button" id="locationgo" value="주소검색" /> 
	</div>
	<button id="button">확인</button>
	</form>
	
</div>

</body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	$("#locationgo").click(function(){
		new daum.Postcode({
		   	 oncomplete: function(data) {
		   		 
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                	alert("지번 주소를 선택해주세요");
                }else{
                	$("#si_do").val(data.sido);
                    $("#gu").val(data.sigungu);
                    $("#dong").val(data.bname);
                	
                }
                
		   	 }
			}).open();
		});
	
	

	
</script>
</html>