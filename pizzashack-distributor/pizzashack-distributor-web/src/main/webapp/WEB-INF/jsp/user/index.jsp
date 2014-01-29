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
		<spring:message code="userlist.title" />
	</h3>

	<div>
		<form:errors path="user" cssClass="errorBlock" element="div" />
		<form:form action="/user/search" cssClass="well" commandName="user"
			method="POST">
			<div id="control-group-username" class="control-group">
				<label for="user-username"><spring:message
						code="user.label.username" />:</label>
				<div class="controls">
					<form:input id="user-username" path="username" />
					<form:errors id="error-username" path="username"
						cssClass="help-inline" />
				</div>
			</div>
			<div class="form-buttons">
				<button id="search-user-button" type="submit"
					class="btn btn-primary">
					<spring:message code="search.button.label" />
				</button>
				<a href="/user/create" class="btn btn-primary"> <spring:message
						code="pizza.label.create.link" /></a>
			</div>
		</form:form>


		<div id="pizza-list">
			<c:choose>
				<c:when test="${empty users}">
					<p>
						<spring:message code="pizza.list.label.no.users" />
					</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${users}" var="user">
						<div class="well pizza-list-item">
							<c:if test="${not empty user.username}">
								<abbr title="<spring:message code="view.user.username.title"/>">
									<spring:message code="view.user.username.label" />
								</abbr>
								<c:out value="${user.username}" />
								<br />
							</c:if>

							<c:if test="${not empty user.createTime}">
								<abbr title="<spring:message code="createtime.title"/>">
									<spring:message code="createtime.label" />
								</abbr>
								<c:out value="${user.createTime}" />
								<br />
							</c:if>

							<a href="/user/${user.userId}" class="btn btn-primary"><spring:message
									code="pizza.label.show.link" /></a>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>