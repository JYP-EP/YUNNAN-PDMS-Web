import _ from 'lodash'
let socket = null
// let url="ws://101.133.230.206";
// let url="ws://192.168.8.110";
//let port = '8600';
// let port = '8601';

// let url = "ws://192.168.1.218:8600"
//let url= window.websocketIp
let url = window.WebConfig.websocketIp
let isOpened = false
let subscribeMap = {}
let unsubMap = {}


function subscribe(subData){
  if(!isOpened && socket == null){
    socket =  new WebSocket(url);
    socket.onerror = function(event) {
      console.error("WebSocket error observed:", event);
    };
    socket.onopen = function(event) {
      isOpened = true
      console.log("WebSocket is open now.");
      subscribeMap["svg"+subData.subsParam.id] = subData
      if(subData.subsParam.params.length >0){
        sendMessage(subData.subsParam)
      }

    };
    // Listen for messages
    socket.onmessage = receiveMessage;
    socket.onclose = function(event){
      isOpened = false
      socket = null
      console.log('socket 关闭')
    }
  }else{
    subscribeMap["svg"+subData.subsParam.id] = subData
    if(subData.subsParam.params.length >0){
      sendMessage(subData.subsParam)
    }
  }
}

function query(socket, queryParam, callback){
  if(socket == null){
    socket = new WebSocket(url);
    console.log("webSocket connect to: " + url);
    socket.onerror = function(event) {
      console.error("WebSocket error observed:", event);
    };
    socket.onopen = function(event) {
      console.log("WebSocket is open now.");
      let queryId = Math.ceil(Math.random()*100)
      let msg = {
        "method": "query",
        "params": queryParam,
        "id": queryId
      }
      socket.send(JSON.stringify(msg));
      // console.log("query: " + JSON.stringify(msg));
    };
    socket.onmessage = callback;
    socket.onclose = function(event){
      console.log('socket closed');
      socket = null;
    }
  }
  else{
    let queryId = Math.ceil(Math.random()*100)
    let msg = {
      "method": "query",
      "params": queryParam,
      "id": queryId
    }
    socket.send(JSON.stringify(msg));
    // console.log("query: " + JSON.stringify(msg));
  }

  return socket;
}

function rpc(socket, queryParam, callback){
  if(socket == null){
    socket = new WebSocket(url);
    console.log("webSocket connect to: " + url);
    socket.onerror = function(event) {
      console.error("WebSocket error observed:", event);
    };
    socket.onopen = function(event) {
      console.log("WebSocket is open now.");
      let queryId = Math.ceil(Math.random()*100)
      let msg = {
        "method": "rpc",
        "params": queryParam,
        "id": queryId
      }
      socket.send(JSON.stringify(msg));
    };
    socket.onmessage = callback;
    socket.onclose = function(event){
      console.log('socket closed');
    }
  }
  else{
    let queryId = Math.ceil(Math.random()*100)
    let msg = {
      "method": "rpc",
      "params": queryParam,
      "id": queryId
    }
    socket.send(JSON.stringify(msg));
    // console.log("query: " + JSON.stringify(msg));
  }

  return socket;
}

function sendMessage(msg){
  if(isOpened && socket != null){
      // console.log(JSON.stringify(msg))
      socket.send(JSON.stringify(msg))
  }
}

function receiveMessage(event){
  let messages = event.data;
  let dataJson = JSON.parse(messages)
  let error = dataJson.error
  let results = dataJson.result
  let dataId = dataJson.id
  if(results != undefined) {
    if (typeof results == 'object') {
      for (let subskeyId in subscribeMap) {
        let subscribeData = subscribeMap[subskeyId]
        subscribeData.onData(results)
      }
    } else if (results == 0) {
      if(unsubMap['svgunsub'+dataId] != undefined){
        let subId = unsubMap['svgunsub'+dataId]
        delete subscribeMap[subId]
        delete unsubMap['svgunsub'+dataId]
        console.log("id为" + dataJson.id + "取消订阅成功")
      }else if(subscribeMap['svg'+dataId] != undefined){
        console.log("id为" + dataJson.id + "订阅成功")
      }
    }
  }else{
    if(subscribeMap['svg'+dataId] != undefined){
      console.error("id为" + dataJson.id + "订阅失败")
    }else if(unsubMap['svgunsub'+dataId] != undefined){
      console.error("id为" + dataJson.id + "取消订阅失败")
      delete unsubMap['svgunsub'+dataId]
    }
  }
}

function unsubscribe(subsId){
  if(subscribeMap[subsId] != undefined){
    let params = subscribeMap[subsId].subsParam.params
    _.forEach(params,function(item){
      delete item["cols"]
    })
    let unsubId = Math.ceil(Math.random()*100)
    let msg = {
      "method": "unsubs",
      "params": params,
      "id": unsubId
    }
    unsubMap['svgunsub'+unsubId] = subsId
    sendMessage(msg)
  }
  // if()
}

function unsubscribeAll(){
  subscribeMap = {}
  unsubMap = {}
  if(socket != null){
    let unsubId = Math.ceil(Math.random()*100)
    let msg = {
      "method": "unsubsall",
      "id": unsubId
    }
    sendMessage(msg)
    isOpened = false
    socket.close()
    socket = null
  }


}

export {subscribe, query, rpc, unsubscribe,unsubscribeAll}


