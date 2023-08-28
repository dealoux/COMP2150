'use strict';

class Software{
    constructor (name, size, os) {
        if (arguments.length = 3) {
            this._name = name;
            this._size = size;
            this._os = os
        } else {
            this._name = "New Software";
            this._size = 0;
            this._os = "Windows";
        }
    }

    increaseSize(amount){
        this._size += amount;
    }

    update(){
        throw new Error("missing update in a concrete class.");
    }
} // end of Software abastract class

class TaxSoftware extends Software{
    constructor(name, size, os, province) { 
        if (arguments.length = 4) {
            super(name, size, os);
            this._province = province;

        } else {
            super();
            this._province = "Manitoba";
        }
    }

    update(){
        console.log("Update in progress…");
        this.increaseSize(125);
    }
}