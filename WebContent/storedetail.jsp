<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
       
        <style>
            div
            {
                padding: 10px;
                border: 2px solid coral;
                margin:30px; 
                text-align: center;
            }
            
            #main
            {
                width : 800px;
                height: 1000px;
                margin: auto;
            }
        
            #photo
            {
                position:relative;
                left: -10px;
                width : 400px;
                height: 400px;
                text-align: center;
            }
            
            #name
            {
                position: relative;
                left: 440px;
                width: 300px;
                height: 720px;
                top:-455px;
                text-align: left;
            }
            
            #map
            {
                position: relative;
                left : -10px;
                width: 400px;
                height: 350px;
                top: -920px;
                text-align: left;
            }
         	button
            {
                position: relative;
                width: 320px;
                height: 90px;
                top: -400px;
                left: 392px;
                background-color: coral;
                color: white;
                border-style: none;
                font-size: 23px;
            }
            #name input
            {
                position: relative;
                width: 300px;
                height: 30px;
                top: -10px;
            }
            #map input
            {
                width:400px;
                height: 30px;
            }
            input#cmt
            {
                height: 110px;
            }
            input#p_upload
            {
            	position: relative;
                width: 200px;
                height: 50px;
                top: -10px;
                background-color: coral;
                color: white;
                border-style: none;
                font-size: 19px;
            }
            input#reset
            {
            	position: relative;
                width: 200px;
                height: 50px;
                top: -10px;
                background-color: coral;
                color: white;
                border-style: none;
                font-size: 19px;
            }
            #cmtarea
            {
            	position:relative;
            	top:-10px;
                width:300px;
                height:40px;
            }
            #txtarea
            {
                width: 395px;
                height: 88px;
                resize: none;
            }
            #listdiv
            {
                position: relative;
                top : -927px;
                left: -10px;
                width: 400px;
                height: 69px;
            }
            #msgarea
            {
                position: relative;
                top : -470px;
                height: 80px;
                left : 65px;
                width: 320px;
            }
        </style>    
    </head>

    <body>
     <form action="insertstoreinfo" method="post" enctype="multipart/form-data">
        <div id="main">
            <div id="photo">
               	 식당 사진
				</div>
				
                <div id="name">
                <h2>식당이름:</h2> <input type="text" id="st_name" name="st_name" required>
                <h2>식당 카테고리:</h2> <input type="text" id="st_cate" name="st_cate" required>
                <h2>가게 소개:</h2> <input type="text" id="st_intro" name="st_intro" required>
                <h3>메뉴:</h3> <input type="text" id="st_menu" name="st_menu" required>
                <h3>연락처:</h3> <input type="text" id="st_phone" name="st_phone" required>
                 <h3>이벤트:</h3> <input type="text" id="st_event" name="st_event" required>
                  <h3>사장님의 한마디:</h3> <textarea id="cmtarea" name="st_comment"></textarea>
            </div>
            <button>현재 식당 선택하기</button>
            <textarea id="msgarea" placeholder="한 줄 메세지를 입력해주세요. (먹는 스타일, 메뉴 등)"></textarea>   
                
                   
            <div id="map">
                 <h3>영업정보:</h3>
                 <input type="text" id="st_saleinfo" name="st_saleinfo" required>
                  <h3>위치정보</h3>
                  <input type="text" id="st_locainfo" name="st_locainfo" required>
                   <h3>사업자정보:</h3>
                   <textarea id=txtarea id="st_info" name="st_info"></textarea>
            </div>
            <div id="listdiv"> 
            <h3>( )명이 이 식당에서 먹으려고 합니다.</h3>
            </div>
            </div>
            
         </form>
    </body>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
/* var st_num='2222-2222-22';

${str} */

	/* $.ajax
	({
		url:'selectstoredetail',
		type:'post',
		data:{st_num:st_num},
		
		dataType:'json',
		
		success: function(data)
		{
			console.log(data);
		},
		
		error: function(error)
		{
			console.log(error)
		}
	}); */


</script>   
</html>