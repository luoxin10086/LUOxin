<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻中国</title>
<link href="css/read.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/news_read.js"></script>

</head>
<body>
      <ul class="classlist">
        <table width="80%" align="center">
          <tr width="100%">
            <td colspan="2" align="center">${news.ntitle}</td>
          </tr>
          <tr>
            <td colspan="2"><hr />
            </td>
          </tr>
          <tr>
            <td align="center">作者：${news.nauthor}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
            <td align="left">发布时间：<fmt:formatDate value="${news.ncreatedate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          </tr>          
          <tr>
            <td colspan="2" align="center"></td>
          </tr>
          <tr>
            <td colspan="2">${news.ncontent}</td>
          </tr>
          <tr>
            <td colspan="2"><hr />
            </td>
          </tr>
        </table>
      </ul>
      <ul class="classlist">
        <table id="news_comments" width="80%" align="center">
        <c:choose>
        <c:when test="${empty news.comments}">
            <tr><td colspan="6"> 暂无评论！ </td></tr>
            <tr>
                <td colspan="6"><hr />
                </td>
            </tr>
        </c:when>
        <c:otherwise>
            <c:forEach items="${news.comments}" var="comment">
                <tr>
                    <td> 留言人： </td>
                    <td>${comment.cauthor}</td>
                    <td> IP： </td>
                    <td>${comment.cip}</td>
                    <td> 留言时间： </td>
                    <td><fmt:formatDate value="${comment.cdate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <td colspan="6">${comment.ccontent}</td>
                </tr>
                <tr>
                    <td colspan="6"><hr />
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
        </c:choose>
        </table>
      </ul>
      <ul class="classlist">
        <li>
          <input type="hidden" id="nid" name="nid" value="${news.nid}" />
          <table width="80%" align="center">
            <tr>
              <td> 评 论 </td>
            </tr>
            <tr>
              <td> 用户名： </td>
              <td>
              <c:choose>
              <c:when test="${not empty sessionScope.admin}">
                  <input id="cauthor" name="cauthor" value="${sessionScope.admin}" readonly="readonly" style="border:0px;"/>
              </c:when>
              <c:otherwise>
                  <input id="cauthor" name="cauthor" value="这家伙很懒什么也没留下"/>
              </c:otherwise>
              </c:choose>
                IP：
                <input name="cip" id="cip" value="${pageContext.request.remoteAddr}" readonly="readonly" style="border:0px;"/>
              </td>
            </tr>
            <tr>
              <td colspan="2"><textarea name="ccontent" id="ccontent" cols="70" rows="10">test评论</textarea>
              </td>
            </tr>
            <tr><td><input name="submit" value="发  表" type="button" onclick="addComment()"/>
              </td></tr>
          </table>
        </li>
      </ul>

</body>
</html>
