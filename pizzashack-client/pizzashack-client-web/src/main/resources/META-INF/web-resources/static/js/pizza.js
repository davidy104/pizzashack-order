var messageCacheOptions = {
    expires: 30000
};

var Pizza = {
    addErrorMessage:function (message) {
        var alertTemplate = Handlebars.compile($("#template-alert-message-error").html());
        $("#message-holder").html(alertTemplate({message:message}));
        $("#alert-message-error").alert().delay(5000).fadeOut("fast", function() { $(this).remove(); });
    },

    addMessage:function (message) {
        var alertTemplate = Handlebars.compile($("#template-alert-message").html());
        $("#message-holder").html(alertTemplate({message:message}));
        $("#alert-message").alert().delay(5000).fadeOut("fast", function() { $(this).remove(); })
    },
    getErrorMessageCacheKey: function() {
        return "pizzas.errorMessage";
    },
    getErrorMessageFromCache: function() {
        var errorMessage = amplify.store(Pizza.getErrorMessageCacheKey());
        amplify.store(Pizza.getErrorMessageCacheKey(), null);
        return errorMessage;
    },
    getMessageCacheKey: function() {
        return "pizzas.message";
    },
    getMessageFromCache: function() {
        var message = amplify.store(Pizza.getMessageCacheKey());
        amplify.store(Pizza.getMessageCacheKey(), null);
        return message;
    },
    storeErrorMessageToCache: function(message) {
        amplify.store(Pizza.getErrorMessageCacheKey(), message, messageCacheOptions);
    },
    storeMessageToCache: function(message) {
        amplify.store(Pizza.getMessageCacheKey(), message, messageCacheOptions);
    }

};

$(document).ready(function () {

    var errorMessage = $(".errroblock");
    if (errorMessage.length > 0) {
    	Pizza.addErrorMessage(errorMessage.text());
    }
    else {
        errorMessage = Pizza.getErrorMessageFromCache();
        if (errorMessage) {
        	Pizza.addErrorMessage(errorMessage);
        }
    }

    var feedbackMessage = $(".messageblock");
    if (feedbackMessage.length > 0) {
    	Pizza.addMessage(feedbackMessage.text());
    }
    else {
        feedbackMessage = Pizza.getMessageFromCache();
        if (feedbackMessage) {
        	Pizza.addMessage(feedbackMessage);
        }
    }
});

$(document).bind('ajaxError', function(error, response) {
    if (response.status == "404") {
        window.location.href = "/error/404";
    }
    else {
        window.location.href = "/error/error";
    }
});
