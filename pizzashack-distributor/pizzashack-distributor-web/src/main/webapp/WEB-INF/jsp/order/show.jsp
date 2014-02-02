<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/orderHistory.show.js"></script>
<title></title>
</head>
<body>
	<div id="orderProcess-id" class="hidden">${orderProcess.orderProcessId}</div>
	<div id="orderProcess-no" class="hidden">${orderProcess.order.orderNo}</div>
	<h2>
		<spring:message code="pizzashack.title" />
	</h2>
	<div>
		<a href="/index" class="btn btn-primary"> <spring:message
				code="pizza.label.tasks.link" /></a> <a href="/orderQuery/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.orders.link" /></a> <a href="/logout"
			class="btn btn-primary"> <spring:message
				code="pizza.label.logout.link" /></a> <br>

		<div class="well">
			<c:if test="${not empty orderProcess.order}">
				<abbr title="<spring:message code="view.order.orderNo.title"/>">
					<spring:message code="view.order.orderNo.label" />:
				</abbr>
				<c:out value="${orderProcess.order.orderNo}" />
				<br />

				<abbr title="<spring:message code="view.order.orderStatus.title"/>">
					<spring:message code="view.order.orderStatus.label" />:
				</abbr>
				<c:out value="${orderProcess.order.status}" />
				<br />

				<abbr title="<spring:message code="view.order.orderTime.title"/>">
					<spring:message code="view.order.orderTime.label" />:
				</abbr>
				<c:out value="${orderProcess.order.orderTime}" />
				<br />

				<c:if test="${not empty orderProcess.order.deliverTime}">
					<abbr title="<spring:message code="view.order.deliverTime.title"/>">
						<spring:message code="view.order.deliverTime.label" />:
					</abbr>
					<c:out value="${orderProcess.order.deliverTime}" />
					<br />
				</c:if>

				<abbr title="<spring:message code="view.order.qty.title"/>">
					<spring:message code="view.order.qty.label" />:
				</abbr>
				<c:out value="${orderProcess.order.qty}" />
				<br />

				<abbr title="<spring:message code="view.order.totalPrice.title"/>">
					<spring:message code="view.order.totalPrice.label" />:
				</abbr>
				<c:out value="${orderProcess.order.totalPrice}" />
				<br />
			</c:if>

			<c:if test="${not empty orderProcess.operator.username}">
				<abbr
					title="<spring:message code="view.order.operator.username.title"/>">
					<spring:message code="view.order.operator.username.label" />:
				</abbr>
				<c:out value="${orderProcess.operator.username}" />
				<br />
			</c:if>

			<c:if test="${not empty orderProcess.pendingActivity}">
				<abbr
					title="<spring:message code="view.order.pendingActivity.name.title"/>">
					<spring:message code="view.order.pendingActivity.name.label" />:
				</abbr>
				<c:out value="${orderProcess.pendingActivity.name}" />
				<br />

				<abbr
					title="<spring:message code="view.order.pendingActivity.type.title"/>">
					<spring:message code="view.order.pendingActivity.type.label" />:
				</abbr>
				<c:out value="${orderProcess.pendingActivity.type}" />
				<br />

				<abbr
					title="<spring:message code="view.order.pendingActivity.assignee.title"/>">
					<spring:message code="view.order.pendingActivity.assignee.label" />:
				</abbr>
				<c:out value="${orderProcess.pendingActivity.assignee}" />
				<br />
			</c:if>
		</div>

		<div id="pizza-list">
			<c:choose>
				<c:when test="${empty orderProcess.order.orderDetailsSet}">
					<p>
						<spring:message code="pizza.list.label.no.orders" />
					</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${orderProcess.order.orderDetailsSet}"
						var="orderDetails">
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


		<div id="showHistory">
			<a id="show-history-link" class="btn btn-primary"> <spring:message
					code="show.history.button.label" /></a>
		</div>

	</div>


	<script id="template-show-histories-window"
		type="text/x-handlebars-template">
    	<div id="show-histories-window" class="modal">
        	<div class="modal-header">
            	<button class="close" data-dismiss="modal">×</button>
            	<h3><spring:message code="show.histories.title"/></h3>
        	</div>
        	<div class="modal-body">
					<div id="history-list" class="pizza-list-item">
					</div>
        	</div>

			<div class="modal-footer">
			 	<a id="cancel-show-histories-button" href="#" class="btn"><spring:message code="cancel.button.label"/></a>
        	</div>

    	</div>
	</script>
</body>
</html>