<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.home.js"></script>
<title></title>
</head>
<body>
	<h1>
		<spring:message code="pizzashack.title" />
	</h1>
	<div>
		<a href="/index" class="btn btn-primary"> <spring:message
				code="pizza.label.tasks.link" /></a> <a href="/department/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.depts.link" /></a> <a href="/staff/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.staffs.link" /></a> <a href="/user/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.users.link" /></a> <a href="/logout"
			class="btn btn-primary"> <spring:message
				code="pizza.label.logout.link" /></a>


	</div>
</body>
</html>