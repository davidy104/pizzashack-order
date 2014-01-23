$(function() {
	$("form").on("click", "#cancel-button", function(e) {
		e.preventDefault();
		window.location.href = "/index";
	})
});