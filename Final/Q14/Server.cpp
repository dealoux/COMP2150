#include "Server.h"

Server::Server(string* URI){
    this->URI = URI;
    this->running = false;
}

void Server::start(){
    if(!this->isRunning()){
        this->running = true;
    }
}

void Server::stop(){
    if(this->isRunning()){
        this->running = false;
    }
}

bool Server::isRunning(){ return this->running; }