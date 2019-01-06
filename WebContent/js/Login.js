$(document).ready(function () {
    $("#background").particleground({
        dotColor: "#f4f4f4",
        lineColor: "#f4f4f4",
        directionY: "down",
        lineWidth: 1,
        particleRidius: 1
    })
    
    $("#openLogin").click(function () {
        $("#login").slideDown(500);
        $("#register").slideUp(10);
    })

    $("#openRegister").click(function () {
        $("#register").slideDown(500);
        $("#login").slideUp(10);
    })

    $("#registeredButton").click(function () {
    	var password=$("#newPassword").val();
    	var reviewPassword=$("#reviewPassword").val();
    	if(password===reviewPassword){
    		var params = {
        			"account":$("#newAccount").val()
        		};
        	var url="/SimpleChat/UserServlet?mark=2";
            $.get(url,params,function(data){
            	if(data=="true"){
            		creatNewUser();
            	}else if(data=="false"){
            		$("#feedback").html("用户名重复");
            		$("#feedback").slideDown(500);
                    setTimeout(cleanFeedBack,2000);
            	}
            })
    	}else{
    		$("#feedback").html("两次密码不正确");
    		$("#feedback").slideDown(500);
            setTimeout(cleanFeedBack,2000);
    	}
    	
    })
    function cleanFeedBack() {
        $("#feedback").slideUp(500);
    }
    function creatNewUser(){
    	var jsonObj={
    			"account":$("#newAccount").val(),
    			"name":$("#newName").val(),
    			"password":$("#newPassword").val()
    	}
		var jsonStr = JSON.stringify(jsonObj);
		var url = "/SimpleChat/UserServlet";
		var params={
				"mark":1,
				"data":jsonStr
		}
		$.post(url,params,function(data){
			$("#feedback").html(data);
    		$("#feedback").slideDown(500);
            setTimeout(cleanFeedBack,2000);
		})
    }
})

