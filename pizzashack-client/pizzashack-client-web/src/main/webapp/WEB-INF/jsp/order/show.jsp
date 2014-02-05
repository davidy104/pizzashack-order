<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/order.billing.js"></script>
<title></title>
</head>
<body>
	<div id="order-no" class="hidden">${order.orderNo}</div>
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

		<div class="well">

			<abbr title="<spring:message code="view.order.orderNo.title"/>">
				<spring:message code="view.order.orderNo.label" />
			</abbr>
			<c:out value="${order.orderNo}" />
			<br /> <abbr
				title="<spring:message code="view.order.orderStatus.title"/>">
				<spring:message code="view.order.orderStatus.label" />
			</abbr>
			<c:out value="${order.status}" />
			<br /> <abbr
				title="<spring:message code="view.order.orderTime.title"/>">
				<spring:message code="view.order.orderTime.label" />
			</abbr>
			<c:out value="${order.orderTime}" />
			<br />

			<c:if test="${not empty order.deliverTime}">
				<abbr title="<spring:message code="view.order.deliverTime.title"/>">
					<spring:message code="view.order.deliverTime.label" />
				</abbr>
				<c:out value="${order.deliverTime}" />
				<br />
			</c:if>

			<abbr title="<spring:message code="view.order.qty.title"/>"> <spring:message
					code="view.order.qty.label" />
			</abbr>
			<c:out value="${order.qty}" />
			<br /> <abbr
				title="<spring:message code="view.order.totalPrice.title"/>">
				<spring:message code="view.order.totalPrice.label" />
			</abbr>
			<c:out value="${order.totalPrice}" />
			<br /> <abbr
				title="<spring:message code="view.order.address.title"/>"> <spring:message
					code="view.order.address.label" />
			</abbr>
			<c:out value="${order.address}" />
			<br />
			<c:if test="${not empty order.customer}">
				<abbr title="<spring:message code="view.order.customername.title"/>">
					<spring:message code="view.order.customername.label" />
				</abbr>
				<c:out value="${order.customer.customerName}" />
				<br />
				<abbr
					title="<spring:message code="view.order.customerEmail.title"/>">
					<spring:message code="view.order.customerEmail.label" />
				</abbr>
				<c:out value="${order.customer.customerEmail}" />
				<br />
			</c:if>
		</div>

		<div id="pizza-list">
			<c:choose>
				<c:when test="${empty order.orderDetailsSet}">
					<p>
						<spring:message code="pizza.label.no.orders" />
					</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${order.orderDetailsSet}" var="orderDetails">
						<div class="well pizza-list-item">

							<abbr title="<spring:message code="view.pizza.name.title"/>">
								<spring:message code="view.pizza.name.label" />:
							</abbr>
							<c:out value="${orderDetails.pizzaName}" />
							<br /> <abbr
								title="<spring:message code="view.order.qty.title"/>"> <spring:message
									code="view.order.qty.label" />:
							</abbr>
							<c:out value="${orderDetails.qty}" />
							<br /> <abbr
								title="<spring:message code="view.order.totalPrice.title"/>">
								<spring:message code="view.order.totalPrice.label" />:
							</abbr>
							<c:out value="${orderDetails.totalPrice}" />
							<br />

						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>


		<div id="orderOperations">
			<c:if test="${not empty pendingOnBilling}">
				<a id="order-billing-link" class="btn btn-primary"> <spring:message
						code="billing.button.label" /></a>
			</c:if>

		</div>
	</div>

	<script id="template-order-billing-window"
		type="text/x-handlebars-template">
    <div id="order-billing-window" class="modal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3><spring:message code="order.billing.dialog.title"/></h3>
        </div>
        <div class="modal-body">
            <form:errors path="billing" cssClass="errorBlock" element="div" />
			<form:form action="/order/billing" id="billingForm" cssClass="well" commandName="billing" method="POST">
			<form:hidden path="orderNo" />
			<form:hidden path="billingAmount" />
		<div id="control-group-orderNo" class="control-group">
			<label for="billing-orderNo"> <spring:message
					code="view.order.orderNo.label" />
			</label>

			<div class="controls">
				<form:input id="billing-orderNo" path="orderNo"  disabled="true" />
			</div>
		</div>

		<div id="control-group-amount" class="control-group">
			<label for="billing-billingAmount"> <spring:message
					code="view.pizza.order.amount.label" />
			</label>

			<div class="controls">
				<form:input id="billing-billingAmount" path="billingAmount"  disabled="true"/>
			</div>
		</div>
		
		<div id="control-group-paymode" class="control-group">
			<label for="billing-account-paymode"> <spring:message
					code="view.pizza.billing.account.paymode.label" />
			</label>

			<div class="controls">
				<form:select path="account.paymode" items="${paymodes}" />
			</div>
		</div>

		<div id="control-group-accountNo" class="control-group">
			<label for="billing-account-accountNo"> <spring:message
					code="view.pizza.billing.account.accountNo.label" />
			</label>

			<div class="controls">
				<form:input id="billing-account-accountNo" path="account.accountNo" />
			</div>
		</div>

		<div id="control-group-securityNo" class="control-group">
			<label for="billing-account-securityNo"> <spring:message
					code="view.pizza.billing.account.securityNo.label" />
			</label>

			<div class="controls">
				<form:input id="billing-account-securityNo" path="account.securityNo" />
			</div>
		</div>

		<div id="control-group-expireDate" class="control-group">
			<label for="billing-account-expireDate"> <spring:message
					code="view.pizza.billing.account.expireDate.label" />
			</label>

			<div class="controls">
				<form:input id="billing-account-expireDate" path="account.expireDate" />
			</div>
		</div>

		<div class="form-buttons">
			<button id="cancel-billing-create-button" class="btn">
				<spring:message code="close.button.label" />
			</button>
			<button id="add-billing-button" type="submit" class="btn btn-primary">
				<spring:message code="create.button.label" />
			</button>
		</div>
		</form:form>

        </div>
        
    </div>
	</script>



</body>
</html>