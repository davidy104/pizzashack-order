<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/cart.add.js"></script>
<title></title>
</head>
<body>
	<div id="pizza-id" class="hidden">${pizza.pizzashackId}</div>
	<h2>
		<spring:message code="pizzashack.title" />
	</h2>
	<div>
		<a href="/index" class="btn btn-primary"> <spring:message
				code="pizza.label.pizzas.link" /></a> <a href="/order/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.orders.link" /></a> <a href="/cart/show"
			class="btn btn-primary"> <spring:message
				code="pizza.label.cart.link" /></a> <br>

		<div class="well">
			<abbr title="<spring:message code="view.pizza.name.title"/>">
				<spring:message code="view.pizza.name.label" />:
			</abbr>
			<c:out value="${pizza.pizzaName}" />
			<br />

			<c:if test="${not empty pizza.description}">
				<abbr title="<spring:message code="view.pizza.desc.title"/>">
					<spring:message code="view.pizza.desc.label" />:
				</abbr>
				<c:out value="${pizza.description}" />
				<br />
			</c:if>

			<c:if test="${not empty pizza.price}">
				<abbr title="<spring:message code="view.pizza.price.title"/>">
					<spring:message code="view.pizza.price.label" />
				</abbr>
				<c:out value="${pizza.price}" />
				<br />
			</c:if>
			
			<c:if test="${not empty pizza.afterDiscount}">
				<abbr title="<spring:message code="view.pizza.discountPrice.title"/>">
					<spring:message code="view.pizza.discountPrice.label" />
				</abbr>
				<c:out value="${pizza.afterDiscount}" />
				<br />
			</c:if>

			<div>
				<a id="addto-cart-link" class="btn btn-primary"> <spring:message
								code="addToCart.button.label" /></a>
			</div>
		</div>
	</div>


	<script id="template-add-cart-window"
		type="text/x-handlebars-template">
    <div id="add-cart-window" class="modal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3><spring:message code="add.cart.dialog.title"/></h3>
        </div>
        <div class="modal-body">
            <form:errors path="addToCartReq" cssClass="errorBlock" element="div" />
			<form:form action="/cart/add" id="cartAddForm" cssClass="well" commandName="addToCartReq" method="POST">
			<form:hidden path="pizzashackId" />
		<div id="control-group-pizzaname" class="control-group">
			<label> <spring:message
					code="view.pizza.name.label" />
			</label>

			<div class="controls">
				${pizza.pizzaName}
			</div>
		</div>

		<div id="control-group-amount" class="control-group">
			<label for="addToCartReq-amount"> <spring:message
					code="view.pizza.order.amount.label" />
			</label>

			<div class="controls">
				<form:input id="addToCartReq-amount" path="amount" />
			</div>
		</div>

		<div class="form-buttons">
			<button id="cancel-cart-add-button" class="btn">
				<spring:message code="close.button.label" />
			</button>
			<button id="add-cart-button" type="submit" class="btn btn-primary">
				<spring:message code="create.button.label" />
			</button>
		</div>
		</form:form>

        </div>
        
    </div>
	</script>
</body>
</html>