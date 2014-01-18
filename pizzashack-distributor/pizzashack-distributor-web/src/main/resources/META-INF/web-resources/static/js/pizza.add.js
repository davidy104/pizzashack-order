$(function() {
    $("form").on("click", "#cancel-contact-button", function(e) {
        e.preventDefault();
        window.location.href = "/index";
    })
});