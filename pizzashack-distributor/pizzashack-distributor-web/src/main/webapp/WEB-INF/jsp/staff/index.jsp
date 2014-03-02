<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.home.js"></script>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
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
		<spring:message code="staff.search.title" />
	</h3>
	<br>
	<div>
		<form:errors path="staff" cssClass="errorBlock" element="div" />
		<form:form action="/staff/search" cssClass="well" commandName="staff"
			method="POST">
			<div id="control-group-deptName class="control-group">
				<label for="staff-individual-firstName"><spring:message
						code="individual.label.firstName" />:</label>
				<div class="controls">
					<form:input id="staff-individual-firstName"
						path="individual.firstName" />
					<form:errors id="error-individual-firstName"
						path="individual.firstName" cssClass="help-inline" />
				</div>
			</div>
			<div class="form-buttons">
				<button id="search-staff-button" type="submit"
					class="btn btn-primary">
					<spring:message code="search.button.label" />
				</button>

			</div>
		</form:form>
		<a href="/staff/create" class="btn btn-primary"> <spring:message
				code="pizza.label.create.link" /></a> <br>

		<div id="pizza-list">
			<c:choose>
				<c:when test="${empty staffs}">
					<p>
						<spring:message code="pizza.list.label.no.staffs" />
					</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${staffs}" var="staff">
						<div class="well pizza-list-item">

							<c:if test="${not empty staff.individual}">
								<abbr
									title="<spring:message code="view.staff.firstName.title"/>">
									<spring:message code="view.staff.firstName.label" />:
								</abbr>
								<c:out value="${staff.individual.firstName}" />
								<br />

								<abbr title="<spring:message code="view.staff.lastName.title"/>">
									<spring:message code="view.staff.lastName.label" />:
								</abbr>
								<c:out value="${staff.individual.lastName}" />
								<br />

							</c:if>

							<c:if test="${not empty staff.role}">
								<abbr title="<spring:message code="view.staff.role.title"/>">
									<spring:message code="view.staff.role.label" />:
								</abbr>
								<c:out value="${staff.role}" />
								<br />
							</c:if>

							<c:if test="${not empty staff.level}">
								<abbr title="<spring:message code="view.staff.level.title"/>">
									<spring:message code="view.staff.level.label" />:
								</abbr>
								<c:out value="${staff.level}" />
								<br />
							</c:if>


							<c:if test="${not empty staff.createDate}">
								<abbr
									title="<spring:message code="view.staff.createDate.title"/>">
									<spring:message code="view.staff.createDate.label" />:
								</abbr>
								<c:out value="${staff.createDate}" />
								<br />
							</c:if>

							<a href="/staff/${staff.staffId}" class="btn btn-primary"><spring:message
									code="pizza.label.show.link" /></a>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>