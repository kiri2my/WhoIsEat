<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    
    #full{
        
        width: 1100px;
        height: 500px;
        margin: auto;
        padding: 3px;
        
    }
    #header{
        background-color: coral;
        width: 872px;
        height: 40px;
        margin: 3px;
        overflow: hidden;
        position:relative;
        float:left;
        
        
    }
    #refresh{
    	width:27px;
    	height:40px;
		color: white;
		font-size: 9px;
		border-style: none;
		background-color: coral;
		display:block;
		margin:3px;
		
		
	}
    #main{
        border: 1px solid;
        width: 900px;
        height: 400px;
        float: left;
        margin: 3px;
        overflow: auto;
        position:relative;
    }
    #sidebar{
        border: 1px solid;
    	width: 183px;
    	height: 447px;
    	float: right;
    	margin: 3px;
    	position: relative;
    	top: -46px;
    	overflow: auto;
    	display: inline-block;
    }
    
	#wrap{
		border: 1px solid;
		
	
	}
	.smsgbox,.smsgbox2,.smsgbox3{
		padding : 5px;
		background-color: LightSalmon;
		color: white;
		font-size: 12px;
		border : 1px solid coral; 
		
	}
	.smsgbox:hover{
		background-color:coral;
	}
	.accept , .reject , .confirm{
		background-color: coral;
		color: white;
		font-size: 9px;
		border-style: none;
		
		width:50px;
		
	}
	
	#box {
	position: fixed;
	top: 300px;
	left: 50%;
	width: 400px;
	height:100px;
	padding: 7px;
	margin-left: -200px;
	border: 1px solid black;
	z-index: 101;
	display: none;
	overflow: auto;
	background-color: LightSalmon;
	}

	#box-shadow {
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

	#box.open {
	display: block;
	color: black;
	}
	#bigaccept,#bigreject{
		background-color: coral;
		color: white;
		/* font-size: 11px; */
		border-style: none;
	}
	
	#msginfo{
	font-size: 11px;
	
	}
	#m_msg{
	font-size: 16px;
	color: white;
	
	}
	.confirmaccept{
	background-color: coral;
    color: white;
    border: 3px solid coral;
    box-shadow: 1px 1px 3px 2px #0000004a;
    text-shadow: 1px 2px 2px #000000b8;
    border-radius: 7px;
    width: 70px;
	}
	.confirmreject{
	background-color: #8e8e8e;
    color: white;
    border: 3px solid #8e8e8e;
    box-shadow: 1px 1px 3px 2px #0000004a;
    text-shadow: 1px 2px 2px #000000b8;
    border-radius: 7px;
    width: 70px;
	
	
	}
	.confirmaccept:hover,.confirmreject:hover{
	color:coral;
	}
	
	
</style>
</head>
<body>
<div id='full'>
	<div id='header'>
	<jsp:include page='header.jsp'/>
	
	</div>
	<button id='refresh'>R </button>
	
	
	
	
	
	<div id='main'>
	</div>
	
	
	<div id='sidebar'>
	</div>
	
</div>

<div id='box'>
</div>

<div id='box-shadow'>
</div>





</body>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$('#box-shadow').click(function(){
	$('#box').removeClass();
	$('#box-shadow').hide();
});
var mb = '${mb}';
var sId = '${sessionScope.id}';
var kind = '${kind}';
var url = '${url}';
var storenum= '${sessionScope.storenum}';
console.log(mb);
console.log(sId);
console.log(kind);
console.log(url);
console.log(storenum);

var strList = JSON.stringify(${strList});
var none = '${none}';
var dong = '${dong}';
console.log(dong);
console.log(strList);

var choiceList = JSON.stringify(${choiceList});
var st_name = '${st_name}';
console.log(choiceList);
console.log(st_name);



if(url=='reservetime'){
	include(url,'#main');
}
if(url=='selectlocation'){
	include(url,'#main');
}
if(url=='toshowstorelist'){
	include2(url,'#main',strList,dong,none);
}
if(url=='toshowchoicelist'){
	include2(url,'#main',choiceList,st_name);	
}
if(url=='showchoicelist'){
	include(url,'#main');
}


function include(url,position){
	$.ajax({
		url:url, 
		dataType:"html", 
		success:function(fw){ 
			//console.log(data);
			$(position).html(fw);
        },
		error:function(error){
			console.log(error);
		}
		
	});//ajax end
}//include end 




