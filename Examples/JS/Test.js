//why we need this?
"use strict";

function main() {

    //x
    //y
    //z

    var x = 13;  // global
    let y = 13;  // this is the best way we want to define our variables

    console.log(typeof (y)); //number
    y = "Ali";
    console.log(typeof (y)); //string
    y = 'c';
    console.log(typeof (y)); //string
    x = Date.now();
    let z = 13; //global

    x = 20;
    console.log(typeof (x)); //number
    x = 12.53;
    console.log(typeof (x)); //number
    x = new Array(3);

    let k;
    console.log(typeof (k)); // undefined

    //change var to let
    for (var i = 0; i < 10; i++)  //var and nothing -> global     ....  let is local
    {
        console.log("test");
    }

    console.log(i); //error

    x = "Ali";
    console.log(mytestFunction(x)); // "undefined"


    myFunctionThree(1,2,3);
    myFunctionThree(1,"ali",3);
    myFunctionThree(1,"ali",3.364);


    myFunctionThree("Ali", "ali",2, 3);
    myFunctionThree("Ali", "ali",2, 3, 10, 20, 20.5);
    myFunctionThree(1);
    // myFunctionThree(array1, array2);
    myFunctionThree();
}

//other methods
//no input parameters type
function mytestFunction(myVar) {
    if (myVar == 13) {
        return "lucky";
    }
    //return nothing
}

function myTestFunctionTwo() {
    let myArray = new Array(3);
    myArray[0] = 'a';
    myArray[1] = "string";
    myArray[2] = 13;
}

function myFunctionThree(input1, input2, input3) {
    //do some stuff

    console.log(arguments); // length of this arguments is three
    console.log(arguments[3]); //3

    if(arguments.length == 3)
    {

        //do something with index 0,1,2
    }
}


main();