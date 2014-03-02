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

	<div>
		<div id="pizza-list">
			<c:choose>
				<c:when test="${empty workflows}">
					<p>
						<spring:message code="pizza.list.label.no.workflows" />
					</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${workflows}" var="workflow">
						<div class="well pizza-list-item">
							<c:if test="${not empty workflow.name}">
								<abbr title="<spring:message code="view.workflow.name.title"/>">
									<spring:message code="view.workflow.name.label" />
								</abbr>
								<c:out value="${workflow.name}" />
								<br />
							</c:if>
							
							<c:if test="${not empty workflow.category}">
								<abbr title="<spring:message code="view.workflow.category.title"/>">
									<spring:message code="view.workflow.category.label" />
								</abbr>
								<c:out value="${workflow.category}" />
								<br />
							</c:if>

							<c:if test="${not empty workflow.createTime}">
								<abbr title="<spring:message code="createtime.title"/>">
									<spring:message code="createtime.label" />
								</abbr>
								<c:out value="${workflow.createTime}" />
								<br />
							</c:if>

							<a href="/workflow/${workflow.wfId}" class="btn btn-primary"><spring:message
									code="pizza.label.show.link" /></a>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>