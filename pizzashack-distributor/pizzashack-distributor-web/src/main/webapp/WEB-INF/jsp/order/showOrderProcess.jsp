<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/staff.view.js"></script>
<title></title>
</head>
<body>
	<div id="staff-id" class="hidden">${staff.staffId}</div>
	<h2>
		<spring:message code="pizzashack.title" />
	</h2>
	<div>
		<a href="/department/list" class="btn btn-primary"> <spring:message
				code="pizza.label.depts.link" /></a> <a href="/staff/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.staffs.link" /></a> <a href="/user/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.users.link" /></a> <a href="/logout"
			class="btn btn-primary"> <spring:message
				code="pizza.label.logout.link" /></a>
	<br>

		<div class="well">
			<staff>
			<c:if test="${not empty staff.individual.firstName}">
			<abbr
				title="<spring:message code="view.staff.firstName.title"/>">
				<spring:message
					code="view.staff.firstName.label" />:
			</abbr> <c:out value="${staff.individual.firstName}" /> <br />
			</c:if>

			<c:if test="${not empty staff.individual.lastName}">
			<abbr
				title="<spring:message code="view.staff.lastName.title"/>">
				<spring:message
					code="view.staff.lastName.label" />:
			</abbr> <c:out value="${staff.individual.lastName}" /> <br />
			</c:if>

			<c:if test="${not empty staff.individual.email}">
			<abbr
				title="<spring:message code="view.staff.email.title"/>">
				<spring:message
					code="view.staff.email.label" />:
			</abbr> <c:out value="${staff.individual.email}" /> <br />
			</c:if>

			<c:if test="${not empty staff.individual.identity}">
			<abbr
				title="<spring:message code="view.staff.identity.title"/>">
				<spring:message
					code="view.staff.identity.label" />:
			</abbr> <c:out value="${staff.individual.identity}" /> <br />
			</c:if>

			<c:if test="${not empty staff.role}">
			<abbr
				title="<spring:message code="view.staff.role.title"/>">
				<spring:message
					code="view.staff.role.label" />:
			</abbr> <c:out value="${staff.role}" /> <br />
			</c:if>

			<c:if test="${not empty staff.level}">
			<abbr
				title="<spring:message code="view.staff.level.title"/>">
				<spring:message
					code="view.staff.level.label" />:
			</abbr> <c:out value="${staff.level}" /> <br />
			</c:if>

			<c:if test="${not empty staff.createDate}">
			<abbr
				title="<spring:message code="view.staff.createDate.title"/>">
				<spring:message
					code="view.staff.createDate.label" />:
			</abbr> <c:out value="${staff.createDate}" /> <br />
			</c:if>

			<c:if test="${not empty staff.user.username}">
			<abbr
				title="<spring:message code="view.user.username.title"/>">
				<spring:message
					code="view.user.username.label" />:
			</abbr> <c:out value="${staff.user.username}" /> <br />
			</c:if>

			<c:if test="${not empty staff.selectedDeptIdsStr}">
			<abbr
				title="<spring:message code="view.dept.deptName.title"/>">
				<spring:message
					code="view.dept.deptName.label" />:
			</abbr> <c:out value="${staff.selectedDeptIdsStr}" /> <br />
			</c:if>

			</staff>

			<div>
				<a href="/staff/update/${staff.staffId}" class="btn btn-primary"> <spring:message
						code="update.button.label" />
				</a> <a id="delete-staff-link" class="btn btn-primary"> <spring:message
						code="delete.button.label" /></a>
			</div>
		</div>
	</div>


	<script id="template-delete-staff-confirmation-dialog"
		type="text/x-handlebars-template">
    <div id="delete-staff-confirmation-dialog" class="modal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3><spring:message code="delete.staff.dialog.title"/></h3>
        </div>
        <div class="modal-body">
            <p><spring:message code="delete.staff.dialog.message"/></p>
        </div>
        <div class="modal-footer">
            <a id="cancel-delete-staff-button" href="#" class="btn"><spring:message code="cancel.button.label"/></a>
            <a id="delete-staff-button" href="#" class="btn btn-primary"><spring:message code="delete.button.label"/></a>
        </div>
    </div>
	</script>
</body>
</html>