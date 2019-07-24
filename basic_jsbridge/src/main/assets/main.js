
window.onload = function() {
    var inputMessage = document.getElementById("inputMessage")
    var sendButton = document.getElementById("sendButton")
    sendButton.addEventListener("click", () => {
        var res = send(inputMessage.value)
        console.debug(res)
    })
    var sendButtonAsync = document.getElementById("sendButtonAsync")
    sendButtonAsync.addEventListener("click", () => {
        sendAsync(inputMessage.value, res => {
            console.debug(res)
        })
    })
}

// Javascricpt call native synchronously via bridge
function send(data) {
    return bridge.send(data)
}

// Javascript endpoint called by native
function onReceived(data) {
    var receivedMessage = document.getElementById("receivedMessage");
    receivedMessage.innerHTML = data + " " + new Date().Format("hh:mm:ss") + "<br/>" + receivedMessage.innerHTML
    return "I am web, responding message received to you"
}

//==========================================================

var callbackMap = {}

// Javascricpt call native asynchronously via bridge
function sendAsync(data, callback) {

    var callbackId = ''
    if (typeof callback === 'function') {
        callbackId = 'callback_' + generateUUID();
        callbackMap[callbackId] = callback;
    }

    bridge.sendAsync(data, callbackId)
}

// Javascript endpoint call by native
function onAsyncCallback(callbackId, result) {
    if (callbackId && callbackMap[callbackId]) {
        var callback = callbackMap[callbackId]
        callback(result)
        delete callbackId[callbackId]
    }

    return "I am web, responding async callback to you"
}

//==========================================================

function generateUUID() { // Public Domain/MIT
    var d = new Date().getTime();
    if (typeof performance !== 'undefined' && typeof performance.now === 'function'){
        d += performance.now(); //use high-precision timer if available
    }
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
}

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(), 
        "s+": this.getSeconds(), 
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}