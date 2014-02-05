$(function() {
	$("#placeOrderOperation").on(
			"click",
			"#place-order-link",
			function(e) {
				e.preventDefault();
				var placeOrderTemplate = Handlebars.compile($(
						"#template-place-order-window").html());
				$("#view-holder").append(placeOrderTemplate());
				$("#place-order-window").modal();
			});

	$("#view-holder").on("click", "#cancel-place-order-button", function(e) {
		e.preventDefault();
		var placeOrderWindow = $("#place-order-window")
		placeOrderWindow.modal('hide');
		placeOrderWindow.remove();
	});

	$('#placeForm').bind('submit', function(event) {
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
//				window.location.href = "/order";
			}
		});

	});

});
