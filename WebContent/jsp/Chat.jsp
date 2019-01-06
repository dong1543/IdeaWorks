<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Simple Chat</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Chat.css">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/Chat.js"></script>
    <script src="${pageContext.request.contextPath}/js/ChatWebSocket.js"></script>
</head>
<body>
    <div id="background"></div>
    <span id="senderAccount">${sessionScope.userInfo.userBasic.account}</span>
    <div id="userDetail">
        <div id="userHead">
            <img src="${pageContext.request.contextPath}/userHeadImg/${sessionScope.userInfo.userBasic.account}.jpg" alt="HEAD">
            <div id="uploadPanel">
                <input type="file" id="file" name="file">
                <input type="button" id="submit" value="上传" class="Button">
            </div>
        </div>
        <div id="Uploading">
            <progress value="0" max="100" id="progress">%</progress>
            <p id="message"></p>
        </div>
        <div id="nameGroup" class="InputGroup">
            <span><label for="Name">姓名</label></span>
            <span id="NameE" class="error"></span>
            <input id="name" name="name" type="text" value="${sessionScope.userInfo.userBasic.name}">
        </div>
        <div id="ageGroup" class="InputGroup">
            <span><label for="age">年龄</label></span>
            <span id="ageE" class="error"></span>
            <input id="age" name="age" type="text" value="${sessionScope.userInfo.userBasic.age}">
        </div>
        <div id="genderGroup" class="InputGroup">
            <span><label for="gender">性别</label></span>
            <span id="genderE" class="error"></span>
            <input id="gender" name="gender" type="text" value="${sessionScope.userInfo.userBasic.gender}">
        </div>
        <div id="cityGroup" class="InputGroup">
            <span><label for="city">城市</label></span>
            <span id="cityE" class="error"></span>
            <input id="city" name="city" type="text" value="${sessionScope.userInfo.userBasic.city}">
        </div>
    </div>
    <div id="friends">
        <div id="searchFriend">
            <input type="text" id="keyWords" placeholder="姓名/账号/城市">
            <i id="search"  class="fa fa-search fa-1x"></i>
        </div>

        <ul id="friendList">
	        <c:forEach var="friend" items="${sessionScope.userInfo.friends}" varStatus="status">
		        <li>
		        	<c:if test="${friend.unreadFlag}">
		        	<span class="unreadFlag"></span>
		        	</c:if>
		        	<span class="friendId">${friend.id}</span>
		        	<span class="friendAccount">${friend.account}</span>
	                <img class="userHead" src="${pageContext.request.contextPath}/userHeadImg/${friend.account}.jpg" alt="HEAD">
	                <h1 class="userName">${friend.name}</h1>
	                <i  class="fa fa-minus-square-o"></i>
	                <i  class="fa fa-plus-square-o"></i>
	            </li>
			</c:forEach> 
        </ul>
    </div>

    <div id="header">
        <div id="title">
            <i id="logout" class="fa fa-ban fa-2x"></i>
            <h1 class="H1_2">SIMPLE CHAT</h1>
        </div>

        <div id="userInfo">
            <img id="openUserDetail" class="userHead" src="${pageContext.request.contextPath}/userHeadImg/${sessionScope.userInfo.userBasic.account}.jpg" alt="HEAD">
            <h1 class="userName">${sessionScope.userInfo.userBasic.name}</h1>
            <i id="openFriends" class="fa fa-list fa-2x"></i>
        </div>
    </div>

    <div id="chatContainer">
        <div id="chatBox">

            <p id="friendName"></p>
            <p id="colseChatBox">X</p>

            <div id="chatPanel">
                
            </div>

            <textarea name="message" id="sendMessage"></textarea>
            <input type="button" id="sendButton" value="send">
        </div>

    </div>

    <div id="functionMenu">
        <div class="function">
        	<i class="fa fa-bank fa-2x"></i>
        </div>
        <div class="function">
        	<i class="fa fa-bug fa-2x"></i>
        </div>
        <div class="function">
        	<i class="fa fa-bolt fa-2x"></i>
        </div>
        <div class="function">
        	<i class="fa fa-coffee fa-2x"></i>
        </div>
        <div class="function">
        	<i class="fa fa-code fa-2x"></i>
        </div>
        <div class="function">
        	<i class="fa fa-cogs fa-2x"></i>
        </div>
        <div class="function">
        	<i class="fa fa-film fa-2x"></i>
        </div>
        <div class="function">
        	<i class="fa fa-group fa-2x"></i>
        </div>
    </div>
	
    <div id="footer">
        <br>
        <h3><i id="openFooter" class="fa fa-info fa-1x"></i>.温馨提示</h3>
        <p>1.快捷发送按键ALT+S</p>
        <p>2.点击朋友头像切换聊天</p>
        <p>3.朋友头像前有红色标识说明有未读消息</p>
    </div>
    <div id="feedback" class="feedback feedback_success" ></div>
</body>

</html>