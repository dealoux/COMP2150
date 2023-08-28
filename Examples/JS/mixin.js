'use strict';

let compare = { lessThanEqual(y) { return ( this.compareTo(y) <= 0);},
                lessThan(y) { return (this.compareTo(y) < 0 );},
                greaterThan(y) { return (this.compareTo(y) > 0); },
                greaterThanEqual(y) { return (this.comapareTo(y) >= 0); },
                notEqual(y) { return this.compareTo(y) != 0 ;}
};

class S {
    #data;
    constructor(d) {
            this.#data = d;
        
    }

    get data() {
        return this.#data;
    }

    // x.compareTo(y)==0 x and y are the same string.
    // x.comapreTo(y)<0  x comes before y in alphabetical order.
    compareTo(y) { 
        if (y instanceof S) {
            return this.#data.localeCompare(y.data); 
            // this is string compareTo in javascript
        } else {
            throw new Error ("can't compare");
        }
    }
}

Object.assign(S.prototype, compare);

let x = new S("alpha");
let y = new S("beta");
console.log( x.notEqual(y));
console.log( y.greaterThan(x));