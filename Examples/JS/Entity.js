'use strict';

/*
two jobs:
1. shouldn't be able to create Entity.
2. should enforce that all subclasses have print method.
*/

class Entity {
    constructor() {
        if (this.constructor === Entity ) { // are we trying to create Entity DIRECTLY?
            throw new Error("please don't create the abstract class");
        } else {
            // could be important - initializing fields in the abstract class.
        }
    }

    //abstract method in JS
    print() { 
        throw new Error("missing print method");
    }
}







module.exports = Entity;

