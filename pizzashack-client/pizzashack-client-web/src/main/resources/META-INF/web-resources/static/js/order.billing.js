$(function() {
	$("#orderOperations").on(
			"click",
			"#order-billing-link",
			function(e) {
				e.preventDefault();
				var orderBillingTemplate = Handlebars.compile($(
						"#template-order-billing-window").html());
				$("#view-holder").append(orderBillingTemplate());
				$("#order-billing-window").modal();
			});

	$("#view-holder").on("click", "#cancel-billing-create-button", function(e) {
		e.preventDefault();
		var orderBillingWindow = $("#order-billing-window")
		orderBillingWindow.modal('hide');
		orderBillingWindow.remove();
	});

	$('#billingForm').bind('submit', function(event) {
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
