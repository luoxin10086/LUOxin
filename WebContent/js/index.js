var newsData = null;//新闻数据
var index = 1;//当前页数
var num = 10;//每页条数
var total = 0;//总页数
$(function(){
	//1.发送ajax请求，加载左侧菜单
	initLeftNews();
	//2.发送ajax请求，加载中间新闻和分类
	initNewsCenter();
	
})
//1.发送ajax请求，加载左侧菜单
function initLeftNews(){
	$(".side_list").each(function(i,o){
		var tid = $(this).find("ul").attr("ntid");
		var ul = $(this).find("ul");
		$.post("news.do?opr=getNewsByTid",{"ntid":tid,"num":"5"},function(data){//就是Servlet层的newsJson
			$.each(data,function(i,o){
				ul.append("<li><a onclick='test("+o.nid+")'><b>"+o.ntitle+"</b></a></li>")
			})
		},"json")
	})
}
//2.发送ajax请求，加载中间新闻和分类
function initNewsCenter(){
	$(".content .class_date").empty();
	$.post("news.do?opr=getNewsCenter",null,function(data){
		//1.加载主题
		var str = "<li>";
		$(data.topicsList).each(function(i,o){
			str += "&emsp;<a href='javascript:topicLimitNews("+o.tid+")'>"+o.tname+"</a>&emsp;"	
			if((i+1)%10 == 0 || i == (data.topicsList.length-1)){
				str+="</li>";
				$(".content .class_date").append(str);
				str = "<li>";
			}			
		});
		//2.新闻
		newsData = data.newsList;
		total = Math.ceil(data.newsList.length/num);//向上取整
		limitNews();
	},"json")
}
//主题新闻分页 后端
function topicLimitNews(tid,pageNo,pageSize){
	pageNo = pageNo?pageNo:1;//当前页面
	pageSize = pageSize?pageSize:5;//每页显示量
	$.post("news.do?opr=getNewsLimitByNtid",{"ntid":tid,"index":pageNo,"num":pageSize},function(data){
		$(".content .classlist").empty();
		if(data.newsList.length != 0){
			//1.数据
			$.each(data.newsList,function(i,o){
				$(".content .classlist").append(
						"<li>"+
							"<a onclick='test("+o.nid+")'>"+(i+1)+"&nbsp;"+o.ntitle+"</a>"+
							"<span>"+showDay(o.ncreatedate)+"</span>"+
						"</li>");
			})
			//2.显示分页信息
			$(".content .classlist").append(
					"<li><span>"+
						"<a onclick='showPage2("+tid+",1)'>首页&nbsp;&nbsp;</a>"+
						"<a onclick='showPage2("+tid+","+(Number(data.currPageNo)-1)+")'>上一页&nbsp;&nbsp;</a>"+
						"<a>当前页："+data.currPageNo+"/"+data.totalPageCount+"</a>"+
						"<a onclick='showPage2("+tid+","+(Number(data.currPageNo)+1)+")'>下一页&nbsp;&nbsp;</a>"+
						"<a onclick='showPage2("+tid+","+data.totalPageCount+")'>末尾&nbsp;&nbsp;</a>"+
						"</span>"+
						"<select id='pageSize'><option value=5>5</option><option value=10>10</option></select></li>"
					)
		}else{
			$(".content .classlist").append("暂时没有新闻");
		}
	},"json")
}
function showPage2(tid,pageNo){//下一页触发的方法
	if(pageNo < 1){
		pageNo = 1;
	}
	topicLimitNews(tid,pageNo,$("#pageSize").val());//id改变每页条数也发生改变
}
//分页展示
function limitNews(){
	//1页 0~9 9 10*1
	//2页10~19 19 10*2
	//3页20~29
	//num * (index-1) 10 * (3-1)
			//10*(1-1)         1*10
	//清空之前数据
	$(".content .classlist").empty();
	//1.新闻数据
	for(var i=(num*(index-1));i<index*num;i++){
		if(i >= 0 && i < newsData.length){
			$(".content .classlist").append(
					"<li>" +
					"<a onclick='test("+newsData[i].nid+")'>"+(i+1)+"&nbsp;"+newsData[i].ntitle+"</a>" +
					"<span>"+showDay(newsData[i].ncreatedate)+"</span>" +
				"</li>");
			if((i+1)%5 == 0){
				$(".content .classlist").append("<br>");
			}
		}else{
			$(".content .classlist").append("<p>&nbsp;</p>")
			if((i+1)%5 == 0){
				$(".content .classlist").append("<br>");
			}
			
		}
	}
	/*//2.显示分页信息
	$(".content .classlist").append(
			"<li><span>" +
			"<a onclick='showPage(1)'>首页&nbsp;&nbsp;</a>" +
			"<a onclick='prePage()'>上一页&nbsp;&nbsp;</a>" +
			"<n>当前页："+index+"/"+total+"&nbsp;&nbsp;</n>" +
			"<a onclick='nextPage()'>下一页&nbsp;&nbsp;</a>" +
			"<a onclick='showPage("+total+")'>末页&nbsp;&nbsp;</a>" +
			"</span></li>"
			);*/
	//2.显示分页信息
	$(".content .classlist").append(
			"<li><span>" +
			"<a onclick='showPage(1)'>首页&nbsp;&nbsp;</a>" +
			"<a onclick='showPage("+(index-1)+")'>上一页&nbsp;&nbsp;</a>" +
			"<n>当前页："+index+"/"+total+"&nbsp;&nbsp;</n>" +
			"<a onclick='showPage("+(index+1)+")'>下一页&nbsp;&nbsp;</a>" +
			"<a onclick='showPage("+total+")'>末页&nbsp;&nbsp;</a>" +
			"</span></li>"
			);
}
function test(nid){
	$(".content .classlist").empty();
	$(".picnews").empty();
	$(".content").load("news.do?opr=readNew",{"nid":nid})
}
/*//上一页
function prePage(){
	if(index>1){
		index--;
		limitNews();
	}
}
//下一页
function nextPage(){
	if(index < total){
		index++;
		limitNews();
	}
}
//控制分页参数
function showPage(page){
	index = page;
	limitNews();
}*/
function showPage(page){
	index = page;
	if(index > total){
		index = total
	}else if(index < 1){
		index = 1
	}
	limitNews();
}
function showDay(date){
    var time = "";
    //获取年月日
    var now = new Date(date);
    var year = now.getFullYear();
    var month = setTime(now.getMonth()+1);
    var day = setTime(now.getDate());
    var hours = setTime(now.getHours());
    var minute = setTime(now.getMinutes());
    var second = setTime(now.getSeconds());
    time = year+"-"+month+"-"+day+"-"+hours+":"+minute+":"+second;
    return time;
}
function setTime(time){
    if(time < 10){time = "0"+time;}
    return time;
}