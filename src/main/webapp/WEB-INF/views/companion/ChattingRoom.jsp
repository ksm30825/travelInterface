<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" type = "text/css" href = "resources/css/companion/chattingRoom.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<!-- modal   -->
<style>
	#chatpeopleTable {
		width : 100%;
	}

	#chatpeopleTable:hover{
		background : lightgray;
		cursor : pointer;
		width : 100%;
	}
	
	.label-danger {
		margin:5%; float:right;padding: .5em .6em .5em;
	}
	
	#chat_box {
		overflow-y: auto;
	   	height: 75%;
	    position: absolute;
	    width: 100%;
	    margin-top: 15%;
	}
	
	#ChatRoomTitle{
	    margin: 5%;
	}
	
	::-webkit-scrollbar { 
    	display: none !important; 	
    }
    
    .Firebtn{
    	background : none ; 
    	border : none;
    	color : red;
    }
    
    #reputicon { 
	 	width: 100%;
	    height: 100%;
	    max-width: 30%;
	    margin-left: 35%;
	    margin-top : 3%;
    }
    
    #reputid{
	   	margin: 5%;
	    margin-left: 20%;
	    font-size: 15px;
	    text-align : center;
    }
    
    #reputinfor{
    	margin-left: 43%;
    	font-size: 15px;
    	text-align : center;
    }
    
    .item{
    	height : auto;
    	max-height : 300px;
    	overflow-y: auto;
    }
    
    .itembtn{
    	width : 50% !important;
    	color: white!important;
    	background-color: #7c91dc!important;
    	border :1px solid #7c91dc!important;
	}
	
	.itembtn:hover{
    	width : 50% !important;
    	background-color: white!important;
    	color: #7c91dc!important;
    }
    
    #chatStatusTable td{
		padding : 3%;
    }
    

    #chatStatusTable {
    	width : 100%;	   
   	}

    
	
