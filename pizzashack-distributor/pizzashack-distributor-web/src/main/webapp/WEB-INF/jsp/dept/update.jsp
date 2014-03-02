<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/dept.js"></script>
<title></title>
</head>
<body>
	<h2>
		<spring:message code="pizzashack.title" />
	</h2>

	<div id="dept-id" class="hidden">${dept.deptId}</div>
	<a href="/workflow/list" class="btn btn-primary"> <spring:message
			code="pizza.label.workflows.link" /></a>
	<a href="/department/list" class="btn btn-primary"> <spring:message
			code="pizza.label.depts.link" /></a>
	<a href="/staff/list" class="btn btn-primary"> <spring:message
			code="pizza.label.staffs.link" /></a>
	<a href="/user/list" class="btn btn-primary"> <spring:message
			code="pizza.label.users.link" /></a>
	<a href="/logout" class="btn btn-primary"> <spring:message
			code="pizza.label.logout.link" /></a>
	<br>
	<h3>
		<spring:message code="dept.update.title" />
	</h3>
	<form:errors path="dept" cssClass="errorBlock" element="div" />
	<form:form action="/department/update" cssClass="well"
		commandName="dept" method="POST">
		<form:hidden path="deptId" />

		<div id="control-group-deptName" class="control-group">
			<label for="dept-deptName"> <spring:message
					code="dept.label.deptName" />:
			</label>

			<div class="controls">
				<form:input id="dept-deptName" path="deptName" />
				<form:errors id="error-deptName" path="deptName"
					cssClass="help-inline" />
			</div>
		</div>

		<div class="form-buttons">
			<button id="cancel-dept-udpate-button" class="btn">
				<spring:message code="cancel.button.label" />
			</button>
			<button id="update-dept-button" type="submit" class="btn btn-primary">
				<spring:message code="update.button.label" />
			</button>
		</div>
	</form:form>
</body>
</html>