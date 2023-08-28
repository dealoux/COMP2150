'use strict';
class C {

    method1 (argument) { 
        console.log("in second one");
    }
    
    method1 () { 
        console.log("in first one");
    }


}

let c = new C();
c.method1("param");
c.method1();
