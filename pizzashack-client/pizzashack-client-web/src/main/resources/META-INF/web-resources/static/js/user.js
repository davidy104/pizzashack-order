$(function() {
	$("form").on("click", "#cancel-user-update-button", function(e) {
		e.preventDefault();
		window.location.href = "/user/" + $("#user-id").text();
	})
	
	$("form").on("click", "#cancel-user-create-button", function(e) {
		e.preventDefault();
		window.location.href = "/user/list";
	})
});