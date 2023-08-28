'use strict'
class A {
    #field;
    constructor() { 
        this.#field = 'A';
    }

    showField() { 
        console.log(this.#field);
    }
}

class B extends A {
    #field;
    constructor() { 
        super();
        this.#field = 'B';
    }

    get f() { 
        return this.#field;
    }
}

let b = new B;
console.log(b.f);
console.log(b);
b.showField();