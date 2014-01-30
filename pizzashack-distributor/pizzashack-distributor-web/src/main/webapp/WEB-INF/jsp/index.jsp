<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.home.js"></script>
<title></title>
</head>
<body>
	<h2>
		<spring:message code="pizzashack.title" />
	</h2>
	<div>
		<a href="/index" class="btn btn-primary"> <spring:message
				code="pizza.label.tasks.link" /></a>
				
		<a href="" class="btn btn-primary"> <spring:message
				code="pizza.label.orders.link" /></a>
				
		<a href="/logout" class="btn btn-primary"> <spring:message
				code="pizza.label.logout.link" /></a> <br>
	</div>

	<div id="pizza-list">
		<c:choose>
			<c:when test="${empty orderProcesses}">
				<p>
					<spring:message code="pizza.list.label.no.tasks" />
				</p>
			</c:when>
			<c:otherwise>
				<c:forEach items="${orderProcesses}" var="orderProcess">
					<div class="well pizza-list-item">

						<c:if test="${not empty orderProcess.order}">
							<abbr title="<spring:message code="view.order.orderNo.title"/>">
								<spring:message code="view.order.orderNo.label" />:
							</abbr>
							<c:out value="${orderProcess.order.orderNo}" />
							<br />

							<abbr
								title="<spring:message code="view.order.orderStatus.title"/>">
								<spring:message code="view.order.orderStatus.label" />:
							</abbr>
							<c:out value="${orderProcess.order.status}" />
							<br />
						</c:if>


						<c:if test="${not empty orderProcess.createTime}">
							<abbr
								title="<spring:message code="view.order.createTime.title"/>">
								<spring:message code="view.order.createTime.label" />:
							</abbr>
							<c:out value="${orderProcess.createTime}" />
							<br />
						</c:if>

						<c:if test="${not empty orderProcess.operator}">
							<abbr
								title="<spring:message code="view.order.operator.username.title"/>">
								<spring:message code="view.order.operator.username.label" />:
							</abbr>
							<c:out value="${orderProcess.operator.username}" />
							<br />
						</c:if>

						<a href="/orderProcess/${orderProcess.orderProcessId}"
							class="btn btn-primary"><spring:message
								code="pizza.label.show.link" /></a>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>

</body>
</html>