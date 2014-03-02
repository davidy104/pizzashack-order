$(function() {
    $(".well").on("click", "#show-flowimage-link", function(e) {
    	e.preventDefault();
    	$.ajax({
            type: "GET",
            url: "/showImage/"+ $("#workflow-wfId").text(),
            dataType: 'text',
            processData: false,
            contentType: false,
            success: function(data) {
            	$('#flowimage').html(data);
            }
        });

        var flowImageTemplate = Handlebars.compile($("#template-show-flowimage-window").html());
        $("#view-holder").append(flowImageTemplate());
        $("#show-flowimage-window").modal();
    })

    $("#view-holder").on("click", "#close-show-flowimage-button", function(e) {
        e.preventDefault();
        var showFlowWindow = $("#show-flowimage-window")
        showFlowWindow.modal('hide');
        showFlowWindow.remove();
    });
    
});