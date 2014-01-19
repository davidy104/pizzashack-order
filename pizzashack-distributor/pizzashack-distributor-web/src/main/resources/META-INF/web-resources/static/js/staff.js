$(function() {
	$("form").on("click", "#cancel-staff-udpate-button", function(e) {
		e.preventDefault();
		window.location.href = "/staff/" + $("#staff-id").text();
	})

	$("form").on("click", "#cancel-staff-create-button", function(e) {
		e.preventDefault();
		window.location.href = "/staff/list";
	})

	$(".well").on(
			"click",
			"#add-user-link",
			function(e) {
				e.preventDefault();
				var addUserWindowTemplate = Handlebars.compile($(
						"#template-add-user-window").html());
				$("#view-holder").append(addUserWindowTemplate());
				$("#add-user-window").modal();
			})

	$("#view-holder").on("click", "#close-user-create-button", function(e) {
		e.preventDefault();
		var addUserWindow = $("#add-user-window")
		addUserWindow.modal('hide');
		addUserWindow.remove();
	});
	
	
	
	$("#view-holder").on("click", "#add-user-button", function(e) {
        e.preventDefault();
        var username = $('#username').val();
        var password = $('#password').val();
        
        var jsonData = {
				"username" : username,
				"password" : password
		};
        
        $.ajax({
            type: "POST",
            url: "/user/createUser",
            dataType: 'json',
    		contentType: 'application/json',
            data : JSON.stringify(jsonData),
            success : response
        });
    });

//	$('#useraddForm').bind('submit', function(event) {
//		event.preventDefault();
//		var formValues = $(this).serialize();
//		console.log(formValues);
//
//		$.ajax({
//			url : "/user/create",
//			type : "post",
//			data : formValues,
//			success : response
//		});
//	});
	
	var response = function(data){
		console.log(data);
		if (data) {
			Pizza.storeMessageToCache("User created");
			var userId = data.userId;
	    	console.log(userId);
	    	$('#staff-user-userId').val(userId);
		}
		
		var addUserWindow = $("#add-user-window")
		addUserWindow.modal('hide');
		addUserWindow.remove();
	};

});