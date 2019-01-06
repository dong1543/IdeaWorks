<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>错误页面</title>
    <style>
        *{
            margin: 0;
            padding: 0;
        }
        body{
            background: url("${pageContext.request.contextPath}/image/11.jpg") no-repeat;
            background-size: 100%;
        }
        #content{
            margin-top: 5%;
            margin-left: 20%;
        }
        h1,h2{
            color: #d4d4d4;
            font-family: 方正兰亭超细黑简体;
        }
        h1{
            font-size:75px;
        }
        h2{
            margin-top: 20px;
            font-size: 35px;
        }
    </style>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
</head>
<body>
    <div id="content">
        <h1>错误:</h1>
        <h2>对不起服务器发生错误，将在<span id="time" time="5">5</span>秒后返回首页</h2>
    </div>
</body>
<script>
    showTime();
    function showTime()
    {
        var time=$("#time").attr("time");
        var url="http://localhost:8080/CityBookLibrary/";
        $("#time").html(time);
        if(time==0){
            $(window).attr('location',url);
        }else{
            $("#time").attr("time",time-1);
        }
        setTimeout("showTime()", 1000);
    }
</script>
</html>