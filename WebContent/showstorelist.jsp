<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
span{
	font-size:14px;
	color:coral;
	font-weight: bold;
}
#st_name,#st_cate,#st_intro,#st_menu,#st_phone,#st_event,#cmtarea,#st_saleinfo,#st_locainfo,#st_info{
	font-size:20px;
	color:white;
	
}
#listdiv {
    width: 410px;
    float: right;
    text-align: center;
    width: 426px;
    
}
#listdiv>span{
	font-size: 18px;
}
#listdiv>span>b{
	font-size: 25px;
}


#main_de {

    padding: 7px;
    border: 1px solid white;
    height: 500px;
    
}

#photo {
    margin: 5px;
    float: left;
    width: 310px;
    height: 300px;
    padding: 5px;
    overflow: hidden;
}
img{
	border-radius: 20px;
	
}

#name {
    width: 400px;
    height: 300px;
    text-align: left;
    float: right;
    margin: 5px;
    padding: 5px;
    border: 4px solid coral;
    border-radius: 10px;
        overflow: auto;
}

#map {
    /* position: relative; */
    text-align: left;
    /* float: left; */
    margin: 5px;
    padding: 5px;
    /* border: 3px solid coral; */
    /* border-radius: 10px; */
    width: 305px;
    height: 130px;
    overflow: auto;
    /* display: flow-root; */
}

#btn_ajax {
    width: 418px;
    height: 40px;
    background-color: coral;
    color: white;
    border-style: none;
    font-size: 23px;
    float: right;
    margin: 2px 5px;
    padding: 5px;
    border-radius: 10px;
    text-shadow: -1px 2px black;
    /* background-color: #bc9494; */
    /* box-shadow: -2px 2px 0px black; */
}

#table {
	border: 1px solid white;
	width: 870px;
	height: 500px;
	float: left;
	margin: 3px;
	padding: 3px;
	text-align: center;
}

#head {
	
	height: 50px;
}

#middle {
	border: 5px solid coral;
    border-radius: 10px;
    text-align: center;
    padding: 7px;
    margin: 1px;
}

#under {
	top: 100px;
	height: 50px;
	margin: 10px;
}

#detail {
	position: fixed;
	top: 30px;
	left: 25%;
	width: 800px;
	height: 519px;
	padding: 20px;
	margin-left: -200px;
	border: 1px solid black;
	z-index: 101;
	display: none;
	overflow: auto;
	
}

#detail-shadow {
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

#detail.open {
	display: block;
	color: #848484;
}



#name input {
	
	width: 200px;
	height: 20px;
	
}

#map input {
	width: 300px;
	height: 20px;
}

input#cmt {
	height: 100px;
}

input#p_upload {
	position: relative;
	width: 100px;
	height: 30px;
	
	background-color: coral;
	color: white;
	border-style: none;
	font-size: 19px;
}

input#reset {
	position: relative;
	width: 100px;
	height: 40px;
	top: -10px;
	background-color: coral;
	color: white;
	border-style: none;
	font-size: 19px;
}
#msgarea {
    /* position: relative; */
    height: 60px;
    width: 410px;
    resize: none;
    float: right;
    margin: 6px;
    border-radius: inherit;
}
#msgarea::placeholder{
	text-align: center;
	font-size: 20px;
}

#cmtarea {
	position: relative;
	width: 200px;
	height: 30px;
}

#txtarea {
	width: 280px;
	height: 78px;
	resize: none;
}




#htmltable {
	width: 840px;
	border-style: none;
}

#showdetail {
    background: white;
    color: lightsalmon;
    border-style: none;
    font-size: 13px;
}
#showdetail:hover{
	color: #FE2E64;
	text-decoration: underline;
}
h1{
color:coral;
}

.showdetail{
	
}


.select{
	border: coral;
	background-color: coral;
	font-color: white;
}
a {
	color: white;
    text-decoration: none;
}
a:hover{
	text-decoration: overline;
}

#localreturn{
	
}
#searchutil{
    float: right;
    padding: 3px;
    border-bottom: 2px solid coral;
}    
#storeserch{
	border-style: inherit;
    background-color: coral;
    border: coral;
    color: white;
    border-radius: 5px;
    padding: 3px 10px;
}
#storeserch:hover{
	
    background-color: lightsalmon;
    
}
#localreturn{
	border-style: inherit;
    background-color: #bebebe;
    border: coral;
    color: white;
    border-radius: 5px;
}

h3,h5{
	margin:5px;
	color: coral;
}

#text{
	color:white;

}




