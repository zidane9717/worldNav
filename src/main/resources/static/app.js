var stompClient = null;

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#hostBtn").click(function () {
        validate(1);
    });
    $("#joinBtn").click(function () {
        validate(2);
    });
    $("#start").click(function () {
        startGame();
    });
    $("#send").click(function () {
        sendCommand($("#commandInput").val());
        $("#send").reset;
    });
    $("#rock").click(function () {
        fightModeInput("rock");
        fightButtons(true)
    });
    $("#paper").click(function () {
        fightModeInput("paper");
        fightButtons(true)
    });
    $("#scissors").click(function () {
        fightModeInput("scissors");
        fightButtons(true)
    });
    $("#disconnect").click(function () {
        disconnectPlayer();
    });
});

function fightButtons(type) {
    $("#rock").prop("disabled", type);
    $("#paper").prop("disabled", type);
    $("#scissors").prop("disabled", type);
}

function sendCommandButtons(type) {
    $("#commandInput").prop("disabled", type);
    $("#send").prop("disabled", type);
}

function buttonsDisable() {

    $("#name").prop("disabled", true);
    $("#number").prop("disabled", true);

    $("#hostBtn").prop("disabled", true);
    $("#joinBtn").prop("disabled", true);

    $("#commandInput").prop("disabled", false);
    $("#send").prop("disabled", false);

    $("#conversation").show();
    $("#greetings").html("");
}

function fightModeInput(input) {

    var urlType = '/fight';

    $.ajax({
        type: 'GET',
        url: urlType,
        data: {
            number: $("#number").val(),
            id: $("#fight").val(),
            name: $("#name").val(),
            content: input
        },
        success: function (text) {
            console.log(text);
            if (text === 1) {
                sendCommandButtons(false);
                $("#fight").val("");
            } else if (text === 2) {
                fightButtons(false);
            }
        },
        error: function (jqXHR) {
        }
    });
}

function validate(type) {
    if (type === 1) {
        var urlType = '/validateHosting';
    } else if (type === 2) {
        var urlType = '/validateJoinning';
    }

    $.ajax({
        type: 'GET',
        url: urlType,
        data: {
            number: $("#number").val(),
            name: $("#name").val(),
        },
        success: function (text) {
            if (type === 1) {
                $("#start").prop("disabled", false);
            }

            $("#validateAtIndex").text("");
            buttonsDisable();
            connect();
            serverMessage(text);

        },
        error: function (jqXHR) {
            if (type === 1) {
                $("#validateAtIndex").text("Game number already exist");
            } else if (type === 2) {
                $("#validateAtIndex").text("Make sure the Game exist, Join able and valid name");
            }
        }
    });
}

function disconnectPlayer() {
    var urlType = '/disconnect';
    $.ajax({
        type: 'GET',
        url: urlType,
        data: {
            number: $("#number").val(),
            name: $("#name").val(),
        },
        success: function (text) {
            stompClient.disconnect();
            sendCommandButtons(true);
            $("#disconnect").prop("disabled", true);
        },
        error: function (jqXHR) {
        }
    });
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/greetings/' + $("#number").val() + "/" + $("#name").val(), function (greeting) {
            serverMessage(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/greetings/' + $("#number").val(), function (greeting) {
            serverMessage(JSON.parse(greeting.body).content);
        });
    });
}

function serverMessage(message) {

    var n = message.split(" ");

    if (n[0] === "FIGHTMODE") {
        fightOutcomes(message);
    } else if (n[0] === "player" || n[0] === "!!!") {
        showPlayer(message);
    } else if (n[2] === "STARTED") {
        $("#disconnect").prop("disabled", false);
    } else {
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }
}

function fightOutcomes(message) {
    var n = message.split(" ");
    sendCommandButtons(true);

    if (n[1] === "ANNOUNCEMENT" || n[n.length - 2] === "DRAW") {
        fightButtons(false);
        sendCommandButtons(true);
        $("#fight").val(n[n.length - 1]);
    } else if (n[n.length - 2] !== $("#name").val()) {
        stompClient.disconnect();
    } else if (n[n.length - 2] === $("#name").val()) {
        sendCommandButtons(false);
    }
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showPlayer(message) {
    $("#players").append("<tr><td>" + message + "</td></tr>");
}

function startGame() {
    $("#start").prop("disabled", true);
    $("#disconnect").prop("disabled", false);
    var urlType = '/startGame';
    $.ajax({
        type: 'GET',
        url: urlType,
        data: {
            number: $("#number").val(),
        },
        success: function (text) {
        },
        error: function (jqXHR) {
        }
    });
}

function sendCommand(text) {
    stompClient.send("/app/hello/" + $("#number").val() + "/" + $("#name").val(), {}, JSON.stringify({
        'name': $("#name").val(),
        'number': $("#number").val(),
        'content': text
    }));
    $("#commandInput").val("");
}

function myFunction() {
    window.scrollTo(0, document.body.scrollHeight);
}

