<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#reqmsg{
    background: transparent;
    border-style: hidden;
    color: white;

}
#reqbtn{
background-color: coral;
    border: 2px solid coral;
    border-radius: 5px;
    color: white;

}

#request {
	position: fixed;
    top: 300px;
    left: 50%;
    width: 400px;
    height: 30px;
    padding: 20px;
    margin-left: -200px;
    border: 5px solid coral;
    z-index: 101;
    display: none;
    overflow: auto;
    /* background: white; */
    border-radius: 10px;
}
#profile{
    position: fixed;
    top: 80px;
    left: 50%;
    width: 500px;
    height: 380px;
    padding: 20px;
    margin-left: -200px;
    border: 1px solid white;
    z-index: 102;
    display: none;
    overflow: auto;
    /* background: white; */

}
#pro_photo{
	border: 2px dashed coral;
	width: 360px;
	height: 370px;
	float: left;
}
#pro_content{
	width: 120px;
	height: 370px;
	float: right;
	color : coral;
	font-size: 12px;
	font-weight: bold;
}
#nickname,#phone,#oneline{
	color: white;
    font-size: 10px;
}

#request-shadow,#profile-shadow {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: #000;
	filter: alpha(opacity = 75);
	-moz-opacity: 0.75;
	-khtml-opacity: 0.75;
	opacity: 0.75;
	z-index: 100;
	display: none;
}

#request.open,#profile.open {
	display: block;
	color: #848484;
}

#htmltable {
	border: 1px solid coral;
    width: 870px;
    margin: 10px;
    padding: 10px;
}
.cancelbtn{
	background-color: #848484;
    color: white;
    border-style: none;
    font-size: 17px;
    font-weight: bold;
    border-radius: 5px;
}
.cancelbtn:hover{
	background-color:#acacac;
}
.profileId:hover{
	color: coral;
}
.requestbtn{
    background-color: coral;
    color: white;
    border-style: none;
    font-size: 17px;
    font-weight: bold;
    border-radius: 5px;
}
.requestbtn:hover{
	background-color: lightsalmon; 
}
.showkind{
background: white;
    color: coral;
    border-style: none;
    font-size: 17px;
    font-weight: bold;
    border-radius: 5px;

}




#exit{
    padding: 5px;
    margin: 20px;
    color: coral;
    font-size: large;
    border-radius: 10px;
    border-style: double;
    background-color: white;
}
#exit:hover{
	color:black;
	border-color: coral;
}


</style>
</head>
<body>
<h2>${st_name} 선택 해주신분들의 목록입니다.</h2><br>
${choiceListHtml}

<input id="exit" type="button" value="현재 식당 선택 취소하기"/>

<div id='request'>
<input type='text' id='reqmsg' size='40' placeholder='(선택)요청메세지를 입력해주세요'/>
<input type="button" id=reqbtn value='보내기'/> 
</div>

<div id='request-shadow'>
</div>

<div id='profile'>
<div id='pro_photo'></div>
<div id='pro_content'></div>

</div>
<div id='profile-shadow'>
</div>

</body>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>


$('#request-shadow').click(function(){
	$('#request').removeClass();
	$('#request-shadow').hide();
	
});//click end
$('#profile-shadow').click(function(){
	$('#profile').removeClass();
	$('#profile-shadow').hide();
	
});//click end
var targetReqBtn="";
var targetCclBtn="";
var targetReceiveId="";




for(var i=0;i<$("tr").length;i++){ 
	$(".requestbtn").eq(i).click(function(){
		$("#request").addClass("open");
		$('#request').show();
		$('#request-shadow').show();
		targetReqBtn = $(this).parents().filter(".choicemember").children().eq(5).children();
		targetCclBtn = $(this).parents().filter(".choicemember").children().eq(6).children();
		console.log(targetReqBtn);	
		console.log(targetCclBtn);
		
		targetReceiveId = $(this).parents().filter(".choicemember").children().eq(3).children();
		
		
		
	});//end .requestbtn click
	
	
	
}//end for

$('#reqbtn').click(function(){
	$('#request').removeClass();
	$('#request-shadow').hide();
	$.ajax({
	url:'sendrequest',
	data:{receiveid:targetReceiveId.val(),reqmsg:$('#reqmsg').val()},
	type:'post',
	dataType:'json',
	success:(function(data){
		console.log(data);
		console.log(targetReqBtn);
		console.log(targetCclBtn);
		alert(data);
		targetReqBtn.hide();
		targetCclBtn.show();
	}),
	error:(function(err){
		console.log(err);
	})
	});//end ajax 
})//end reqbtn click



for(var i=0;i<$("tr").length;i++){ 
	$(".cancelbtn").eq(i).click(function(){
		//0번으로 보낸 요청메세지5번상태로 바꾸기 update
		
	 	targetReqBtn = $(this).parents().filter(".choicemember").children().eq(5).children();
		targetCclBtn = $(this).parents().filter(".choicemember").children().eq(6).children();
		console.log(targetReqBtn);
		console.log(targetCclBtn);
		$.ajax({
			url:'requestcancel',
			data:{targetid:$('.c_id').val()},
			type:'post',
			dataType:'json',
			success:(function(data){
				console.log(data);
				console.log(targetReqBtn);
				console.log(targetCclBtn);
				targetReqBtn.show();
				targetCclBtn.hide();
								
			}),
			error:(function(err){
				console.log(err);
			})
		});//end ajax 
					
	});//end .cancelbtn click
}//end for2

$(function() {
	var kind='${kind}';
	if(kind=='S'){
		$("#exit").attr("type","hidden");
		$(".requestbtn").attr("type","hidden");
		$(".cancelbtn").attr("type","hidden");
		$(".showkind").attr("type","button");
	}	
	
});
	
$("#exit").click(function() {
	location.href="cancelstorechoice";	
});


//자기자신 안보이게하기
var sId = '${sessionScope.id}'
for(var i=0;i<$("tr").length;i++){
	var sIdFromList = $('.choicemember').eq(i).children().eq(3).children().val();
	if(sId == sIdFromList){
		$('.choicemember').eq(i).prop("hidden",true);		
	}	
}

//프로필보기
for(var i=0;i<$("tr").length;i++){
	//console.log("999",$('.choicemember').eq(i).children());
	$('.choicemember').eq(i).children().eq(0).click(function(){//아이디 클릭 시
		$("#profile").addClass("open");
		$('#profile').show();
		$('#profile-shadow').show();
		$.ajax({
			data:{profileid:$(this).html()},
			url:'getprofile',
			type:'post',
			dataType:'json',
			success:(function(profileInfo){
				console.log(profileInfo);
				if(profileInfo.profilephoto==null || profileInfo.profilephoto==""){
					$('#pro_photo').html("<img src='upload/noProfilePhoto.jpg'/>");
				}else{
					$('#pro_photo').html("<img src='"+profileInfo.profilephoto+"'/>");
				}				
				var str = "<br><br><br><br><br><br><br><br><br><br><br><br>"
						+ "<br>NICKNAME : <span id='nickname'>"+profileInfo.nickname+"</span>"
						+ "<br>NUMBER : <span id='phone'>"+profileInfo.phone+"</span><br>";
				if(profileInfo.oneline==null || profileInfo.oneline==""){
					str+="<br><span id='oneline'>한줄소개가 없습니다</span>";
				}else{
					str+="<br>COMMENT : <span id='oneline'>"+profileInfo.oneline+"</span>";
				}			
				$('#pro_content').html(str);
			}),
			error:(function(err){
				console.log(err);
			})
		});
		
	});
}

	
		
		
</script>
</html>