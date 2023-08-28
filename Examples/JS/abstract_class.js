'use strict';

class Abstract {

    #f;
    constructor () {
        if (this.constructor === Abstract) {
            throw new Error("don't create an abstract class.");
        } else {
            //console.log(this.constructor);
            this.#f = 3;
        }
    }

    get field() {
        return this.#f;
    }

    print() {
        throw new Error("missing print in a concrete class.");
    }
}

class Concrete extends Abstract { 
    constructor() { 
        super();
    }

    // print() {
    //     console.log("message");
    // }
}

// let a = new Abstract();
let c = new Concrete(); // no error.
// c.print(); // this is error if you don't define the print method in the child class.









console.log( c instanceof Concrete);
console.log( c instanceof Abstract);
console.log( c instanceof Number);

