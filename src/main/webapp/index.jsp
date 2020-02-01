<%@page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <title>websocket</title>
    <script type="text/javascript">
        var websocket = null;
        if('WebSocket' in window){
            websocket = new WebSocket("ws://127.0.0.1:8080/WebRTCServer/websocket");
        }else{
            alert("当前浏览器不支持WebSocket");
        }

        function send(){
            var message = document.getElementById("message").value;
            websocket.send(message);
        }

        function showMessage(message){
            document.getElementById("message-list").innerHTML += message+"<br/>";
        }

        function closeWebsocket(){
            websocket.close();
        }
        websocket.onerror = function(){
            showMessage("连接发生错误");
        }

        websocket.onopen = function(){
            showMessage("websocket连接成功");
        }

        websocket.onmessage = function(event){
            showMessage(event.data);
        }

        websocket.onclose = function(){
            showMessage("websocket连接关闭");
        }

        websocket.onbeforeupload = function(){
            closeWebsocket();
        }


    </script>
</head>
<body>
<h2>chatroom with websocket</h2>
<div id="container">
    <div>
        <input type="text" name="message" id="message" placeholder="hello,websocket"/>
        <input type="button" value="send" onclick="send()"/>
    </div>
    <div id="message-list">

    </div>
</div>

</body>
</html>