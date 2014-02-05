<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/order.place.js"></script>
<title></title>
</head>
<body>

	<h2>
		<spring:message code="pizzashack.title" />
	</h2>
	<div>
		<a href="/index" class="btn btn-primary"> <spring:message
				code="pizza.label.pizzas.link" /></a> <a href="/order/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.orders.link" /></a> <a href="/cart/show"
			class="btn btn-primary"> <spring:message
				code="pizza.label.cart.link" /></a> <br> <br>

		<div id="pizza-list">
			<c:choose>
				<c:when test="${empty cart.pizzashackItems}">
					<p>
						<spring:message code="cart.items.label.no.pizzas" />
					</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${cart.pizzashackItems}" var="item">
						<div class="well pizza-list-item">
							<c:if test="${not empty item.key.pizzaName}">
								<abbr title="<spring:message code="view.pizza.name.title"/>">
									<spring:message code="view.pizza.name.label" />
								</abbr>
								<c:out value="${item.key.pizzaName}" />
								<br />
							</c:if>


							<c:if test="${not empty item.value}">
								<abbr
									title="<spring:message code="view.pizza.order.amount.title"/>">
									<spring:message code="view.pizza.order.amount.label" />
								</abbr>
								<c:out value="${item.value}" />
								<br />
							</c:if>

						</div>
					</c:forEach>
					<div id="placeOrderOperation">
						<c:if test="${not empty readyForOrder}">
							<a id="place-order-link" class="btn btn-primary"> <spring:message
								code="pizza.label.order.link" /></a>
						</c:if>
					</div>

				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<script id="template-place-order-window"
		type="text/x-handlebars-template">
    <div id="place-order-window" class="modal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3><spring:message code="place.order.title"/></h3>
        </div>
        <div class="modal-body">
           <form:errors path="order" cssClass="errorBlock" element="div" />
	<form:form action="/order/placeOrder" id="placeForm" cssClass="well" commandName="order"
		method="POST">

		<div id="control-group-customerName" class="control-group">
			<label for="order-customer-customerName"> <spring:message
					code="view.order.customername.label" />
			</label>

			<div class="controls">
				<form:input id="order-customer-customerName" path="customer.customerName" />
				<form:errors id="error-customer.customerName" path="customer.customerName"
					cssClass="help-inline" />
			</div>
		</div>

		<div id="control-group-customerEmail" class="control-group">
			<label for="order-customer-customerEmail"><spring:message
					code="view.order.customerEmail.label" /></label>

			<div class="controls">
				<form:input id="order-customer-customerEmail" path="customer.customerEmail" />
				<form:errors id="error-customer.customerEmail" path="customer.customerEmail"
					cssClass="help-inline" />
			</div>
		</div>
		
		<div id="control-group-address" class="control-group">
			<label for="order-address"><spring:message
					code="view.order.address.label" /></label>

			<div class="controls">
				<form:input id="order-address" path="address" />
				<form:errors id="error-address" path="address"
					cssClass="help-inline" />
			</div>
		</div>

		<div class="form-buttons">
			<button id="cancel-place-order-button" class="btn">
				<spring:message code="cancel.button.label" />
			</button>
			<button id="place-order-button" type="submit" class="btn btn-primary">
				<spring:message code="create.button.label" />
			</button>
		</div>
		</form:form>
        </div>
        
    </div>
	</script>


</body>
</html>