</style>
<body>
	<c:set var = "contextPath" value = "${pageContext.servletContext.contextPath }" scope = "application"/>
	<input type = "hidden" value = "${chatId}" id = "ReChatID">
	<input type = "hidden" value = "${loginUser.userName}" id = "UserName">
	<input type = "hidden" value = "${loginUser.memberId}" id = "loginId">

	
	<div class="w3-sidebar w3-bar-block w3-card w3-animate-right" style="display:none;right:0;width:80%;" id="rightMenu">
	  <div style = "height : 7%; width : 100%; background: #6196ed;">
	  	<button onclick="closeRightMenu()" 
	  			class="w3-bar-item w3-button w3-large" 
	  		 	data-toggle="tooltip" data-placement="right" title="메뉴닫기"
	  			style = "width: 15%;float:left;height: 100%;">
			<i class="material-icons" 
			style = "color : black; font-size : 20px;">close</i>
	  	</button>
		 <label style = "font-size :15px; padding : 15px; margin-left : 15%; margin-top:1%;">채팅방 정보</label>
		 <span id = "Recruitingicon" class="label label-danger">모집중</span>
	  </div>
	  <div id = "RoomInfoDIV" style = "height : 30%; width : 100%; overflow-y: auto; "></div>
	  <div style = "height : 7%; width : 100%; background : #a8c9ff">
	  	 <label style = "margin: 20px;">대화상대</label>
	  </div>
	  <div id = "MemberInfoDiv" style = "height : 50%; width : 100%; overflow-y : auto;"></div>
	  <div style = "height : 5%;">
	  	<ul class="list-inline" style = "border-top:1px solid lightgray;">
   			 <li> 
   			 <button  data-toggle="tooltip" 
	  	      data-placement="right" title="채팅방 나가기" id = "outbtn" 
	  	      onclick="document.getElementById('id01').style.display='block'">
	  	 		<i class="material-icons" 
	  	 			style = "margin :0 ; padding : 3px; 
	  	 				color: black; font-size : 30px;">input</i>
	  	 	</button></li>
    		<li style = "margin: 3px;float: right;">
	    		<button id = "setting"  data-toggle="tooltip" 
	  	     		 data-placement="top" title="채팅방 설정"   onclick="document.getElementById('statusModal').style.display='block'">
	    			<i class="material-icons"
	    			style = "margin:0; padding: 0; color : gray; font-size : 30px;">
	    			settings</i>
	    		</button>
    		</li>
  		</ul>
	 </div>
	</div>
	
	<div class="w3-teal">
	  <button id = "returnBtn" onclick = "location.href = '${contextPath}/enterRoom.ch'"><i class="material-icons" >keyboard_arrow_left</i></button>
	  <button class="w3-button w3-teal w3-xlarge w3-right" id = "menubtn" onclick="openRightMenu()">&#9776;</button>
	  <div class="w3-container" id = "header">
	   	 <h4 align = "center" id = "ChatRoomTitle">채팅방 불러오기 실패</h4>
	  </div>
	</div>
	<div class="w3-container" id = "chat_box"></div>
	<div id="footer" >
		<textarea rows="3" cols="30" name = "message" id = "message"></textarea>
		<button type="button" class="btn btn-primary" id = "sendbtn">전송</button>
	</div>
	
	<!-- 나가기 모달창 -->
	 <div id="id01" class="w3-modal">
	    <div class="w3-modal-content w3-animate-bottom w3-card-4" >
	      <header class="w3-container w3-teal" style = "background : #f09eda !important;" >
	        <span onclick="document.getElementById('id01').style.display='none'" 
	        class="w3-button w3-display-topright">&times;</span>
	        <h4 align="center" >채팅방을 나가겠습니까?</h4>
	      </header>
	      <div class="w3-container">
	      <br>
	        <p>
	                  나가기를 하면 대화내용이 모두 삭제되고 채팅목록에서도 삭제됩니다 <br>
	                  또한, 나가기 버튼 클릭시 참여중인 채팅목록으로 이동됩니다.
	        </p>
	        <br>
	      </div>
	      <footer class="w3-container w3-teal" style = "background : #f09eda !important;">
	       	 <button id = "exitBtn" class="w3-button w3-black" style = "float : right; background-color: #f09eda !important;">나가기</button>
	      </footer>
	    </div>
  	</div>
  	
  	<!-- 상태 변화 하는 설정 모달 창   -->
  	 <div id="statusModal" class="w3-modal">
	    <div class="w3-modal-content w3-animate-bottom w3-card-4" >
	      <header class="w3-container w3-teal" style = "background : #f09eda !important;" >
	        <span onclick="document.getElementById('statusModal').style.display='none'" 
	        class="w3-button w3-display-topright">&times;</span>
	        <h4 align="center">설정</h4>
	      </header>
	      <div class="w3-container" id = "statusContent" align = "center">
	      	<br>
	   		<table id = "chatStatusTable">
	   			<tr>
	   				<td><b>채팅방 상태 </b></td>
	   				<td id = "chatStatusLabel" style = "text-align : right;">
	   				</td>
	   			</tr>
	   			<tr>
	   				<td colspan = "2" id = "chatSatusDetail"></td>
	   			</tr>
	   		</table>
	      </div>
	      <br>
	      <footer class="w3-container w3-teal" style = "background : #f09eda !important;">
	       	 <button id = "AddchangeBtn" class="w3-button w3-black" 
	       	 style = "float : left; background-color: #f09eda !important;height : 50px;">설정 더보기</button>
	       	 <button id = "changeBtn" class="w3-button w3-black" style = "float : right;height : 50px; background-color: #f09eda !important;"></button>
	      </footer>
	    </div>
  	</div>
  	
  	<!-- 사용자 정보 모달창  -->
  	<div id="reputInfo" class="w3-modal">
	    <div class="w3-modal-content w3-animate-bottom w3-card-4" >
	      <header class="w3-container w3-teal" style = "background : #f09eda !important;" >
	        <span id = "reputClose" onclick="document.getElementById('reputInfo').style.display='none'" 
	        class="w3-button w3-display-topright">&times;</span>
	        <h4 align="center" id = "reputuserName">사용자정보</h4>
	      </header>
	      <div class="w3-container">
	   		<div class = "info">
	   			<input type = "hidden" id = "selectUserId">
	   			<input type = "hidden" id = "selectUserName">
    			<img alt="user" src="resources/images/usericon.png" id = "reputicon">
    			<br>
    			<label id = "reputid"></label>
    			<br>
    			<label id = "reputinfor">20대 (여)</label>
    			<div class="w3-bar w3-black">
				  <button class="w3-bar-item w3-button itembtn" onclick="openItem('Like')">좋아요</button>
				  <button class="w3-bar-item w3-button itembtn" onclick="openItem('Bad')">싫어요</button>
				</div>
				<div id="Like" class="w3-container item">
				 	<h4 align = "center">아직 정보가 없습니다.</h4>
				</div>
				<div id="Bad" class="w3-container item" style="display:none">
				  	<h4 align = "center">아직 정보가 없습니다.</h4>
				</div>			
    		</div>
	      </div>
	      <br><br>
	      <footer class="w3-container w3-teal" style = "background : #f09eda !important; height: 50px;">
	       	 <button id = "Firebtn" class="w3-button w3-black" style = "height: 50px;  float : left; background-color: #f09eda !important;">강퇴하기</button>
	       	 <button id = "declarBtn" class="w3-button w3-black" style = "height: 50px; float : right; background-color: #f09eda !important;">신고하기</button>
	      </footer>
	    </div>
  	</div>
	
	
	
	<script src="http://localhost:8010/socket.io/socket.io.js"></script>
    <script src="https://code.jquery.com/jquery-1.11.1.js"></script>
	<script>
		function openRightMenu() {
		  document.getElementById("rightMenu").style.display = "block";
		}
		
		function closeRightMenu() {
		  document.getElementById("rightMenu").style.display = "none";
		}
		
		
		$(document).ready(function() { //start
			
			var userId = ${ loginUser.memberId };
			var userName = $("#UserName").val();
			var chatId = $("#ReChatID").val();
    	 	
    		console.log("userId :"+userId);
    		console.log("chatId :" + chatId);
    		  		
    	   //서버
		   var socket = io("http://localhost:8010");
    	  	
		   //채팅Manager 값 가져오기
	       socket.emit('preChatManager' , {chatId : chatId , div : "처음"});
	       
		   
	       //채팅Manager 값 가져오기
	       socket.on('preChatManager', function(data){
	        	   console.log(data);
	      
	        	   $.ajax({
	        		   url : "${contextPath}/memberInfo.ch",
	        		   data : {userId : data.user},
	        	       success : function(userInfo) {
	        	    	   
	        	    	   var output = "";
	        	    	   output += '<table id = "chatpeopleTable" style = "border-bottom : 1px solid lightgray;">';
	    	        	   output += '<tr><td colspan = "2">';
	    	        	   output += '<input type = "hidden" value = "'+ userInfo.memberId +'" name = "userId" id = "userId">';
	    				   output += '<input type = "hidden" value = "'+ userInfo.userName +'" name = "username" id = "username">';
	    				   output += '<input type = "hidden" value = "'+ userInfo.age +'" name = "userAge" id = "userAge">';
	    				   output += '<input type = "hidden" value = "'+ userInfo.gender +'" name = "usergender" id = "usergender">';
	    				   output += '<label id = "chUserInfo">'+ userInfo.userName +'('+userInfo.email+')</label>';				
	    				   output += '</tr><tr><td>';
	    				   output += '<i id = "goodicon" class="material-icons">thumb_up_alt</i>';
	    				   output += '<p id = "good">0</p>';
	    				   output += '</td>';
	    				   output += '<td>';
	    				   output += '<i id = "badicon" class="material-icons">thumb_down_alt</i>';
	    				   output += '<p id = "bad">0</p>';
	    				   output +=  '</td>';
	    				   output += '</tr>';	  		
	    		  		   output += '</table>';
	    		  		   $(output).appendTo("#MemberInfoDiv");
	        	       },
	        	       error : function(){
	        	    	   console.log("에러발생");
	        	       }
	        	   });
	        	   
	        	   if (userId == data.user){
	        		   var enterDate = data.enter_date
	        		   
	        		   console.log("들어온 날짜 :"+enterDate);
	        		   
	        		   if (data.div == "처음"){
	        			   //채팅방  대화 가져오기 위해서 소켓 실행
		    	           socket.emit('preChat', {userId : userId ,  chatId : chatId , enter_date : enterDate});
	        		   }
	        		   
	        		   $("#Firebtn").hide();
	        		   if (data.level == 2){
	        			   $("#setting").hide();
	        			   $("#Firebtn").hide();
	        		   }else {
	        			   $("#outbtn").attr("disabled" , "disabled");
	        			   $("#checkModel").show();
	        			   $("#setting").show();
	        			   $("#Firebtn").show();
	 	        	   }
	        		 
	        		 
	        	   }
	        	
	        	
	          });
    	  	
    	  	//메세지 보내기 enter 
			 $("#message").keydown(function(key) {
	                //해당하는 키가 엔터키(13) 일떄
	                if (key.keyCode == 13) {
	                    //msg_process를 클릭해준다.
	                    sendbtn.click();
	                }
	            });
	 
	           //msg_process를 클릭할 때
	           $("#sendbtn").click(function() {
	                //소켓에 send_msg라는 이벤트로 input에 #msg의 벨류를 담고 보내준다.
	                
	                console.log("message :" + $("#message").val());
	                
	                socket.emit('message', {
	                	userId :userId , userName : userName , message : $("#message").val() , chatId : chatId
	                });
	                //#msg에 벨류값을 비워준다.
	                $("#message").val("");
	          });
	           
	           //메세지 보낸 후
	           socket.on('message' , function(data){
	                var output = '';
	                
	                console.log(data);
	                
	                var mchatId = data.chat_id;
	                
	                if (mchatId == chatId){
	                	
	                	if (data.userId == userId){
	 		                output += '<div class="alert alert-info" id = "msg" style = "background : #f1ccfc; border-color: #f1ccfc;"><strong>';                	
	 	                }else {
	 	                	output += '<div class="alert alert-info" id = "msg"><strong>'; 
	 	                }
	                	
	                	if (data.userName != null){
	                		output += data.userName;
	                	}
	 	              	
	 	                
	 	                output += '</strong> ';
	 	                output += data.message;
	 	                output += '</div>';
	 	                $(output).appendTo('#chat_box');
	 	                
	 	                $("#chat_box").scrollTop($("#chat_box")[0].scrollHeight);
	                }
	               
	            });
	           
      
	            //채팅들어왔을때 채팅 정보가져오기 
	           socket.on('preChat' , function(data){
	                var output = '';
	                
	                if (data.userId == userId ){
		                output += '<div class="alert alert-info" id = "msg" style = "background : #f1ccfc; border-color: #f1ccfc;"><strong>';                	
	                }else {
	                	output += '<div class="alert alert-info" id = "msg"><strong>'; 
	                }
	                
	                if (data.userName != null){
                		output += data.userName;
                	}
	                
	                output += '</strong> ';
		            output += data.message;
		            output += '</div>';
	                
	                $(output).appendTo('#chat_box');
	                
	                $("#chat_box").scrollTop($("#chat_box")[0].scrollHeight);
	            });
	           
	          //채팅방 정보  가져오기
	          socket.emit('preChatInfo' , {chatId : chatId});
	          
	          //채팅방 정보  가져오기
	          socket.on('preChatInfo' , function(data){  
	        	  var title = data.title;
	        	  
	        	  if (title != null){
					  if (title.length > 7){
				        	  $("#ChatRoomTitle").text(title.substr(0,7) + "...    (" + data.activityNum  + ")" );
			          }else {
			        		  $("#ChatRoomTitle").text(title + "     (" + data.activityNum + ")");
			          } 
				  }
	        	  
	        	  var formattedStartDate = new Date(data.start);
	        	  var sd = formattedStartDate.getDate();
	        	  var sm =  formattedStartDate.getMonth();
	        	  sm += 1;  // JavaScript months are 0-11
	        	  var sy = formattedStartDate.getFullYear();

				 var startDate = sy + "/" + sm  + '/' + sd;
				 
				 var formattedEndDate = new Date(data.end);
	        	  var ed = formattedEndDate.getDate();
	        	  var em =  formattedEndDate.getMonth();
	        	  em += 1;  // JavaScript months are 0-11
	        	  var ey = formattedEndDate.getFullYear();

				 var endDate = ey + "/" + em  + '/' + ed;

	        	 var output = "";
	        	  
	        	 output += '<table id = "Chattinginfor">';
	        	 output += '<tr><td><b>채팅방 제목</b></td>';
	  	  		 output += '<td>'+title+'</td>';
	  	  		 output += '</tr><tr>';
	  	  		 output += '<td><b>여행지</b></td>';
	  	  		 output += '<td>'+data.place+'</td>'
	  	  		 output +=	'</tr><tr>'
	  	  		 output += '<td><b>여행날짜</b></td>';
	  	  		 output += '<td>' + startDate + "~" + endDate + "</td>"
	  	  		 output += "</tr>"
	  	  		 output += '<tr><td><b>상세설명</b></td>';
	  	  		 output += "</tr>"
	  	  		 output += "<tr>"
	  	  		 output += "<td colspan = '2'>" + data.detail + "</td>"
	  	  		 output += '</table>';
	        	  
	  	  		 $(output).appendTo('#RoomInfoDIV');
	  	  		 
	  	  		 var chatStatus = data.status;
	  	  		 $("#Recruitingicon").text(data.status); 
	  	  		 $("#chatStatusLabel").text(chatStatus);
	  	  		 if (data.status == "여행중"){
	  	  		 	$("#Recruitingicon").style("color" , "yellow");
	  	  		 }else if (data.status == "모집완료"){
	  	  			$("#Recruitingicon").style("color" , "green");
	  	  			var temp = "다시 모집을 하시겠습니까?";
	  	  			$("#chatSatusDetail").text(temp);
	  	  			$("#changeBtn").text("모집하기");
	  	  		 }else if (data.status == "여행준비중"){
	  	  			$("#Recruitingicon").style("color" , "pink");
	  	  		 }else if (data.status == "여행종료"){
	  	  			$("#Recruitingicon").style("color" , "blue");
	  	  		 }else {
	  	  			var temp = "모집종료를 하시겠습니까?";
	  	  			$("#chatSatusDetail").text(temp);
	  	  			$("#changeBtn").text("모집종료");
	  	  		 }
	  	  		 
	  	  		 
	        	 

	        	  
	          });
	          
	          //새로운 회원이 채팅방에 들어왔을 때
	          socket.on('newUser' , function(data){
	        	  var newUser = data;
	        	  
	        	  console.log("userName :" + data.userName)
	        	 
	        	  var mchatId = data.chatId;
	 			  var output = "";
		       	  if (mchatId == chatId){
	 	                output += '<div class="alert alert-info" id = "msg">';
			            output += data.message;
			            output += '</div>';
			            $(output).appendTo('#chat_box');
			            
	 	                $("#chat_box").scrollTop($("#chat_box")[0].scrollHeight);
	               }
	        	  
	        	  $.ajax({
	        		   url : "${contextPath}/memberInfo.ch",
	        		   data : {userId : data.user},
	        	       success : function(userInfo) {
	        	    	   
	        	    	   var output = "";
	        	    	   output += '<table id = "chatpeopleTable" class = "chatpeopleTable" style = "border-bottom : 1px solid lightgray;">';
	    	        	   output += '<tr><td colspan = "2">';
	    	        	   output += '<input type = "hidden" value = "'+ userInfo.memberId +'" name = "userId" id = "userId">';
	    				   output += '<input type = "hidden" value = "'+ userInfo.userName +'" name = "username" id = "username">';
	    				   output += '<label id = "chUserInfo">'+ userInfo.userName +'('+userInfo.email+')</label></td>';					
	    				   output += '</tr><tr><td>';
	    				   output += '<i id = "goodicon" class="material-icons">thumb_up_alt</i>';
	    				   output += '<p id = "good">0</p>';
	    				   output += '</td>';
	    				   output += '<td>';
	    				   output += '<i id = "badicon" class="material-icons">thumb_down_alt</i>';
	    				   output += '<p id = "bad">0</p>';
	    				   output +=  '</td>';
	    				   output += '</tr>';	  		
	    		  		   output += '</table>';
	    		  		   $(output).appendTo("#MemberInfoDiv");
	    		  	
	        	       },
	        	       error : function(){
	        	    	   console.log("에러발생");
	        	       }
	        	   });
	        	 
	        	  
	        	  //채팅방 수정될때 다시 채팅방 정보불러오기 
		          socket.emit('updateChatInfo' , {chatId : chatId});
	 				
		       	
	 	       
	          });
	          
	         
	          
	          //채팅방에서 나갈때 
	          $("#exitBtn").click(function(){
	        	  
	        	  socket.emit('exitChatting',{userId: userId , chatId : chatId , userName : userName});
	        	  
	        	  
	        	  socket.on('exitChatting', function(data){
	        		  console.log("나간사람 :" +data);
	        		
	        		  
	        		  location.href = "${contextPath}/enterRoom.ch";
	        		  
	        	  });
	        	  
	        	  
	          }); 
	          
	        //사용자가 채팅방을 나간경우 
	        socket.on('outUser' , function(data){
	        	  var newUser = data;
	        	  
	        	 console.log("outUser :" + data);
	        	 
	        	  var mchatId = data.chat_id;
	 			  var output = "";
	 			  
	 			  if (data.div == "나감"){
	 				  if (mchatId == chatId){
		 	                output += '<div class="alert alert-info" id = "msg">'; 
				            output += data.message;
				            output += "<button> 신고 </button>";
				            output += "<button> 평판관리 </button>";
				            output += '</div>';
				            $(output).appendTo('#chat_box');
				            
		 	                $("#chat_box").scrollTop($("#chat_box")[0].scrollHeight);
		               } 
	 			  }else {
	 				  if (mchatId == chatId){
		 	                output += '<div class="alert alert-info" id = "msg">'; 
				            output += data.message;
				            output += '</div>';
				            $(output).appendTo('#chat_box');
				            
		 	                $("#chat_box").scrollTop($("#chat_box")[0].scrollHeight);
		               }  
	 			  }
		       	
		       	  
		       	  var muserId = data.userId;
		       	  $(".chatpeopleTable").each(function(index ,item) {
	        			 var checkUserID = $(this).children().children().children().children("#userId").val();
	        			 console.log("muserId : " + muserId + "- checkUserId :" + checkUserID);
	        			 if (checkUserID == muserId){
	        				$(this).remove();
	        			 } 
	        	 });
		       	  
		       	  if (userId == data.userId){
		       		  location.href = "${contextPath}/joinRoom.ch";
		       	  }
	        	  
	        	 
	        	  //채팅방 수정될때 다시 채팅방 정보불러오기 
		          socket.emit('updateChatInfo' , {chatId : chatId});
	 			
	 	       
	         });
	        
	          //강퇴하기 
	          $("#Firebtn").click(function(){
	        	  console.log("fireUser:" + $("#selectUserId").val() );
	        	  
	        	  var fireuser = $("#selectUserId").val();
	        	  var fireusername = $("#selectUserName").val();
	        	  
				  socket.emit('FireChattingUser',{user: fireuser , chatId : chatId , username : fireusername});
	        	  
	        	  
	        	  socket.on('FireChattingUser', function(data){
	        		  $("#reputClose").click();
	    
	        	  });
	        	  
	        	  
	          });
	          
	          //채팅방에 누군가 들어왔을때 다시 채팅방 정보 띄워주는 함수
	          socket.on('updateChatInfo' , function(data){
				  var title = data.title;
	        	  
				  if (title != null){
					  if (title.length > 7){
				        	  $("#ChatRoomTitle").text(title.substr(0,7) + "...    (" + data.activityNum  + ")" );
			          }else {
			        		  $("#ChatRoomTitle").text(title + "     (" + data.activityNum + ")");
			          } 
				  }
	        	
	          });
	          
	          //설정 더보기 버튼 클릭시 
	          $("#AddchangeBtn").click(function(){
	        	  location.href = "${contextPath}/showchange.ch?num=" + chatId;
	          });
	          
	          
	          //신고하기 버튼 클릭시 
	          $("#declarBtn").click(function(){
	        	 location.href = "${contextPath}/declaration.ch?num=" + chatId;  
	          });
	          
	          
	         
	         
	           
	           
	           
		}); //end
		
		//참여중인 사용자 리스트 클릭시 
		$(document).on("click","#chatpeopleTable tr",function(){
			var loginUser = $("#loginId").val();
			
 	 		var userid = $(this).parent().children().children().children("#userId").val();
 	 		var user = $(this).parent().children().children().children("#username").val();
 	 		var ulabel = $(this).parent().children().children().children("#chUserInfo").html();
 	 		var gender = $(this).parent().children().children().children("#usergender").val();
 	 		var userAge = $(this).parent().children().children().children("#userAge").val();
 	 		
 	 		
 	 		$("#selectUserId").val(userid);
 	 		$("#selectUserName").val(user);
 	 		
 	 		console.log("user :" + ulabel);
 	 		
 	 		
 	 		if (gender == "M"){
 	 			gender = "남";
 	 		}else {
 	 			gender = "여";
 	 		}
 	 		
 	 		var age;
 	 		if (userAge != 0){
 	 			var temp = userAge / 10;
 	 			
 	 			temp = Math.floor(temp);
 	 			
 	 			age = temp + "0 대";
 	 			
 	 		}else {
 	 			age = "미정";
 	 		}
 	 		
 	 		console.log("userId :" + userid + "- login" + loginUser );
 	 		
 	 		if (loginUser == userid ){
 	 		   document.getElementById('declarBtn').disabled = true;
 	 		   document.getElementById('Firebtn').disabled = true;
 	 		}else {
 	 			document.getElementById('declarBtn').disabled = false;
  	 		   document.getElementById('Firebtn').disabled = false;
 	 		}
 	 		
 	 		$("#reputid").text(ulabel);
 	 		$("#reputinfor").text(age + " (" + gender + ") ");
 	 		
			document.getElementById('reputInfo').style.display='block';
 	 		
 	 	}); 
		
		//Tab탭 좋아요 싫어요 
		function openItem(ItemName) {
			  var i;
			  var x = document.getElementsByClassName("item");
			  for (i = 0; i < x.length; i++) {
			    x[i].style.display = "none";  
			  }
			  document.getElementById(ItemName).style.display = "block";  
		}
	
	
		
	</script>
</body>
</html>