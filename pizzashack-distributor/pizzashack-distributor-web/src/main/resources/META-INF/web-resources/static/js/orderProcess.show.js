$(function() {

	$("#reviewOptions").on(
			"click",
			"#review-order-link",
			function(e) {
				e.preventDefault();
				var createReviewTemplate = Handlebars.compile($("#template-create-review-window").html());
				$("#view-holder").append(createReviewTemplate());
				$("#create-review-window").modal();
			});

	$("#view-holder").on("click", "#close-review-create-button", function(e) {
		e.preventDefault();
		var createReviewWindow = $("#create-review-window")
		createReviewWindow.modal('hide');
		createReviewWindow.remove();
	});

	$("#view-holder").on("click", "#add-review-button", function(e) {
		e.preventDefault();
		console.log('add review start');
		var $reviewForm = $('#reviewForm');
		var $responseMsg = $('#actionMsg');

		$reviewForm.bind('submit', function(event) {
			event.preventDefault();
			var formValues = $(this).serialize();
			var formURL = $(this).attr("action");

			$.ajax({
				url : formURL,
				type : "POST",
				data : formValues,
				success : function(result) {
					$responseMsg.html(result);
				}
			});

		});
	});

});
