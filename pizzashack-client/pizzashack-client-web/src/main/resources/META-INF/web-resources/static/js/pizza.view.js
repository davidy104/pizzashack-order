$(function() {
	
    $(".well").on("click", "#delete-order-link", function(e) {
        e.preventDefault();
        var contactDeleteDialogTemplate = Handlebars.compile($("#template-delete-order-confirmation-dialog").html());
        $("#view-holder").append(contactDeleteDialogTemplate());
        $("#delete-order-confirmation-dialog").modal();
    })

    $("#view-holder").on("click", "#cancel-order-button", function(e) {
        e.preventDefault();
        var deleteConfirmationDialog = $("#delete-order-confirmation-dialog")
        deleteConfirmationDialog.modal('hide');
        deleteConfirmationDialog.remove();
    });

    $("#view-holder").on("click", "#delete-order-button", function(e) {
        e.preventDefault();
        $.ajax({
            type: "DELETE",
            url: "/order/" + $("#order-id").text(),
            success: function(result) {
            	Pizza.storeMessageToCache(result);
                window.location.href = "/order/myOrders";
            }
        });
    });
    
    
    $(".well").on("click", "#deliver-order-link", function(e) {
        e.preventDefault();
        var contactDeleteDialogTemplate = Handlebars.compile($("#template-deliver-order-confirmation-dialog").html());
        $("#view-holder").append(contactDeleteDialogTemplate());
        $("#deliver-order-confirmation-dialog").modal();
    })
    
    $("#view-holder").on("click", "#deliver-order-button", function(e) {
        e.preventDefault();
        var deleteConfirmationDialog = $("#deliver-order-confirmation-dialog")
        deleteConfirmationDialog.modal('hide');
        deleteConfirmationDialog.remove();
    });
    
    $("#view-holder").on("click", "#deliver-order-button", function(e) {
        e.preventDefault();
        $.ajax({
            type: "PUT",
            url: "/order/deliver/" + $("#order-id").text(),
            success: function(result) {
            	Pizza.storeMessageToCache(result);
                window.location.href = "/order/myOrders";
            }
        });
    });
    
});