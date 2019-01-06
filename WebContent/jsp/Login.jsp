<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
	<META HTTP-EQUIV="expires" CONTENT="0">
    <meta charset="UTF-8">
    <title>Simple Chat Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.particleground.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/Login.js"></script>
</head>
<body>
    <div id="background"></div>
    <h1 class="H1">SIMPLE CHAT</h1>
    <div id="feedback" class="feedback feedback_success"></div>

    <div id="loginPanel">
        <div id="openLogin" class="Button Button_50">登录</div>
        <div id="openRegister" class="Button Button_50">注册</div>


        <form id="login" action="${pageContext.request.contextPath}/UserServlet?mark=0" class="InputPanel" method="post">
            <div id="accountG" class="InputGroup">
                <span><label for="account">账号</label></span>
                <span id="accountE" class="error"></span>
                <input id="account" name="account" type="text">
            </div>
            <div id="passwordG" class="InputGroup">
                <span><label for="password">密码</label></span>
                <span id="passwordE" class="error"></span>
                <input id="password" name="password" type="password">
            </div>
            <input id="loginButton" class="Button Button_Circle Circle_80" type="submit" value="登录">
        </form>


        <div id="register" action="" class="InputPanel">
            <div id="newAccountG" class="InputGroup">
                <span><label for="newAccount">新账号</label></span>
                <span id="newAccountE" class="error"></span>
                <input id="newAccount" name="newAccount" type="text">
            </div>
            <div id="newNameG" class="InputGroup">
                <span><label for="newName">姓名</label></span>
                <span id="newNameE" class="error"></span>
                <input id="newName" name="account" type="text">
            </div>
            <div id="newPasswordG" class="InputGroup">
                <span><label for="password">新密码</label></span>
                <span id="newPasswordE" class="error"></span>
                <input id="newPassword" name="password" type="password">
            </div>
            <div id="reviewPasswordG" class="InputGroup">
                <span><label for="ReviewPassword">重复密码</label></span>
                <span id="reviewPasswordE" class="error"></span>
                <input id="reviewPassword" name="reviewPassword" type="password">
            </div>
            <input id="registeredButton" class="Button Button_Circle Circle_80" type="button" value="注册">
        </div>


    </div>
</body>
</html>