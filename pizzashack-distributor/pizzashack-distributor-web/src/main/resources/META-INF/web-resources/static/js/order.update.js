$(function() {
	$("form").on("click", "#cancel-order-button", function(e) {
		e.preventDefault();
		window.location.href = "/order/" + $("#order-id").text();
	})
});