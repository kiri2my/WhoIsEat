<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#main2{
	border: 1px solid white;
    width: 890px;
    height: 300px;
    float: left;
    margin: 3px;
    text-align: center;
    padding-top: 80px;
}

#timebox{
	border-radius:10px;
	border: 3px solid coral;
	width: 400px;
	height: 25px;
	margin: auto;
	padding: 3px;
	
}


#hour,#minute{
	width: 40px;
    height: 20px;
    border-style: none;
    font-size: 21px;

}
select{
	border-style: none;

}

#timego{
    color: white;
    border: 2px solid coral;
    background-color: coral;
    height: 25px;
    border-radius: 5px;
    padding: 2px 8px 2px 7px;
}
#timego:hover{
    background-color: lightsalmon;
}

</style>
</head>
<body>
<div id="main2">
<h1>언제 드실건가요?</h1>
	<div id=timebox>
	<form action="reservetimetoselectlocation" method="post">
	<select name="time" size="1" >
      <option value="AM">AM</option>
      <option value="PM">PM</option>

	</select>&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="text" id="hour" name="hour"/> 시 
      <input type="text" id="minute" name="minute"/> 분(경) &nbsp;  	
   	<input type="submit" id="timego" value="확인"/> 
	</form>
	</div>
</div>
</body>
<script>

</script>
</html>