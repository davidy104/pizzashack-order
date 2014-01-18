$(function() {
    $(".well").on("click", "#delete-dept-link", function(e) {
        e.preventDefault();
        var contactDeleteDialogTemplate = Handlebars.compile($("#template-delete-dept-confirmation-dialog").html());
        $("#view-holder").append(contactDeleteDialogTemplate());
        $("#delete-dept-confirmation-dialog").modal();
    })

    $("#view-holder").on("click", "#cancel-delete-dept-button", function(e) {
        e.preventDefault();
        var deleteConfirmationDialog = $("#delete-dept-confirmation-dialog")
        deleteConfirmationDialog.modal('hide');
        deleteConfirmationDialog.remove();
    });

    $("#view-holder").on("click", "#delete-dept-button", function(e) {
        e.preventDefault();
        $.ajax({
            type: "DELETE",
            url: "/department/" + $("#dept-id").text(),
            success: function(result) {
            	Pizza.storeMessageToCache(result);
                window.location.href = "/department/list";
            }
        });
    });
    
});