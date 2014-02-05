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
    
    $('#cartAddForm').bind('submit', function(event) {
		console.log('submit start');
		event.preventDefault();
		var formValues = $(this).serialize();
		var formURL = $(this).attr("action");
		console.log('formURL: ' + formURL);
		console.log('formValues: ' + formValues);

		$.ajax({
			url : formURL,
			type : "POST",
			data : formValues,
			success : function(result) {
				console.log('result: ' + result);
				window.location.href = "/index";
			}
		});

	});

//    $("#view-holder").on("click", "#add-cart-button", function(e) {
//    	console.log('add cart start');
//        e.preventDefault();
//        var urlStr = "/cart/add/" + $("#pizza-id").text()+"/"+$("#amount").text();
//        console.log('urlStr: ' + urlStr);
//        $.ajax({
//            type: "GET",
//            url: urlStr,
//            success: function(result) {
//            	Pizza.storeMessageToCache(result);
//                window.location.href = "/index";
//            }
//        });
//    });
    
});