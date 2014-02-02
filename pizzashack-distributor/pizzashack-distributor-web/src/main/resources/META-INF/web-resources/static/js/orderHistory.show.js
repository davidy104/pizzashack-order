$(function() {

	$("#showHistory").on(
			"click",
			"#show-history-link",
			function(e) {
				e.preventDefault();
				$.ajax({
					type : "GET",
					url : "/orderQuery/showHistory/"
							+ $("#orderProcess-no").text(),
					dataType : 'json',
					contentType : 'application/json',
					success : function(histories) {
						var content = '';
						for ( var i = 0; i < histories.length; i++) {
							content += '<addr>Activity Name:</addr>'
									+ histories[i].name;
							content += '<addr>Activity Type:</addr>'
									+ histories[i].type;
							content += '<addr>Activity Assignee:</addr>'
									+ histories[i].assignee;
							content += '<addr>Activity Duration:</addr>'
									+ histories[i].duration;
							content += '<addr>Activity StartTime:</addr>'
									+ histories[i].startTime;
							content += '<addr>Activity EndTime:</addr>'
									+ histories[i].endTime;
						}
						$('#history-list').html(content);
					}
				});

				var showHistoriesTemplate = Handlebars.compile($(
						"#template-show-histories-window").html());
				$("#view-holder").append(showHistoriesTemplate());
				$("#show-histories-window").modal();
			});

	$("#view-holder").on("click", "#cancel-show-histories-button", function(e) {
		e.preventDefault();
		var selectDeptWindow = $("#show-histories-window")
		selectDeptWindow.modal('hide');
		selectDeptWindow.remove();
	});

});
