'use strict';

class Student {
    #year;
    constructor(name, studentNumber) { 
        this._name =  name;
        if (studentNumber == undefined) {
            this._studentNumber = -1;

        } else {
        this._studentNumber = studentNumber;
        }
        this.#year = 1;
    }

    get name() { 
        return this._name;
    }

    toString() { 
        return this.name + ", " + this._studentNumber + ", " + this.#year;
    }
}

class GradStudent extends Student { 
    constructor(name, studentNumber, title) { 
        super(name,studentNumber);
        this._thesisTitle = title;
        //this.#year = 5; //error - private field in the parent
    }

    get title() {
        return this._thesisTitle;
    }
}

let gs = new GradStudent("mike", 12345, "Inheritance in JS");
console.log(gs.title ); // child
console.log(gs.name);  // parent

console.log(gs.toString());  //parent
