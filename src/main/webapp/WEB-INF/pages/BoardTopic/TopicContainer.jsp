<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: liaosheng
  Date: 2017/3/28
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.jxufe.boy.dao.Page" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link rel='stylesheet' type="text/css" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <%
        Page pageTopic = (Page) request.getAttribute("pagedTopic");
        List topicList = pageTopic.getResult();
    %>
</head>
<body>
<% if (topicList.size()==0){%>
<blockquote>
    <p>该版块是空滴~~</p>
</blockquote>
<%}else{%>
<c:forEach var="topic" items="${pagedTopic.result}">
    <blockquote>

        <c:if test="${USER_CONTEXT.userType == 2 || isboardManager}">
            <input type="checkbox" name="topicIds" value="${topic.topicId}"/>
        </c:if>

        <a  href="<c:url value="/board/listTopicPosts-${topic.topicId}.html"/>">
            <c:if test="${topic.digest > 0}">
                <font color=red>★</font>
            </c:if>
                ${topic.topicTitle}
        </a>
            ${topic.user.userName}
            ${topic.replies}
            <%--<td>--%>
            <%--<fmt:formatDate pattern="yyyy-MM-dd HH:mm"--%>
            <%--value="${topic.createTime}" />--%>
            <%--</td>--%>
            <%--<td>--%>
            <%--<fmt:formatDate pattern="yyyy-MM-dd HH:mm"--%>
            <%--value="${topic.lastPost}" />--%>
            <%--</td>--%>
    </blockquote>
</c:forEach>
<%}%>
<script src='http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js'></script>
<script src='http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js'></script>
</body>
</html>