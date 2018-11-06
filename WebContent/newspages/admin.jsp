<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>添加主题--管理后台</title>
<link href="${pageContext.request.contextPath }/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/admin.js"></script>
</head>
<body>
<div id="header">
  <div id="welcome">欢迎使用新闻管理系统！</div>
  <div id="nav">
    <div id="logo"><img src="${pageContext.request.contextPath }/images/logo.jpg" alt="新闻中国" /></div>
    <div id="a_b01"><img src="${pageContext.request.contextPath }/images/a_b01.gif" alt="" /></div>
  </div>
</div>
<div id="admin_bar">
  <div id="status">管理员： 登录  &#160;&#160;&#160;&#160; <a href="#">login out</a></div>
  <div id="channel"> </div>
</div>
<div id="main">
  <%@ include file="console_element/left.html" %>
  <div id="opt_area">    
    <script type="text/javascript" >
    function clickdel(nid){
        if (confirm("此新闻的相关评论也将删除，确定删除吗？"))
            window.location="${pageContext.request.contextPath }/news.do?opr=delete&nid="+nid;
    }
	
</script>
<img src="WEB-INF/upload/2/3/asdasdas.png">
    <ul class="classlist">
      <c:forEach items="${requestScope.list}" var="news" varStatus="i">
	    <li>
	    <c:url value="/downFile.do" var="downurl">
            <c:param name="filename" value="${news.npicpath }"></c:param>
        </c:url>
	    <img height="30px" width="50px" src="${downurl }">
	    ${news.ntitle}<span> 作者：${news.nauthor} &#160;&#160;&#160;&#160; 
<a href='${pageContext.request.contextPath }/news.do?opr=toModifyNews&nid=${news.nid}'>修改</a> &#160;&#160;&#160;&#160; 
<a href='javascript:;' onclick='delNews("${news.nid}")'>删除</a></span> </li><!-- return clickdel(${news.nid}) -->
	    <c:if test="${i.count % 5 == 0}">
          <li class='space'></li>
        </c:if>
	  </c:forEach>
	  <c:remove var="list" scope="session"/>
    </ul>
  </div>
</div>
<div id="footer">
  <%@ include file="console_element/bottom.html" %>
</div>
</body>
</html>