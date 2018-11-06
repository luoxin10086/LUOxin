//删除新闻
function delNews(nid){
	$(".classlist").empty();
	$.post("news.do",
			{"opr":"delete",
				"nid":nid
				},
				function(data){
					if(data){
						$.each(data,function(i,o){
							$(".classlist").append(
								    "<li>"+
								    "<img height='30px' width='50px' src='${downurl }'>"+
								    o.ntitle+"<span> 作者："+o.nauthor+"&#160;&#160;&#160;&#160;"+ 
							"<a href='/ThNews/news.do?opr=toModifyNews&nid="+o.nid+"'>修改</a>&#160;&#160;&#160;&#160;"+
							"<a onclick='delNews()' id="+o.nid+">删除</a></span> </li>"	
							);
						})
					}
				},"json")
			}
