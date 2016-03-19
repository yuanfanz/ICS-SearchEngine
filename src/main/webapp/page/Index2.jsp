<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]>&lt;!&ndash;> <html lang="en"> &lt;!&ndash;<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Search ICS</title>
  <link rel="stylesheet" href="css/style.css">
  <!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
</head>

<!-- <body background="about-history-overview.jpg";> -->
<body>
    <div style="width: 100%; height:500px">
        <center>
  <section class="container">
    <form class="search" method="post" action="/search">
        <input type="search" name="key" placeholder="Search ICS..." autocomplete="off" value="${key }">
        <a href=""><button type="submit">Search</button></a>
      </form>
  </section>
        </center>
        <center>
        <c:forEach items="${data }" var="d">
        <li><a href="${d.url }">${d.url }</a>--${d.score }</li>
        </c:forEach>
        </center>
        <div style="height: 360px"></div>
  <section class="about">
    <p class="about-links">
      <a href="http://www.cssflow.com/snippets/search-dropdown" target="_parent">Team</a>
      <a href="https://github.uci.edu/yenfengc/CS221_Proj" target="_parent">Source Code</a>
    </p>
    <p class="about-author">
      Supported by  <br>Yen Feng Cheng & Chien-Lin Chen <br>Yuanfan Zhang
    </p>
  </section>
    </div>
</body>
</html>