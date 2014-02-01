$(function() {

	$("#reviewOptions").on(
			"click",
			"#review-order-link",
			function(e) {
				e.preventDefault();
				var createReviewTemplate = Handlebars.compile($(
						"#template-create-review-window").html());
				$("#view-holder").append(createReviewTemplate());
				$("#create-review-window").modal();
			});

	$("#view-holder").on("click", "#close-review-create-button", function(e) {
		e.preventDefault();
		var createReviewWindow = $("#create-review-window")
		createReviewWindow.modal('hide');
		createReviewWindow.remove();
	});

	$('#reviewForm').bind('submit', function(event) {
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
				Pizza.storeMessageToCache(result);
                window.location.href = "/index";
			}
		});

	});

});
