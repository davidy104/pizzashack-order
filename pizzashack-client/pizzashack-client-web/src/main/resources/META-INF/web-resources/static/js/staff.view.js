$(function() {
    $(".well").on("click", "#delete-staff-link", function(e) {
        e.preventDefault();
        var staffDeleteDialogTemplate = Handlebars.compile($("#template-delete-staff-confirmation-dialog").html());
        $("#view-holder").append(staffDeleteDialogTemplate());
        $("#delete-staff-confirmation-dialog").modal();
    })

    $("#view-holder").on("click", "#cancel-delete-staff-button", function(e) {
        e.preventDefault();
        var deleteConfirmationDialog = $("#delete-staff-confirmation-dialog")
        deleteConfirmationDialog.modal('hide');
        deleteConfirmationDialog.remove();
    });

    $("#view-holder").on("click", "#delete-staff-button", function(e) {
        e.preventDefault();
        $.ajax({
            type: "DELETE",
            url: "/staff/" + $("#staff-id").text(),
            success: function(result) {
            	Pizza.storeMessageToCache(result);
                window.location.href = "/staff/list";
            }
        });
    });

});