</style>
</head>
<body>
	<div id="table">
		<div id="head">
			<h1>${dong} 식당 리스트</h1>
		</div>
		<div id=middle>${showstoreList}${none}</div>
		<div id="under">
			<button style="float: left;" id="localreturn">지역다시선택</button>
			<div id='searchutil'>
				<select name="scmode" size="1" style="height: 23px" id="selectmode">
					<option value="ST_NAME">식당이름</option>
					<option value="ST_CATE">식당카테고리</option>
				</select> <input type="text" placeholder="검색어를 입력하세요" id="serchtext">
				<button id="storeserch">검색</button>
			</div>
		</div>
	</div>

	<div id="detail">
		<!-- 상품 상세정보출력 -->
	</div>
	<div id="detail-shadow">
		<!-- 그림자 -->
	</div>




</body>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<script>
	$('#detail-shadow').click(function() {
		$('#detail').removeClass();
		$('#detail-shadow').hide();

	});//click end

	function detail(storenum) {
		$("#detail").addClass("open");
		$('#detail').show();
		$('#detail-shadow').show();
		console.log(storenum);
		$
				.ajax({
					url : 'showstoredetail',
					type : 'get',
					data : {
						storenum : storenum
					},
					dataType : 'json',
					success : (function(data) {
						console.log(data);
						var str = "";
						str = "<div id='main_de'><div id='photo'><img src='upload/"+data.st_photo+"' width='300'></div>"
								+ "<div id='name'><span style='font-size: 15px'>식당이름 : </span><span id='st_name' name='st_name' required>"
								+ data.st_name
								+ "</span><span>식당 카테고리 : </span><span id='st_cate' name='st_cate' required>"
								+ data.st_cate
								+ "</span><br><span>가게소개 : </span><span id='st_intro' name='st_intro' required>"
								+ data.st_intro
								+ "</span><br><span>메뉴 : </span><span id='st_menu' name='st_menu' required>"
								+ data.st_menu
								+ "</span><br><span>연락처 : </span><span id='st_phone' name='st_phone' required>"
								+ data.st_phone
								+ "</span><br><span>이벤트 : </span> <span id='st_event' name='st_event' required>"
								+ data.st_event
								+ "</span><br><span>사장님의 한마디 : </span><span id='cmtarea' name='st_comment'>"
								+ data.st_comment
								+ "</span></div>"
															
								+ "<textarea id='msgarea' placeholder='(선택)한 줄 메세지를 입력해주세요.      (ex.먹는 스타일, 메뉴 등)'></textarea>"
								+ "<span id='b_sp'><button id='btn_ajax'>현재 식당 선택하기</button></span>"
															
								+ "<div id='map'>"
								+ "<span>영업정보 : </span>"
								+ "<span id='st_saleinfo' name='st_saleinfo' required >"
								+ data.st_saleinfo
								+ "</span><br><span>식당주소 : </span>"
								+ "<span id='st_locainfo' name='st_locainfo' required >"
								+ data.st_address
								+ "</span><br><span id='htag'>사업자정보 : </span>"
								+ "<span id='st_info' name='st_info'>"
								+ data.st_storenum
								+ "</span></div>"
								
								+ "<div id='listdiv'>"
								+ "<span>현재<b>(  <b id='storechoice'></b>"
								+ "  )</b>명이 이 식당에서 먹으려고 합니다.</span>"
								+ "</div>" + "</div>";

						$("#detail").html(str);
						
						$("#b_sp").on('click',$("#btn_ajax"),function() {
							var oneline = $("#msgarea").val();
							console.log(oneline);
							location.href = "showchoicelist?st_num="
										+ data.st_storenum
										+ "&st_name="
										+ data.st_name
										+ "&st_oneline="
										+ oneline;
									});
						

						$.ajax({
							url : 'showstorechoice',
							type : 'get',
							data : {
							storenum : storenum
							},
							dataType : 'json',
							success : (function(data2) {
								console.log(data2);
								var str2 = data2;
								$("#storechoice").html(str2);
 								
							}),error : (function(err) {
									console.log(err);
								})

							});//end ajax

					}),
					error : (function(err) {
						console.log(err);
					})

				});//end ajax

	};//end function detail
	
	
$("#localreturn").click(function() {
	location.href="reservetimetoselectlocation";
});
	
	
$("#storeserch").click(function() {
	var mode=$("#selectmode").val();
	var txt=$("#serchtext").val();
	console.log(mode);
	console.log(txt);
	location.href="storeserch?selectmode="+mode+"&serchtext="+txt;
	
/* 	$.ajax({
		url : 'storeserch',
		type : 'get',
		data : {
			selectmode : mode, serchtext : txt
		},
		dataType : 'json',
		success : (function(data) {
			console.log(data);

		}),
		error : (function(err) {
			console.log(err);
		})

	});//end ajax
 */
});	
	
	
</script>
</html>