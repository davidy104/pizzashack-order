$(function() {
	$("form").on("click", "#cancel-dept-udpate-button", function(e) {
		e.preventDefault();
		window.location.href = "/department/" + $("#dept-id").text();
	})
	
	$("form").on("click", "#cancel-dept-create-button", function(e) {
		e.preventDefault();
		window.location.href = "/department/list";
	})
});