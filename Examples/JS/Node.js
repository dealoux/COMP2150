"use strict";

class Node {

    constructor(data,next) {
        if (arguments.length == 1) {
            this.field = "asdas";

            this._data = data;
            this._next = null;
        } else if (arguments.length >= 2) {
           this._data = data;
            this._next = next;
        } else {
            this._data = -1;
            this._next = null;
        }
    }    

    print() {
        if ('print' in this._data && typeof(this._data.print) === 'function') {
            this._data.print();    // want to be enforced by the abstract class.
        } else {
            // no print detected.
        }

        if (this._next != null) {
            this._next.print();
        }
    }

    

    get data() {
        return this._data;
    }

    get next() {
        return this._next;
    }



}


class RandomClass{
    constructor()
    {
        this.randomField = 20;
    }

    //no print method
}

module.exports = Node;