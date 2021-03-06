<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/user.view.js"></script>
<title></title>
</head>
<body>
	<div id="user-id" class="hidden">${user.userId}</div>
	<h2>
		<spring:message code="pizzashack.title" />
	</h2>
	<div>
		<a href="/workflow/list" class="btn btn-primary"> <spring:message
				code="pizza.label.workflows.link" /></a> <a href="/department/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.depts.link" /></a> <a href="/staff/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.staffs.link" /></a> <a href="/user/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.users.link" /></a> <a href="/logout"
			class="btn btn-primary"> <spring:message
				code="pizza.label.logout.link" /></a> <br>

		<div class="well">
			<user> <abbr
				title="<spring:message code="view.user.username.title"/>"> <spring:message
					code="view.user.username.label" />:
			</abbr> <c:out value="${user.username}" /> <br />

			<c:if test="${not empty user.createTime}">
				<abbr title="<spring:message code="view.user.createTime.title"/>">
					<spring:message code="view.user.createTime.label" />:
				</abbr>
				<c:out value="${user.createTime}" />
				<br />
			</c:if> </user>

			<div>
				<a href="/user/update/${user.userId}" class="btn btn-primary"> <spring:message
						code="update.button.label" />
				</a> <a id="delete-user-link" class="btn btn-primary"> <spring:message
						code="delete.button.label" /></a>
			</div>
		</div>
	</div>


	<script id="template-delete-user-confirmation-dialog"
		type="text/x-handlebars-template">
    <div id="delete-user-confirmation-dialog" class="modal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3><spring:message code="delete.user.dialog.title"/></h3>
        </div>
        <div class="modal-body">
            <p><spring:message code="delete.user.dialog.message"/></p>
        </div>
        <div class="modal-footer">
            <a id="cancel-delete-user-button" href="#" class="btn"><spring:message code="cancel.button.label"/></a>
            <a id="delete-user-button" href="#" class="btn btn-primary"><spring:message code="delete.button.label"/></a>
        </div>
    </div>
	</script>
</body>
</html>