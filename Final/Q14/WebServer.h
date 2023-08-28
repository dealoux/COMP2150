#pragma once
#include "Server.h"
#include "List.h"

class WebServer : public Server {
    protected:
    List* connectedUsers;

public:
    WebServer(string*);
    bool connect(string*);
};
