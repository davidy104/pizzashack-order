<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/user.js"></script>
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
		<spring:message code="place.order.title" />
	</h3>

	<form:errors path="order" cssClass="errorBlock" element="div" />
	<form:form action="/order/placeOrder" cssClass="well" commandName="order"
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
			<button id="cancel-user-create-button" class="btn">
				<spring:message code="cancel.button.label" />
			</button>
			<button id="add-user-button" type="submit" class="btn btn-primary">
				<spring:message code="create.button.label" />
			</button>
		</div>
	</form:form>
</body>
</html>