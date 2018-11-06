//新增评论
function addComment(){
		if(checkComment()){
			var cauthor = $("#cauthor").val();//发言人      获取value值
			alert(cauthor)
			var nid = $("#nid").val();//新闻id
			var cip = $("#cip").val();//发言人ip
			var ccontent = $("#ccontent").val();//内容
			alert(cauthor+","+nid+","+cip+","+ccontent)
			$.post("comment.do",
					{"opr":"addComment",
				"cauthor":cauthor,
				"nid":nid,
				"cip":cip,
				"ccontent":ccontent
				},
				function(data){
					if(data){
						alert("发表评论成功！");
						str = "<tr>"+"<td>留言人：</td>"+
						"<td>"+data.cauthor+"</td>"+
						"<td>IP:</td>"+
						"<td>"+data.cip+"</td>"+
						"<td>留言时间：</td>"+
						"<td>"+showDay(data.cdate)+"</td>"+
						"</tr>"+
						"<tr>"+"<td colspan='6'>"+data.ccontent+"</td>"+
						"</tr>"+
						"<tr>"+"<td colspan='6'><hr></td>"+
						"</tr>"
							$("#news_comments").prepend(str);
						}else{
							alert("发表评论失败!")
						}
					},
					"json");
		}
}
//检查评论
function checkComment(){
	var cauthor = document.getElementById("cauthor");
	var content = document.getElementById("ccontent");
	if(cauthor.value == ""){
		alert("用户名不能为空！！");
		return false;
	}else if(content.value == ""){
		alert("用户名不能为空！！");
		return false;
	}
	alert("1111111111");
	return true;
}
function showDay(date){
    var time = "";
    if(date){
    	//获取年月日
        var now = new Date(date);
        var year = now.getFullYear();
        var month = setTime(now.getMonth()+1);
        var day = setTime(now.getDate());
        var hours = setTime(now.getHours());
        var minute = setTime(now.getMinutes());
        var second = setTime(now.getSeconds());
        time = year+"-"+month+"-"+day+" "+hours+":"+minute+":"+second;
    }
    return time;
}
function setTime(time){
    if(time < 10){time = "0"+time;}
    return time;
}