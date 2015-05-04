$(function () {
    "use strict";

    var content = $('#content');
    var author = null;
    var logged = false;
    var socket = atmosphere;
    var subSocket;
    var transport = 'websocket';

    // We are now ready to cut the request
    var request = { url: document.location.toString() + 'quiz',
        contentType : "application/json",
        logLevel : 'debug',
        transport : transport ,
        trackMessageLength : true,
        reconnectInterval : 5000 };

    request.onOpen = function(response) {
        content.text('Connected, waiting for question.');
        transport = response.transport;

        // Carry the UUID. This is required if you want to call subscribe(request) again.
        request.uuid = response.request.uuid;
    };

    request.onClientTimeout = function(r) {
        content.html($('<p>', { text: 'Client closed the connection after a timeout. Reconnecting in ' + request.reconnectInterval }));
        setTimeout(function (){
            subSocket = socket.subscribe(request);
        }, request.reconnectInterval);
    };

    request.onReopen = function(response) {
        content.html($('<p>', { text: 'Atmosphere re-connected using ' + response.transport }));
    };

    // For demonstration of how you can customize the fallbackTransport using the onTransportFailure function
    request.onTransportFailure = function(errorMsg, request) {
        atmosphere.util.info(errorMsg);
        request.fallbackTransport = "long-polling";
    };

    request.onMessage = function (response) {
        var message = response.responseBody;
        try {
            var json = atmosphere.util.parseJSON(message);
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', message);
            return;
        }
        showQuestion(json);
    };

    request.onClose = function(response) {
        content.html($('<p>', { text: 'Server closed the connection after a timeout' }));
        if (subSocket) {
            subSocket.push(atmosphere.util.stringifyJSON({ author: author, message: 'disconnecting' }));
        }
    };

    request.onError = function(response) {
        content.html($('<p>', { text: 'Sorry, but there\'s some problem with your '
        + 'socket or the server is down' }));
        logged = false;
    };

    request.onReconnect = function(request, response) {
        content.html($('<p>', { text: 'Connection lost, trying to reconnect. Trying to reconnect ' + request.reconnectInterval}));
    };

    subSocket = socket.subscribe(request);

    function showQuestion(message){
        content.empty();
        var form = $('<form action="answer">');
        content.append(form);
        form.append('<p class="question">'+message.question+'</p>');
        for (var option in message.options){
            form.append('<p class="question_option"><input type="radio" name="answer" value="'+
                option+'"/>' + message.options[option] + '</p>');
        }
        form.append('<input type="submit"/>');
    };

});