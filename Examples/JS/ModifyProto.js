'use strict';
class OtherClass  { 
    missingMethod() {
        console.log("in missing method");
    }
}

class MyClass {}

let m = new MyClass();
//m.missingMethod();

MyClass.prototype.__proto__ = new OtherClass();

m.missingMethod();
