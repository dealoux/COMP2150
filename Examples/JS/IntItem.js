"use strict";
let Entity = require('./Entity.js'); 

class IntItem extends Entity {
    constructor(d) {
        super();
        this.data = d;
    }

    //we have to implement abstract methods in the child classes
    print() {
        console.log(this.data);
    }

}

module.exports = IntItem;