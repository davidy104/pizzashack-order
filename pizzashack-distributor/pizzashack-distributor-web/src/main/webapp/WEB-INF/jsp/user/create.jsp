<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/user.js"></script>
<title></title>
</head>
<body>
	<h2>
		<spring:message code="pizzashack.title" />
	</h2>
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
		<spring:message code="createuser.title" />
	</h3>

	<form:errors path="user" cssClass="errorBlock" element="div" />
	<form:form action="/user/create" cssClass="well" commandName="user"
		method="POST">

		<div id="control-group-username" class="control-group">
			<label for="user-username"> <spring:message
					code="user.label.username" />:
			</label>

			<div class="controls">
				<form:input id="user-username" path="username" />
				<form:errors id="error-username" path="username"
					cssClass="help-inline" />
			</div>
		</div>

		<div id="control-group-password" class="control-group">
			<label for="user-password"><spring:message
					code="user.label.password" />:</label>

			<div class="controls">
				<form:input id="user-password" type="password" path="password" />
				<form:errors id="error-password" path="password"
					cssClass="help-inline" />
			</div>
		</div>

		<div class="form-buttons">
			<button id="cancel-user-create-button" class="btn">
				<spring:message code="cancel.button.label" />
			</button>
			<button id="add-user-button" type="submit" class="btn btn-primary">
				<spring:message code="create.button.label" />
			</button>
		</div>
	</form:form>
</body>
</html>