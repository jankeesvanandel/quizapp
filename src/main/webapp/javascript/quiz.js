console.log(atmosphere);
var socket = atmosphere
var subsocket;

var request = { url: document.location.toString() + 'quiz',
	contentType : "application/json",
	logLevel : 'debug',
	transport : 'websocket' ,
	trackMessageLength : true,
	reconnectInterval : 5000
};

var content = document.getElementById("content");

request.onOpen = function(response){
	console.log(response);
}

subSocket = socket.subscribe(request);