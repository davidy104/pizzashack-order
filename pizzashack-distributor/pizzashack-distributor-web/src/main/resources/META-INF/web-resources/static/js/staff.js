$(function() {
	$("form").on("click", "#cancel-staff-udpate-button", function(e) {
		e.preventDefault();
		window.location.href = "/staff/" + $("#staff-id").text();
	})

	$("form").on("click", "#cancel-staff-create-button", function(e) {
		e.preventDefault();
		window.location.href = "/staff/list";
	})
});