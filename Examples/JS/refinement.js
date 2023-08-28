'use strict';
class A { 
    #_x;
    constructor() { 
       this.#_x = 3; 
    }

    incr() { this.#_x ++ ;}
    print() { console.log("x: " + this.#_x);}
}

class B extends A { 
    constructor() { 
        super();
        this._y = 5; // could have been private. 
    }

    incr() {
        // I want to call the incr method of the parent class
        super.incr();
        this._y++;
    }

    print() {
        super.print();
        console.log("y: " + this._y);
    }
}

let bb = new B;
bb.incr();
bb.print();