function include2(url,position,list,str1,str2){
	$.ajax({
		url:url, 
		type:'post',
		data: {list:list,dong:str1,none:str2},
		dataType:"html",
		success:function(fw){ 
			//console.log(data);
			$(position).html(fw);
        },
		error:function(error){
			console.log(error);
		}
		
	});//ajax end
}//include2 end 


$(function() {
	setInterval(function(){
		sidemenukind();
	}, 1500);//end setInterval

	$('#refresh').click(function(){
		sidemenukind();
	});//end refreshClick
});//function end 




function sidemenukind(){
	if(kind=="N"){
		//ajax('membersidebar','#sidebar');
		$.ajax({
			url:'membersidebar',
			type: 'post',
			dataType : "json",
			success:(function(msgList){
				console.log(msgList);
				var str="";
				var str="<div id='wrap'>";
				for(idx in msgList){
					if(msgList[idx].m_msgst==0) {
						str+="<div class='smsgbox'>";
						str+="<span>FROM : "+msgList[idx].m_sendid+"<br>";
						str+=" TIME : "+msgList[idx].c_reservationtime+"</span><br>";
						str+="<input type='button' class='accept' value='Y'>&nbsp;&nbsp;&nbsp;";
						str+="<input type='button' class='reject' value='N'>";
						
					}else if(msgList[idx].m_msgst==3){
						str+="<div class='smsgbox2'>";
						str+="<span>업체ID : "+msgList[idx].m_sendid+"<br>";
						str+="식당 수락메세지 :<br>"+msgList[idx].m_msg+"</span><br>";
						str+="<br><input class='confirmaccept' type='button' value='EAT'>";//확인하면 6번메세지상태로
					}else if(msgList[idx].m_msgst==4){
						str+="<div class='smsgbox3'>";
						str+="<span>업체ID : "+msgList[idx].m_sendid+"<br>";
						str+="식당 거절메세지 :<br>"+msgList[idx].m_msg+"</span><br>";
						str+="<br><input class='confirmreject' type='button' value='OK'>";//확인하면 7번메세지상태로
					}
					str+="<input type='hidden' class='m_sendid' value='"+msgList[idx].m_sendid+"'>";
					str+="<input type='hidden' class='m_msg' value='"+msgList[idx].m_msg+"'>";
					str+="<input type='hidden' class='m_msgnum' value='"+msgList[idx].m_msgnum+"'>";
					str+="<input type='hidden' class='c_reservationtime' value='"+msgList[idx].c_reservationtime+"'>";
					str+="<input type='hidden' class='m_msgst' value='"+msgList[idx].m_msgst+"'>";
					str+="</div>";
				}
				str+="</div>";
				$('#sidebar').html(str);
				
				
				
				$('.confirmaccept').on('click', function(event){
					var saMsgNum = $(this).siblings().filter(".m_msgnum").val();//보낸사람:식당id,받은사람:세션id
					$.ajax({
						url:'confirmaccept',
						type:'post',
						data:{saMsgNum:saMsgNum},
						success:(function(suc){
							console.log(suc);
							location.href="toreservetime";
						}),
						error:(function(err){
							console.log(err);
						})
					});
					//$('#box').removeClass();
					//$('#box-shadow').hide();
					event.stopPropagation();
				});//end click confirmaccept
				
				$('.confirmreject').on('click', function(event){
					var srMsgNum = $(this).siblings().filter(".m_msgnum").val();//보낸사람:식당id,받은사람:세션id
					$.ajax({
						url:'confirmreject',
						type:'post',
						data:{srMsgNum:srMsgNum},
						success:(function(suc){
							console.log(suc);
						}),
						error:(function(err){
							console.log(err);
						})
					});
					location.href="toreservetime";	
					//$('#box').removeClass();
					//$('#box-shadow').hide();
					event.stopPropagation();
				});//end click confirmaccept				
				
				$('.smsgbox').on('click', function(event){
					$("#box").addClass("open");
					$('#box').show();
					$('#box-shadow').show();
										
					var str2="";
					str2+="<div id='boxcon'>";
					str2+="<span id='msginfo'>FROM : "+$(this).children().filter(".m_sendid").val()+"<br>";
					if($(this).children().filter(".m_msgst").val()==0) {
						str2+=" TIME : "+$(this).children().filter(".c_reservationtime").val()+"</span><br>";
						str2+="<span id='m_msg'>"+$(this).children().filter(".m_msg").val()+"</span><br>";
						str2+="<input type='button' class='accept' id='bigaccept' value='Y'>&nbsp;&nbsp;&nbsp;";
						str2+="<input type='button' class='reject' id='bigreject' value='N'>";
					}else {
						str2+="<br><input class='confirm' type='button' value='OK'>";
					}
					str2+="<input type='hidden' class='big_m_sendid' value='"+$(this).children().filter(".m_sendid").val()+"'>";
					str2+="<input type='hidden' class='big_m_msg' value='"+$(this).children().filter(".m_msg").val()+"'>";
					str2+="<input type='hidden' class='big_m_msgnum' value='"+$(this).children().filter(".m_msgnum").val()+"'>";
					str2+="<input type='hidden' class='big_c_reservationtime' value='"+$(this).children().filter(".c_reservationtime").val()+"'>";
					str2+="<input type='hidden' class='big_m_msgst' value='"+$(this).children().filter(".m_msgst").val()+"'>";
					str2+="</div>";
					$('#box').html(str2);					
					
					console.log("5.",$('#bigaccept'));
					console.log("6.",$('#bigreject'));
					$('#bigaccept').on('click', function(event){
						var big_requestid = $(this).siblings().filter(".big_m_sendid").val();
						var big_requesttime = $(this).siblings().filter(".big_c_reservationtime").val();
						var big_m_msgnum = $(this).siblings().filter(".big_m_msgnum").val();
						console.log("1.",big_requestid);
						console.log("2.",big_requesttime);
						console.log("3.",big_m_msgnum);
						$.ajax({
							url:'accept',
							type:'post',
							data:{requestid:big_requestid,requesttime:big_requesttime,m_msgnum:big_m_msgnum},
							success:(function(data){
								console.log(data);
							}),
							error:(function(err){
								console.log(err);
							})
						});
						$('#box').removeClass();
						$('#box-shadow').hide();
						event.stopPropagation();
					});//end click bigaccept
					$('#bigreject').on('click', function(event){
						var big_r_m_msgnum = $(this).siblings().filter(".big_m_msgnum").val();
						console.log("4.",big_r_m_msgnum);
						$.ajax({
							url:'reject',
							type:'post',
							data:{r_m_msgnum:big_r_m_msgnum},
							success:(function(data){
								console.log(data);
							}),
							error:(function(err){
								console.log(err);
							})
						});
						$('#box').removeClass();
						$('#box-shadow').hide();
						event.stopPropagation();
					});//end click bigreject					
					
					event.stopPropagation();
				});//end click 'smsgbox'
				
				$('.accept').on('click', function(event){
					
					var requestid = $(this).siblings().filter(".m_sendid").val();
					var requesttime = $(this).siblings().filter(".c_reservationtime").val();
					var m_msgnum = $(this).siblings().filter(".m_msgnum").val();
					$.ajax({
						url:'accept',
						type:'post',
						data:{requestid:requestid,requesttime:requesttime,m_msgnum:m_msgnum},
						success:(function(data){
							console.log(data);
						}),
						error:(function(err){
							console.log(err);
						})
					});
					event.stopPropagation();
				});//end click '수락'
				
				
				$('.reject').on('click', function(event){
					
					var r_m_msgnum = $(this).siblings().filter(".m_msgnum").val();
					$.ajax({
						url:'reject',
						type:'post',
						data:{r_m_msgnum:r_m_msgnum},
						success:(function(data){
							console.log(data);
						}),
						error:(function(err){
							console.log(err);
						})
					});
					event.stopPropagation();
				});//end click '거절'
									
				
			}),//end success
			error:(function(error){
				console.log(error);	
			})
			
		});//end ajax
	}//end ifkindN
	if(kind=="S"){
		//ajax('storesidebar','#sidebar');
		$.ajax({
			url:'storesidebar',
			type: 'post',
			dataType : "json",
			success:(function(resvList){
				console.log(resvList);
				var str3="<div id='wrap'>";
				for(idx in resvList){
					str3+="<div class='smsgbox'>"
					+"예약ID : "+resvList[idx].ac_acceptId+"&nbsp;&nbsp; , "+resvList[idx].ac_requestId+"</br>"
					+"예약시간 : "+resvList[idx].ac_reservationtime+"<br>"
					+"<input class='accept' type='button' value='Y'/>&nbsp;&nbsp;&nbsp;"
					+"<input class='reject' type='button' value='N'/>"
					str3+="<input type='hidden' class='ac_reservationtime' value='"+resvList[idx].ac_reservationtime+"'>";
					str3+="<input type='hidden' class='ac_st_num' value='"+resvList[idx].ac_st_num+"'>";
					str3+="<input type='hidden' class='ac_acceptid' value='"+resvList[idx].ac_acceptId+"'>";
					str3+="<input type='hidden' class='ac_requestid' value='"+resvList[idx].ac_requestId+"'>";
					str3+="<input type='hidden' class='ac_eat' value='"+resvList[idx].ac_eat+"'></div>";
				}
				str3+="</div>";
				$('#sidebar').html(str3);
												
				$('.accept').on('click', function(event){
					var resvId1 = $(this).siblings().filter(".ac_acceptid").val();
					var resvId2 = $(this).siblings().filter(".ac_requestid").val();
					var resvTime = $(this).siblings().filter(".ac_reservationtime").val();
					
					$("#box").addClass("open");
					$('#box').show();
					$('#box-shadow').show();
										
					var str4="";
					str4+="<div id='boxcon'>";
					str4+="<span id='msginfo'>손님에게 식당 대기시간을 알려주세요</span><br>";
					str4+="<input type='radio' class='waitmin' name='waitmin' value='0' checked/>즉시";
					str4+="<input type='radio' class='waitmin' name='waitmin' value='10'/>10분";
					str4+="<input type='radio' class='waitmin' name='waitmin' value='15'/>15분";
					str4+="<input type='radio' class='waitmin' name='waitmin' value='20'/>20분";
					str4+="<input type='number' id='waitmin2' placeholder='직접입력'/>분<br>";
					str4+="<br><input class='confirm' type='button' value='OK'>";
					str4+="</div>";
					$('#box').html(str4);
					
					$('.confirm').on('click', function(event){
						$.ajax({
							url:'acceptstore',
							type:'post',
							data:{resvId1:resvId1,resvId2:resvId2,resvTime:resvTime,
								waitmin:$('[name=waitmin]:checked').val(),waitmin2:$('#waitmin2').val()},
							success:(function(data){
								console.log(data);
							}),
							error:(function(err){
								console.log(err);
							})
						});
						$('#box').removeClass();
						$('#box-shadow').hide();
						event.stopPropagation();
					});//end confirm click
										
					event.stopPropagation();
				});//end click '식당수락'
				
				
				$('.reject').on('click', function(event){
					resvId1 = $(this).siblings().filter(".ac_acceptid").val();
					resvId2 = $(this).siblings().filter(".ac_requestid").val();
					resvTime = $(this).siblings().filter(".ac_reservationtime").val();
					
					$("#box").addClass("open");
					$('#box').show();
					$('#box-shadow').show();
										
					var str5="";
					str5+="<div id='boxcon'>";
					str5+="<span id='msginfo'>예약 요청 거절 사유를 작성해주세요</span><br>";
					str5+="거절사유 : <input type='text' id='rejectreason' placeholder='직접입력'/><br>";
					str5+="<br><input class='confirm' type='button' value='OK'>";
					str5+="</div>";
					$('#box').html(str5);
					
					$('.confirm').on('click', function(event){
						$.ajax({
							url:'rejectstore',
							type:'post',
							data:{resvId1:resvId1,resvId2:resvId2,resvTime:resvTime,
								rejectreason:$('#rejectreason').val()},
							success:(function(data){
								console.log(data);
							}),
							error:(function(err){
								console.log(err);
							})
						});
						$('#box').removeClass();
						$('#box-shadow').hide();
						event.stopPropagation();
					});//end confirm click	
					event.stopPropagation();
				});//end click '식당거절'
				
			}),//end success
			error:(function(error){
				console.log(error);	
			})
			
		});//endajax
	};//end ifkindS
	
};  //end sidemenukind

   
</script>
</html>