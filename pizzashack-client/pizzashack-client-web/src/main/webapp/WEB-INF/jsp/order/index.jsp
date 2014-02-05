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

	<a href="/index" class="btn btn-primary"> <spring:message
			code="pizza.label.pizzas.link" /></a>
	<a href="/order/list" class="btn btn-primary"> <spring:message
			code="pizza.label.orders.link" /></a>
	<a href="/cart/show" class="btn btn-primary"> <spring:message
			code="pizza.label.cart.link" /></a>
	<br>
	<h3>
		<spring:message code="order.search.title" />
	</h3>
	<br>
	<div>
		<form:errors path="order" cssClass="errorBlock" element="div" />
		<form:form action="/order/list" cssClass="well" commandName="order"
			method="POST">
			<div id="control-group-customer-customerEmail" class="control-group">
				<label for="order-customer-customerEmail"><spring:message
						code="view.order.customerEmail.label" /></label>
				<div class="controls">
					<form:input id="order-customer-customerEmail"
						path="customer.customerEmail" />
				</div>
			</div>
			<div class="form-buttons">
				<button id="search-dept-button" type="submit"
					class="btn btn-primary">
					<spring:message code="search.button.label" />
				</button>
			</div>
		</form:form>

		<br>

		<div id="pizza-list">
			<c:choose>
				<c:when test="${empty orders}">
					<p>
						<spring:message code="pizza.label.no.orders" />
					</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${orders}" var="order">
						<div class="well pizza-list-item">

							<abbr
								title="<spring:message code="view.pizza.order.orderNo.title"/>">
								<spring:message code="view.pizza.order.orderNo.label" />
							</abbr>
							<c:out value="${order.orderNo}" />
							<br />


							<c:if test="${not empty order.qty}">
								<abbr
									title="<spring:message code="view.pizza.order.qty.title"/>">
									<spring:message code="view.pizza.order.qty.label" />
								</abbr>
								<c:out value="${order.qty}" />
								<br />
							</c:if>

							<c:if test="${not empty order.totalPrice}">
								<abbr
									title="<spring:message code="view.pizza.order.totalPrice.title"/>">
									<spring:message code="view.pizza.order.totalPrice.label" />
								</abbr>
								<c:out value="${order.totalPrice}" />
								<br />
							</c:if>

							<c:if test="${not empty order.status}">
								<abbr
									title="<spring:message code="view.pizza.order.status.title"/>">
									<spring:message code="view.pizza.order.status.label" />
								</abbr>
								<c:out value="${order.status}" />
								<br />
							</c:if>

							<c:if test="${not empty order.orderTime}">
								<abbr
									title="<spring:message code="view.pizza.order.orderTime.title"/>">
									<spring:message code="view.pizza.order.orderTime.label" />
								</abbr>
								<c:out value="${order.orderTime}" />
								<br />
							</c:if>

							<c:if test="${not empty order.deliverTime}">
								<abbr
									title="<spring:message code="view.pizza.order.deliverTime.title"/>">
									<spring:message code="view.pizza.order.deliverTime.label" />
								</abbr>
								<c:out value="${order.deliverTime}" />
								<br />
							</c:if>

							<a href="/order/${order.orderNo}" class="btn btn-primary"><spring:message
									code="pizza.label.show.link" /></a>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>