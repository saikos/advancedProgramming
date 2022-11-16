const who = 'Jim';

const msg = `Hello ${who}`;

console.log('Hello World');
console.log("Χαίρε κόσμε");
console.log(msg);

// who = 'Peter';
//console.log(msg);

let x = 1;
console.log('x', x);
x = false;
console.log('x', x);
x = msg;
console.log('x', x);

console.log(y);
var y = 3;

const array = [1, 2, 3];
const obj = { name: 'Jim', age:23};

function printArray(arr) {
    for (let i = 0; i < arr.length; i++) {
        console.log(`[${i}]: ${arr[i]}`);
    }
}

const anotherArray = [0, false, null, msg, obj, undefined];
printArray(anotherArray);

anotherArray[10] = 'Test';
printArray(anotherArray);

let number = 42;

function add(a) {
    return number + a;
}

console.log(add(3));
number = 1;
console.log(add(3));


function Person(name) {
    var whatever = 'Mrs';

    this.name = name;

    this.getName = function() {
        return whatever + this.name;
    }

    return this;
}

const p = new Person();
console.log(p);
console.log(p.getName());
// console.log(whatever);

const p2 = Person.call(obj, 'Hello?');
console.log(p2);

function oldVarCaution() {
    const adders = [];
    for (var i = 0; i < 3; i++) {
        adders[i] = function(x) {
            return x + i;
        }        
    }
    return adders;
}

const functions = oldVarCaution();
console.log(functions[0](3));