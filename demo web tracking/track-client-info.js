

$(document).ready(function() {

	window.alert("test");
	var title = document.title;
	window.alert(document.title);
	var url = window.location.href;
	window.alert(url);


    $.post( "http://localhost:8080/log/rest/webtracker/userinfo", { title: title, url: url } );

});