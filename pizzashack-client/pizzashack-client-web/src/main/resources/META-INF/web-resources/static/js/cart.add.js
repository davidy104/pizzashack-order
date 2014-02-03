$(function() {
    $(".well").on("click", "#addto-cart-link", function(e) {
        e.preventDefault();
        var addToCartTemplate = Handlebars.compile($("#template-add-cart-window").html());
        $("#view-holder").append(addToCartTemplate());
        $("#add-cart-window").modal();
    })

    $("#view-holder").on("click", "#cancel-cart-add-button", function(e) {
        e.preventDefault();
        var addToCartWindow = $("#add-cart-window")
        addToCartWindow.modal('hide');
        addToCartWindow.remove();
    });

    $("#view-holder").on("click", "#add-cart-button", function(e) {
        e.preventDefault();
        $.ajax({
            type: "GET",
            url: "/cart/add/" + $("#pizza-id").text()+"/"+$("#amount").text(),
            success: function(result) {
                window.location.href = "/index";
            }
        });
    });
    
});