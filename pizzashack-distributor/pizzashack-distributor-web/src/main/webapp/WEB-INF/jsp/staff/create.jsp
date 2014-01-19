<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/staff.js"></script>
<title></title>
</head>
<body>
	<h1>
		<spring:message code="pizzashack.title" />
	</h1>

	<a href="/index" class="btn btn-primary"> <spring:message
			code="pizza.label.tasks.link" /></a>
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
		<spring:message code="staff.create.title" />
	</h3>

	<form:errors path="dept" cssClass="errorBlock" element="div" />
	<form:form action="/staff/create" cssClass="well" commandName="staff"
		method="POST">

		<div id="control-group-firstName" class="control-group">
			<label for="staff-individual-firstName"> <spring:message
					code="individual.label.firstName" />:
			</label>

			<div class="controls">
				<form:input id="staff-individual-firstName"
					path="individual.firstName" />
				<form:errors id="error-individual-firstName"
					path="individual.firstName" cssClass="help-inline" />
			</div>
		</div>

		<div id="control-group-lastName" class="control-group">
			<label for="staff-individual-lastName"> <spring:message
					code="individual.label.lastName" />:
			</label>

			<div class="controls">
				<form:input id="staff-individual-lastName"
					path="individual.lastName" />
				<form:errors id="error-individual-lastName"
					path="individual.lastName" cssClass="help-inline" />
			</div>
		</div>

		<div id="control-group-email" class="control-group">
			<label for="staff-individual-email"> <spring:message
					code="individual.label.email" />:
			</label>

			<div class="controls">
				<form:input id="staff-individual-email" path="individual.email" />
				<form:errors id="error-individual-email" path="individual.email"
					cssClass="help-inline" />
			</div>
		</div>

		<div id="control-group-identity" class="control-group">
			<label for="staff-individual-identity"> <spring:message
					code="individual.label.identity" />:
			</label>

			<div class="controls">
				<form:input id="staff-individual-identity"
					path="individual.identity" />
				<form:errors id="error-individual-identity"
					path="individual.identity" cssClass="help-inline" />
			</div>
		</div>

		<div id="control-group-role" class="control-group">
			<label for="staff-role"> <spring:message
					code="view.staff.role.label" />:
			</label>

			<div class="controls">
				<form:select path="role" items="${roles}" class="checkbox" />
				<form:errors id="error-role" path="role" cssClass="help-inline" />
			</div>
		</div>
		
		<div id="control-group-user" class="control-group">
			<label for="staff-user-userId"> <spring:message
					code="view.user.userId.label" />:
			</label>

			<div class="controls">
				<form:input id="staff-user-userId"
					path="user.userId" />
			</div>
			<div>
				<a id="add-user-link" class="btn btn-primary"> <spring:message
						code="create.user.button.label" /></a>
			</div>
			
		</div>

		<div class="form-buttons">
			<button id="cancel-staff-create-button" class="btn">
				<spring:message code="cancel.button.label" />
			</button>
			<button id="add-staff-button" type="submit" class="btn btn-primary">
				<spring:message code="create.button.label" />
			</button>
		</div>
	</form:form>



	<script id="template-add-user-window" type="text/x-handlebars-template">
    <div id="add-user-window" class="modal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3><spring:message code="createuser.title"/></h3>
        </div>
        <div class="modal-body">
			<form:form action="/user/create" cssClass="well" modelAttribute="user" method="POST">

			<div id="control-group-username" class="control-group">
				<form:label path="username"><spring:message
					code="user.label.username" />:</form:label>

			<div class="controls">
				<form:input path="username" />
			</div>
		</div>

		<div id="control-group-password" class="control-group">
			<form:label path="password"><spring:message
					code="user.label.password" />:</form:label>
			<div class="controls">
				<form:input type="password" path="password" />
				
			</div>
		</div>

		<div class="form-buttons">
			<button id="close-user-create-button" class="btn">
				<spring:message code="close.button.label" />
			</button>
			<button id="add-user-button" class="btn btn-primary">
				<spring:message code="create.button.label" />
			</button>
		</div>
	</form:form>
        </div>
    </div>
	</script>

</body>
</html>