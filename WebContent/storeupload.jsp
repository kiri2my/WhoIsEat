<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
       
        <style>
div {
	padding: 10px;
	border: 2px solid coral;
	margin: 10px;
	text-align: center;
}

#main {
	width: 860px;
	height: 555px;
	margin: auto;
}

#photo {
	width: 280px;
	height: 280px;
	float: left;
}

#name {
	width: 490px;
	height: 400px;
	float: right;
	text-align: left;
}

#map {
	width: 280px;
	height: 160px;
	display: inline-block;
	float: left;
	text-align: left;
}

button {
	width: 200px;
	height: 50px;
	background-color: coral;
	color: white;
	border-style: none;
	font-size: 19px;
}

input {
	margin: 7px 0px;
	border-style: none;
	border-bottom-style: inset;
}

#reset, #s_btn {
	width: 200px;
	height: 50px;
	top: -10px;
	background-color: coral;
	color: white;
	border-style: none;
	font-size: 19px;
	border-radius: 5px;
}

#mainfont {
	margin: 2px;
	color: coral;
	font-size: 20px;
}




.filebox {
	height: 200px;
	width: 230px;
	overflow: hidden;
}

.filebox, #locationgo {
	border-style: none;
}

.filebox label, #locationgo, #photo label {
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

#locationgo {
	background-color: coral;
	position: sticky;
	width: 70px;
	height: 30px;
	padding: 5px;
	left: 220px;
	overflow: hidden;
	clip: rect(0, 0, 0, 0);
	border: 0;
	margin: 15px;
	font-size: 13px;
}

#locationgo:hover,#photo label:hover,#reset:hover, #s_btn:hover {
	background-color: lightsalmon;
}

#locationgo:active,#photo label:active,#reset:active, #s_btn:active{
	background-color: #CA3D13;
}

.filebox input[type="file"], #photo input[type="file"] {
	position: absolute;
	width: 1px;
	height: 1px;
	padding: 0;
	margin: -1px;
	overflow: hidden;
	clip: rect(0, 0, 0, 0);
	border: 0;
}

#sido, #sigungu, #bname {
	display: block;
}

span {
	font-size: 12px;
	color: coral;
	font-weight: bold;
}

input[name="st_saleinfo"] {
	width: 210px
}

#st_locainfo, #st_address {
	position: relative;
	width: 268px;
	margin: 0px;
}

#cmtarea, #txtarea {
	width: 476px;
	height: 70px;
	resize: none;
	border-style: none;
	border: #ececec;
	border-width: thick;
	border-style: solid;
}

input#st_address {
	position: relative;
}

input[name="st_cate"] {
	width: 150px
}

input[name="st_intro"] {
	width: 415px
}

input[name="st_phone"] {
	width: 431px
}

input[name="st_event"] {
	width: 431px
}

input[name="st_menu"] {
	width: 443px
}



</style>
</head>
<body>
	<form action="updatestoredetail" method="post"
		enctype="multipart/form-data">
		<div id="main">
			<h1 id="mainfont">점포 등록하기</h1>
			<div id="photo">
				<div class="filebox">
					<h1 style="padding-top: 90px; font-size: 20px; color: coral;">식당
						사진</h1>
				</div>
				<label id=filebtn>사진 올리기 <input type="file" id="ex_file"
					name="st_photo">
				</label>
			</div>
			<div id="name">
				<span style="font-size: 15px">식당이름 : </span> <input type="text"
					name="st_name" required> <span>식당 카테고리 : </span> <input
					type="text" name="st_cate" required><br> <span>가게
					소개 : </span> <input type="text" name="st_intro" required><br>
				<span>연락처 : </span> <input type="text" name="st_phone" required><br>
				<span>메뉴 : </span><input type="text" name="st_menu" required><br>
				<span>이벤트 : </span> <input type="text" name="st_event" required><br>
				<span>사장님의 한마디 : </span><br>
				<textarea id="cmtarea" name="st_comment"></textarea>
				<br> <span id="htag">사업자정보 : </span><br>
				<textarea id=txtarea name="st_info"></textarea>
				<br> <input type="hidden" id='sido' name='sido' /> <input
					type="hidden" id='sigungu' name='sigungu' /> <input type="hidden"
					id='bname' name='bname' />
			</div>


			<div id="map">
				<span>영업정보 : </span> <input type="text" name="st_saleinfo" required><br>
				<span>식당주소 : </span> <input type="button" id="locationgo"
					value="주소검색" required /><br> <input type="text"
					id="st_locainfo" name="st_locainfo" required /><br> <input
					type="text" id="st_address" name="st_address"
					placeholder="상세주소를 입력해주세요." required /><br>


			</div>
            <button id="s_btn">등록하기</button>
            <input type="reset" id="reset" value="다시작성"/>
        </div>
         </form>
    </body>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
var id='${id}';

$("#id").val(id);

	
$("#locationgo").click(function(){
	new daum.Postcode({
	   	 oncomplete: function(data) 
	   	 {
            if (data.userSelectedType === 'R') 
            { // 사용자가 도로명 주소를 선택했을 경우
            	alert("지번 주소를 선택해주세요");
            }
            else
            {
            	$("#st_locainfo").val(data.sido+" "+data.sigungu+" "+data.bname);
            	$("#sido").val(data.sido);
            	$("#sigungu").val(data.sigungu);
            	$("#bname").val(data.bname)
            }
	   	 }
		}).open();
});

/* console.log($('#sido').val(sido));
console.log($('#sigungu').val(sigungu));
console.log($('#bname').val(bname)); */

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
        	var str="<img src='"+e.target.result+"' width='220' height='240'/>" 
        	$(".filebox").html(str);  
        }
        reader.readAsDataURL(input.files[0]);
    }
}

	
 $(":file").change(function(){
	 readURL(this);
});

</script>   
</html>