"use strict";
let LinkedList = require('./LL.js');
let IntItem = require('./IntItem.js');

let ll = new LinkedList();
let item = new IntItem(3);
// added after class -- another item to show real insertion.
let item2 = new IntItem(5);
ll.insert(item);
ll.insert(item2);
ll.print();
