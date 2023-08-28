#include "WebServer.h"

WebServer::WebServer(string* URI){
    Server(URI);
    connectedUsers = new List();
}

bool WebServer::connect(string* userid){
    if(this->isRunning()){
        if(!connectedUsers.contains(userid)){
            connectedUsers.insert(userid);
        }
    }
}