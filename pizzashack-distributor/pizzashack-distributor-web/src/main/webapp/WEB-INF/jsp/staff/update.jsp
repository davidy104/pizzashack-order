<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/staff.update.js"></script>
<title></title>
</head>
<body>
	<h2>
		<spring:message code="pizzashack.title" />
	</h2>

	<div id="staff-id" class="hidden">${staff.staffId}</div>
	<div id="staff-deptIds" class="hidden">${staff.selectedDeptIdsStr}</div>

	<a href="/department/list" class="btn btn-primary"> <spring:message
				code="pizza.label.depts.link" /></a> <a href="/staff/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.staffs.link" /></a> <a href="/user/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.users.link" /></a> <a href="/logout"
			class="btn btn-primary"> <spring:message
				code="pizza.label.logout.link" /></a>
	<br>
	<h3>
		<spring:message code="staff.update.title" />
	</h3>
	<form:errors path="staff" cssClass="errorBlock" element="div" />
	<form:form action="/staff/update" cssClass="well" commandName="staff"
		method="POST">
		<form:hidden path="staffId" />
		<form:hidden path="selectedDeptIdsStr" />

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

		<div id="control-group-level" class="control-group">
			<label for="staff-level"> <spring:message
					code="view.staff.level.label" />:
			</label>

			<div class="controls">
				<form:select path="level" items="${levels}" class="checkbox" />
				<form:errors id="error-role" path="level" cssClass="help-inline" />
			</div>
		</div>

			<div id="control-group-depts" class="control-group">
			<label for="staff-depts"> <spring:message
					code="view.staff.selectedDepts.label" />:
			</label>


			<div class="controls">
				<a id="select-depts-link" class="btn"> <spring:message
						code="select.button.label" /></a>

				<form:textarea path="selectedDeptNamesStr" readOnly="true"></form:textarea>
			</div>
		</div>


		<div class="form-buttons">
			<button id="cancel-staff-udpate-button" class="btn">
				<spring:message code="cancel.button.label" />
			</button>
			<button id="update-staff-button" type="submit" class="btn btn-primary">
				<spring:message code="update.button.label" />
			</button>
		</div>

	</form:form>


	<script id="template-select-depts-window"
		type="text/x-handlebars-template">
    	<div id="select-depts-window" class="modal">
        	<div class="modal-header">
            	<button class="close" data-dismiss="modal">×</button>
            	<h3><spring:message code="select.depts.title"/></h3>
        	</div>
        	<div class="modal-body">
					<div id="pizza-list">
					</div>
        	</div>

			<div class="modal-footer">
			 	<a id="cancel-select-depts-button" href="#" class="btn"><spring:message code="cancel.button.label"/></a>
            	<a id="select-depts-button" href="#" class="btn btn-primary"><spring:message code="add.button.label"/></a>
        	</div>

    	</div>
	</script>

</body>
</html>