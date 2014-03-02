<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.home.js"></script>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/pizza.add.js"></script>
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
		<spring:message code="deptsearch.title" />
	</h3>
	<br>
	<div>
		<form:errors path="dept" cssClass="errorBlock" element="div" />
		<form:form action="/department/search" cssClass="well"
			commandName="dept" method="POST">
			<div id="control-group-deptName class="control-group">
				<label for="dept-deptName"><spring:message
						code="dept.label.deptName" />:</label>
				<div class="controls">
					<form:input id="dept-deptName" path="deptName" />
					<form:errors id="error-deptName" path="deptName"
						cssClass="help-inline" />
				</div>
			</div>
			<div class="form-buttons">
				<button id="search-dept-button" type="submit"
					class="btn btn-primary">
					<spring:message code="search.button.label" />
				</button>
			</div>
		</form:form>

		<a href="/department/create" class="btn btn-primary"> <spring:message
				code="pizza.label.create.link" /></a> <br>

		<div id="pizza-list">
			<c:choose>
				<c:when test="${empty depts}">
					<p>
						<spring:message code="pizza.list.label.no.depts" />
					</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${depts}" var="dept">
						<div class="well pizza-list-item">
							<c:if test="${not empty dept.deptName}">
								<abbr title="<spring:message code="view.dept.deptName.title"/>">
									<spring:message code="view.dept.deptName.label" />:
								</abbr>
								<c:out value="${dept.deptName}" />
								<br />
							</c:if>

							<c:if test="${not empty dept.createDate}">
								<abbr title="<spring:message code="createdate.title"/>">
									<spring:message code="createdate.label" />:
								</abbr>
								<c:out value="${dept.createDate}" />
								<br />
							</c:if>

							<a href="/department/${dept.deptId}" class="btn btn-primary"><spring:message
									code="pizza.label.show.link" /></a>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>