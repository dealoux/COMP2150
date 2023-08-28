#pragma once
#include <string>
using namespace std;

class Server {
protected:
    string* URI;
    bool running;
    bool isRunning();

public:
    Server(std::string*);
    string* getURI();
    void start();
    void stop();
    virtual bool connect(string* userid);
};
