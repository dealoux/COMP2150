"use strict";
let Node = require('./Node.js'); // use Node in this file. 

class LinkedList {

    constructor() {
        this._head = null;
    }

    insert ( data ) { 
        this._head = new Node ( data, this._head );
    }
    
    print() { 

       if (this._head != null) { 
           this._head.print();
       }

    }

    isEmpty() {
        return (this._head === null);
	}
}

module.exports = LinkedList;
