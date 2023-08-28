'use strict';

class C {
    constructor() { 
        this._name = 'foo'
        // this.print = "value";
    }

    print() {
        console.log(this._name);
    }
}


let c = new C();
//c.print();



console.log('print' in c && typeof(c.print) === 'function');
console.log('_name' in c && typeof(c._name) === 'function');
console.log('print' in c);

