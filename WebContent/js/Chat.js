$(document).ready(function () {
	$(".function").click(function(){
		$("#feedback").html("该功能暂未开放");
		$("#feedback").slideDown(500);
        setTimeout(cleanFeedBack,2000);
	})
    $("#openFriends").click(function () {
        $("#functionMenu").slideToggle(300);
        $("#friends").slideToggle();
    })
    $("#openUserDetail").click(function () {
        $("#userDetail").slideToggle();
    })
    
    /*使用事件委托将friend列表中的添加按钮与删除，防止异步刷新后事件监听不起作用*/
    $("#friendList").delegate("i.fa-plus-square-o","click",function () {
        var friend=$(this).parent();
        var friendId=$(friend).children(".friendId").html();
        var url="/SimpleChat/UserServlet";
        var param={
        		"mark":6,
        		"friendId":friendId
        }
        $.post(url,param,function(data){
        	if(data==="true"){
        		$("#feedback").html("好友添加成功");
        		$("#feedback").slideDown(500);
                setTimeout(cleanFeedBack,2000);
                var url="/SimpleChat/UserServlet";
        		var param={
        				"mark":7
        		}
        		$.post(url,param,function(data){
        			$("#friendList").empty();
            		var users = $.parseJSON(data);
            		redrawFriendList(users);
        		})
        	}else{
        		$("#feedback").html(data);
        		$("#feedback").slideDown(500);
                setTimeout(cleanFeedBack,2000);
        	}
        })
    })
    $("#friendList").delegate("i.fa-minus-square-o","click",function () {
    	var friend=$(this).parent();
        var friendId=$(friend).children(".friendId").html();
        var url="/SimpleChat/UserServlet";
        var param={
        		"mark":8,
        		"friendId":friendId
        }
        $.post(url,param,function(data){
        	$("#feedback").html("好友删除成功");
    		$("#feedback").slideDown(500);
            setTimeout(cleanFeedBack,2000);
            var url="/SimpleChat/UserServlet";
    		var param={
    				"mark":7
    		}
    		$.post(url,param,function(data){
    			$("#friendList").empty();
        		var users = $.parseJSON(data);
        		redrawFriendList(users);
    		})
        })
    })
    
    /*异步文件上传*/
    $("#submit").click(function () {
                                                            //构建Formdata对象
        var formData=new FormData();
                                                            //为formdata对象添加文件数据（二进制数据）
        formData.append("file",$("#file").prop("files")[0]);   //第一个参数一般为name值
                                                          //使用jquery的ajax异步
        $.ajax({
            contentType:"multipart/form-data",
            processData: false,                            // 告诉jQuery不要去处理发送的数据
            url:"/SimpleChat/UserServlet?mark=3",
            type:"POST",
            data:formData,                                    //索要传输的数据
            dataType:"text",                                 //告知返回的参数
            contentType:false,
            
            xhr:function(){
            	myXhr = $.ajaxSettings.xhr();  
                //检查upload属性是否存在  
                //绑定progress事件的回调函数  
                myXhr.upload.onprogress=function(event){
                   if(event.lengthComputable){
                	   var percent =Math.round((event.loaded/event.total)*100);
                	   $("#progress").val(percent);
                    }
                }
                return myXhr; 			//xhr对象返回给jQuery使用  
            },
            success :function(data){
            	$("#feedback").html(data);
        		$("#feedback").slideDown(500);
                setTimeout(cleanFeedBack,2000);
                window.location.reload();
            }
        })
    })
    /*根据关键字搜索用户*/
    $("#search").click(function(){
    	var keyWords=$("#keyWords").val();
    	//当输入值为空则重新查询自己的好友列表
    	if(keyWords===""){
    		var url="/SimpleChat/UserServlet";
    		var param={
    				"mark":7
    		}
    	}else{
    		var url="/SimpleChat/UserServlet";
        	var param={
        			"mark":5,
        			"keyWords":keyWords
        	}
    	}
    	/*回调函数重绘朋友列表*/
    	$.post(url,param,function(data){
    		$("#friendList").empty();
    		var users = $.parseJSON(data);
    		redrawFriendList(users);
    		$("#keyWords").val("");
    	})
    })
    /*重绘朋友列表*/
    function redrawFriendList(users){
    	for(var i=0;i<users.length;i++){
			var user=users[i];
			$("#friendList").append(
	                "<li>\n" +"<span class=\"friendId\">"+user.id+"</span>"+"<span class=\"friendAccount\">"+user.account+"</span>"+
	                "         <img class=\"userHead\" src=\"/SimpleChat/userHeadImg/"+user.account+".jpg\">\n" +
	                "         <h1 class=\"userName\">"+user.name+"</h1>\n" +
	                "         <i  class=\"fa fa-minus-square-o\"></i>\n" +
	                "         <i  class=\"fa fa-plus-square-o\"></i>\n" +
	                "</li>"
	         )
		}
    }
    /*修改个人信息,光标移除修改字段*/
    $(".InputGroup input").blur(function(){
    	var newValue=$(this).val();
    	var field=$(this).attr("name");
    	var url="/SimpleChat/UserServlet";
    	var param={
    			"mark":9,
    			"value":newValue,
    			"field":field
    	}
    	$.post(url,param,function(data){
    		$("#feedback").html(data);
    		$("#feedback").slideDown(500);
            setTimeout(cleanFeedBack,2000);
    	})
    })
    /*feedback*/
    function cleanFeedBack() {
        $("#feedback").slideUp(500);
    }
    
})