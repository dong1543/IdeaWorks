$(document).ready(function () {
	/*定义一个全局的变量Current Reciver Account存放接收信息好友的账号，方便检测及发送方向正确
	 * 其次如果是集合形式则可以实现多好友在线聊天，同时也可以实现讨论组的形式，但暂不完善*/
	var currentReceiverAccount;
	var currentSenderAccount=$("#senderAccount").html();
	var websocket = null;
	
	$("#colseChatBox").click(function () {
        $("#chatContainer").slideUp(300);
        $("#chatPanel").empty();
        currentReceiverAccount=null;
    })
	function cleanFeedBack() {
        $("#feedback").slideUp(500);
    }
	function showFeedback(data){
		$("#feedback").html(data);
		$("#feedback").slideDown(500);
        setTimeout(cleanFeedBack,2000);
	}
	//异步获取未读消息，并绘制在chatPanel上
	function getUnreadMessage(senderAccount,receiverAccount){
		var url="/SimpleChat/MessageServlet";
		var param={
				"mark":0,
				"senderAccount":senderAccount,
				"receiverAccount":receiverAccount
		}
		$.post(url,param,function(data){
			if(data!=="NoItem"){
				//解析json数据
				var unreadMessages = $.parseJSON(data);
				for(var i=0;i<unreadMessages.length;i++){
					drawFriendPopUnread(unreadMessages[i]);
				}
			}
		})
	}
	//绘制朋友消息泡
	function drawFriendPopUnread(message){
		$("#chatPanel").append(
			"<div class=\"friend messagePop\">"+
                "<img src=\"/SimpleChat/userHeadImg/"+currentReceiverAccount+".jpg\" alt=\"\" class=\"userHead\">"+
                "<span class=\"messageText\">"+message.message+"</span>"+
            "</div>"
		)
	}
	function drawFriendPop(message){
		$("#chatPanel").append(
			"<div class=\"friend messagePop\">"+
                "<img src=\"/SimpleChat/userHeadImg/"+currentReceiverAccount+".jpg\" alt=\"\" class=\"userHead\">"+
                "<span class=\"messageText\">"+message+"</span>"+
            "</div>"
		)
	}
	//绘制自己消息泡
	function drawMyselfPop(message){
		$("#chatPanel").append(
				"<div class=\"my messagePop\">"+
	                "<img src=\"/SimpleChat/userHeadImg/"+currentSenderAccount+".jpg\" alt=\"\" class=\"userHead\">"+
	                "<span class=\"messageText\">"+message+"</span>"+
	            "</div>"
			)
	}
	/*使用事件委托将friend列表中的添加好友头像点击开启websocket连接*/
	$("#friendList").delegate("img.userHead","click",function (){
		//获取好友信息
		var currentLi=$(this).parent();
		var receiverAccount=$(currentLi).children(".friendAccount").html();
		var receiverName=$(currentLi).children(".userName").html();
		//检测当前接收消息用户是否为好友，非好友不允许聊天(AJAX同步请求)
		var result="false";
		var url="/SimpleChat/UserServlet";
		var param={
			"mark":10,
			"friendAccount":receiverAccount
		}
		$.post(url,param,function(data){
			if(data==="true"){
				currentReceiverAccount=receiverAccount;
				//打开聊天面板，更换接收消息对象
		        var oldFriendName=$("#friendName").html();
		        if(receiverName===oldFriendName){
		        }else{
		            $("#chatPanel").empty();
		            $("#sendMessage").val("");
		            $("#friendName").html(receiverName);
		        }
		        $("#chatContainer").slideDown(300);
				//获取该好友的未读消息，好友为sender方,用户为receiver方,所以参数要变
		        getUnreadMessage(currentReceiverAccount,currentSenderAccount);
		        //消除当前好友未读标记
		        $(currentLi).children("span.unreadFlag").remove();
			}else{
				showFeedback("该用户非好友");
			}
		})
	})
	
	/*登出*/
    $("#logout").click(function (){
    	//断开websocket连接，再告知服务器httpSession取消
    	closeWebSocket();
    	var url="/SimpleChat/UserServlet?mark=4";
    	$.get(url,function(data){
    		window.location.replace("http://localhost:8080/SimpleChat");
    	});
    })
	/*WebSocket前端模块*/
	
	//开启WebSocket连接，一登录就开启，注销关闭
    if(websocket==null){
    	websocket = new WebSocket("ws://localhost:8080/SimpleChat/ChatWebSocket/"+currentSenderAccount);
    }
	
	/*接收与发送为主要处理数据方法*/
	
	//接收消息
    websocket.onmessage = function (event) {
    	var jsonstr=event.data;
    	var json=$.parseJSON(jsonstr);
    	var messageSenderAccount=json["senderAccount"];
    	var message=json["message"];
    	//发消息的人如果是当前窗口聊天的人则将消息绘制，否则推送未知消息，并标识该好友，再使用异步请求将消息存入数据库中
    	if(messageSenderAccount===currentReceiverAccount){
    		drawFriendPop(message);
    	}else{
    		showFeedback("有好友未读消息");
    		saveUnreadMessage(jsonstr);
    		markFriend(messageSenderAccount);
    	}
    }
    function markFriend(senderAccount){
    	var friendAccounts=$("span.friendAccount");
    	for(var i=0;i<friendAccounts.length;i++){
    		var friendAccount=friendAccounts[i];
    		var account=$(friendAccount).html();
    		if(account===senderAccount){
    			$(friendAccount).parent().prepend(
    					"<span class=\"unreadFlag\"></span>"
    			)
    		}
    	}
    }
    function saveUnreadMessage(jsonstr){
    	var url="/SimpleChat/MessageServlet";
    	var param={
    		"mark":1,
    		data:jsonstr
    	}
    	$.post(url,param)
    }
    /*消息发送按钮与快捷键的监听*/
    $(document).keydown(function (event) {
        if(event.keyCode=="83" && event.altKey){
            sendMessage();
        }
    })
    $("#sendButton").click(sendMessage);
    
    function sendMessage() {
        var sendMessage=$("#sendMessage").val();
        $("#chatPanel").append(
            "<div class=\"my messagePop\">\n" +
            "      <img src=\"/SimpleChat/userHeadImg/"+currentSenderAccount+".jpg\" alt=\"\" class=\"userHead\">\n" +
            "      <span class=\"messageText\">"+sendMessage+"</span>\n" +
            "</div>");
        $("#sendMessage").val("");
        var senderAccount=currentSenderAccount;
        var receiverAccount=currentReceiverAccount;
        var json={};
        json["senderAccount"]=senderAccount;
        json["receiverAccount"]=receiverAccount;
        json["message"]=sendMessage;
        var jsonstr=JSON.stringify(json);
        websocket.send(jsonstr);
    }
    /*辅助监听，都是必写的*/
    
	//错误处理
	websocket.onerror = function () {
         showFeedback("websocket连接错误");
    };
 
    //连接成功
    websocket.onopen = function () {
    	 showFeedback("登录成功，欢迎你");
    }
 
     //连接关闭
     websocket.onclose = function () {
    	 showFeedback("退出系统");
     }
 
     //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接
     window.onbeforeunload = function () {
         closeWebSocket();
     }
     //关闭连接
     function closeWebSocket() {
         websocket.close();
     }
})