$(function() {

    addValidationErrorClassesToForm();
    addErrorMessage();

    function addValidationErrorClassesToForm() {
        $("form").find(".control-group").each(function() {
            var errorMessage = $(this).find(".help-inline").text();

            if (errorMessage) {
                $(this).addClass("error");
            }
        })
    }

    function addErrorMessage() {
        var errorMessageHolder = $("#form-errors");
        if (errorMessageHolder) {
            var errorMessage = errorMessageHolder.text();
            if (errorMessage) {
            	Pizza.addErrorMessage(errorMessage);
            }
        }
    }
})
