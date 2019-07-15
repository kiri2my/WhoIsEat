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
        #table{
            width: 450px;
            margin: auto;
        }
        #join1, #join2{
            border: 1px solid darkgray;
            margin: 10px;
            padding: 50px;
            width: 500px;
            height: 200px;
        }
        
        input{
            margin: 20px;
            border-style: none;
            background-color: coral;
            width: 200px;
            height: 50px;
            color: white;
        }
        
</style>
</head>
<body>
	<div id="table">

       <div id="join1">
          <img src="pic/join1.JPG" style="width: 60px;" />
           <p id="p1" style="font-size: 20px"><b>일반 회원가입</b></p>
           <input id="memberjoin" type="button" value="회원가입">
       </div>
          
        <div id="join2">
           <img src="pic/join2.JPG" style="width: 50px;" />
          <p id="p2" style="font-size: 20px"><b>업체 회원가입</b></p> 
              <input id="storejoin" type="button" value="회원가입">      
       </div> 
    </div>

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$("#memberjoin").click(function(){
    var windowOpen=window.open("about:blank");
    windowOpen.location.href="memberjoin.jsp";
});

$("#storejoin").click(function(){
	var windowOpen=window.open("about:blank");
    windowOpen.location.href="storejoin.jsp";
});

$("#join1").hover(function() {
    $(this).css("border-color", "red");
    $("#p1").css("color", "red");
},function() {
    $(this).css("border-color", "darkgray");
    $("#p1").css("color", "black");
});

$("#join2").hover(function() {
    $(this).css("border-color", "red");
    $("#p2").css("color", "red");
},function() {
    $(this).css("border-color", "darkgray");
    $("#p2").css("color", "black");
});
</script>
</html>