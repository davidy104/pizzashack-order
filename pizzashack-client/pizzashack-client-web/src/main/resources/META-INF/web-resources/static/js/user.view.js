$(function() {
    $(".well").on("click", "#delete-user-link", function(e) {
        e.preventDefault();
        var contactDeleteDialogTemplate = Handlebars.compile($("#template-delete-user-confirmation-dialog").html());
        $("#view-holder").append(contactDeleteDialogTemplate());
        $("#delete-user-confirmation-dialog").modal();
    })

    $("#view-holder").on("click", "#cancel-delete-user-button", function(e) {
        e.preventDefault();
        var deleteConfirmationDialog = $("#delete-user-confirmation-dialog")
        deleteConfirmationDialog.modal('hide');
        deleteConfirmationDialog.remove();
    });

    $("#view-holder").on("click", "#delete-user-button", function(e) {
        e.preventDefault();
        $.ajax({
            type: "DELETE",
            url: "/user/" + $("#user-id").text(),
            success: function(result) {
            	Pizza.storeMessageToCache(result);
                window.location.href = "/user/list";
            }
        });
    });
    
});