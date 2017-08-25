var ws = new WebSocket("ws://localhost:8081");

ws.onopen = function(){
};

ws.onmessage = function (evt){
    var message = JSON.parse(evt.data);
    var messageType = message.type;
    var content = message.content;

    switch(messageType){
        case 0:
           document.getElementById("pageContent").innerHTML = "<h1>Service is inaccessible!</h1>";
           break;

        case 1:
          if(content == null) {
           document.getElementById("message").innerHTML = "No such user";
          }
          else{
            document.getElementById("message").innerHTML = content.name;
          }
          break;
    }

};

ws.onclose = function(evt){
    document.getElementById("pageContent").innerHTML = "<h1>Connection to server is lost!</h1>";
};


function getUser(){
    var id = document.getElementById("id").value;
    if(id != ""){
        var userAction = {
            action : "get",
            id : id
        };
        ws.send(JSON.stringify(userAction));
    }
}
