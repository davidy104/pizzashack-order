<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<head>
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap-responsive.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css"/>

    <script type="text/javascript" src="/static/js/vendor/jquery-1.7.2.js"></script>

    <script type="text/javascript" src="/static/js/vendor/json2.js"></script>
    <script type="text/javascript" src="/static/js/vendor/amplify.js"></script>

    <script type="text/javascript" src="/static/js/vendor/bootstrap-transition.js"></script>
    <script type="text/javascript" src="/static/js/vendor/bootstrap-alert.js"></script>
    <script type="text/javascript" src="/static/js/vendor/bootstrap-collapse.js"></script>
    <script type="text/javascript" src="/static/js/vendor/bootstrap-modal.js"></script>
    <script type="text/javascript" src="/static/js/vendor/handlebars-1.0.0.beta.6.js"></script>

    <script type="text/javascript" src="/static/js/pizza.js"></script>

    <sitemesh:write property="head"/>

    <title><spring:message code="pizza.shack.title"/></title>
</head>
<body>
<div id="page-wrapper" class="page">
    <div id="header">
        <div class="navbar">
            <div class="navbar-inner">
                <div class="container">

                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <div class="nav-collapse">
                        <ul id="navigation" class="nav">
                            <li id="navi-homepage-link"><a href="/"><spring:message code="header.home.link.label"/></a></li>
                            <li class="divider-vertical"></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="content">
        <div id="message-holder">
            <c:if test="${feedbackMessage != null}">
                <div class="messageblock hidden">${feedbackMessage}</div>
            </c:if>
            <c:if test="${errorMessage != null}">
                <div class="errorblock hidden">${errorMessage}</div>
            </c:if>
        </div>
        <div id="view-holder" class="view-holder">
            <sitemesh:write property="body"/>
        </div>
    </div>
    <div id="footer">

    </div>
</div>
<script id="template-alert-message-error" type="text/x-handlebars-template">
    <div id="alert-message-error" class="alert alert-error fade in">
        <a class="close" data-dismiss="alert">&times;</a>
        {{message}}
    </div>
</script>

<script id="template-alert-message" type="text/x-handlebars-template">
    <div id="alert-message" class="alert alert-success fade in">
        <a class="close" data-dismiss="alert">&times;</a>
        {{message}}
    </div>
</script>
</body>
</html>