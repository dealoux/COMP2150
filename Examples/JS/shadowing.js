'use strict'
class A {
    constructor() { 
        this.field = 'A';
    }
}

class B extends A {
    constructor() { 
        super();
        this.field = 'B';  // updating the value of .field
    }

    get f() { 
        return this.field;
    }
}

let b = new B();
console.log(b.f);
console.log(b);