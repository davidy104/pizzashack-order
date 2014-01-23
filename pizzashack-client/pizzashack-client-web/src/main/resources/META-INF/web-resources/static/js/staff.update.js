$(function() {
	$("form").on("click", "#cancel-staff-udpate-button", function(e) {
		e.preventDefault();
		window.location.href = "/staff/" + $("#staff-id").text();
	})

	$("form").on("click", "#check-user-link", function(e) {
		e.preventDefault();
		var username = $('#staff-user-username').val();
		var $responseMsg = $('#actionMsg');

		$.ajax({
			type : "GET",
			url : "/user/check/" + $('#staff-user-username').val(),
			success : function(result) {
				$responseMsg.html(result);
			}
		});
	})

	 $("form").on("click", "#select-depts-link", function(e) {
	        e.preventDefault();

	        var deptIdsData = $('#staff-deptIds').text();
	        var deptIdsArr = deptIdsData.split(',');

	        $.ajax({
	            type: "GET",
	            url: "/department/selectdepts",
	            dataType: 'json',
	    		contentType: 'application/json',
	            success: function(depts) {
	            	var content = '';
	            	for (var i = 0; i<depts.length; i++) {
	            		var ifchecked = false;
	            		for(var j=0; j<deptIdsArr.length; j++){
	            			if(deptIdsArr[j] == depts[i].deptId){
	            				ifchecked = true;
	            			}
	            		}
	            		if(ifchecked){
	            			content += '<addr> <input type="checkbox" checked id="'+depts[i].deptId+'" value="'+depts[i].deptId+'" deptName="'+depts[i].deptName+'"> </addr>';
	            		} else {
	            			content += '<addr> <input type="checkbox"  id="'+depts[i].deptId+'" value="'+depts[i].deptId+'" deptName="'+depts[i].deptName+'"> </addr>';
	            		}
	            		content += '<abbr title="deptName">Department Name:</abbr>'+depts[i].deptName;
	            	}
	            	$('#pizza-list').html(content);
	            }
	        });

	        var selectDeptsTemplate = Handlebars.compile($("#template-select-depts-window").html());
	        $("#view-holder").append(selectDeptsTemplate());
	        $("#select-depts-window").modal();
	    })

	    $("#view-holder").on("click", "#cancel-select-depts-button", function(e) {
	        e.preventDefault();
	        var selectDeptWindow = $("#select-depts-window")
	        selectDeptWindow.modal('hide');
	        selectDeptWindow.remove();
	    });

	    $("#view-holder").on("click", "#select-depts-button", function(e) {
	        e.preventDefault();
	        console.log('selectdepts start');
			var allDeptNames = [];
			var selectedIdsStr = "";

			$('#pizza-list :checked').each(function() {
				selectedIdsStr+=$(this).val();
				allDeptNames.push($(this).attr("deptName"));
			});
			console.log(selectedIdsStr);
			$('#selectedDeptIdsStr').val(selectedIdsStr);
			$('#staff-depts-textarea').val(allDeptNames);

			 var selectDeptWindow = $("#select-depts-window")
		        selectDeptWindow.modal('hide');
		        selectDeptWindow.remove();
	    });


});

