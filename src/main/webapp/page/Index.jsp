<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="x-ua-compatible" content="ie=7" />
<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1,user-scalable=no"/>
<title>${!empty key? key:"" }</title>
<link href="css/css.css" rel="stylesheet"/> 
</head>
<body>
  <table id="container">
    <tr id="header">
	 <td colspan="2">
	    <table class="top">
		 <tr class="menu">
		   <td>
		     <table class="nav">
			  <tr>
			   <c:forEach items="${menuList}" var="menu">
			     <td><h3><a href="${ctx }/${menu.url }.html" title="${menu.cname }" target="_self">${menu.cname }</a></h3></td>
			   </c:forEach>
			  </tr>
			 </table>
		   </td>
		 </tr>
		 <tr><td>
<form action="/search">
   <span class="search"><input type="text" class="word" name="key" placeholder="Search ICS..." value="${key }"/></span>
   <span><input class="searchBtn" type="submit" value="Search"/></span>
</form>
		</td></tr>
		</table>
	 </td>
	</tr>
	<tr id="content">
	 <td id="left">
	   <c:forEach items="${data}" var="d">
	   <table class="item">
			 <tr>
			  <td class="td_title" colspan="2">
			  <h1 class="h1class"><a href="${d.url }">${d.title }</a></h1></td>
			 </tr>
			 <tr><td class="itemSummary">${d.content }</td></tr>
			 <tr class="spread"><td height="25px"><div class="official"><span>Website: <a href="${d.url }">${d.url }</a> </span>&nbsp;</div></td></tr>
		   <tr class="spread"><td height="25px"><div class="official"><span>Score: ${d.score} </span>&nbsp;</div></td></tr>
	   </table>
	   </c:forEach>
	   <div class="clear"></div>
	 </td>
	 <td id="right">
</td>
	</tr>
  </table>
</body>
</html>