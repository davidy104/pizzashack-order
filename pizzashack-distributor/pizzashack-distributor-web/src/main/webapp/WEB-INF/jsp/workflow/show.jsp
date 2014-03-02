<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<script type="text/javascript" src="/static/js/pizza.form.js"></script>
<script type="text/javascript" src="/static/js/workflow.view.js"></script>
<title></title>
</head>
<body>
	<div id="workflow-wfId" class="hidden">${workflow.wfId}</div>
	<h2>
		<spring:message code="pizzashack.title" />
	</h2>
	<div>
		<a href="/workflow/list" class="btn btn-primary"> <spring:message
				code="pizza.label.workflows.link" /></a> <a href="/department/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.depts.link" /></a> <a href="/staff/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.staffs.link" /></a> <a href="/user/list"
			class="btn btn-primary"> <spring:message
				code="pizza.label.users.link" /></a> <a href="/logout"
			class="btn btn-primary"> <spring:message
				code="pizza.label.logout.link" /></a> <br>

		<div class="well">
			<abbr title="<spring:message code="view.workflow.name.title"/>">
				<spring:message code="view.workflow.name.label" />
			</abbr>
			<c:out value="${workflow.name}" />
			<br />

			<c:if test="${not empty workflow.category}">
				<abbr title="<spring:message code="view.workflow.category.title"/>">
					<spring:message code="view.workflow.category.label" />
				</abbr>
				<c:out value="${workflow.category}" />
				<br />
			</c:if>
			
			<c:if test="${not empty workflow.deployId}">
				<abbr title="<spring:message code="view.workflow.deployId.title"/>"> <spring:message
						code="view.workflow.deployId.label" />
				</abbr>
				<c:out value="${workflow.deployId}" />
				<br />
			</c:if>
			
			<c:if test="${not empty workflow.processDefinitionKey}">
				<abbr title="<spring:message code="view.workflow.processDefinitionKey.title"/>"> <spring:message
						code="view.workflow.processDefinitionKey.label" />
				</abbr>
				<c:out value="${workflow.processDefinitionKey}" />
				<br />
			</c:if>
			
			<c:if test="${not empty workflow.processDefinitionId}">
				<abbr title="<spring:message code="view.workflow.processDefinitionId.title"/>"> <spring:message
						code="view.workflow.processDefinitionId.label" />
				</abbr>
				<c:out value="${workflow.processDefinitionId}" />
				<br />
			</c:if>
			

			<c:if test="${not empty workflow.createTime}">
				<abbr title="<spring:message code="createtime.title"/>"> <spring:message
						code="createtime.label" />
				</abbr>
				<c:out value="${workflow.createTime}" />
				<br />
			</c:if>

			<div>
				<a id="show-flowimage-link" class="btn btn-primary"> <spring:message
						code="showflow.button.label" /></a>
			</div>
		</div>
	</div>


	<script id="template-show-flowimage-window"
		type="text/x-handlebars-template">
    <div id="show-flowimage-window" class="modal">
        <div class="modal-header">
            <button class="close" data-dismiss="modal">×</button>
            <h3><spring:message code="showflow.title"/></h3>
        </div>
        <div class="modal-body">
            <div id="flowimage"></div>
        </div>
        <div class="modal-footer">
            <a id="close-show-flowimage-button" href="#" class="btn"><spring:message code="close.button.label"/></a>
        </div>
    </div>
	</script>
</body>
</html>