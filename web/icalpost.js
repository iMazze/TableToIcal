var ical_original;
var ical_new;


function ical_compare() {	
	
	alert("ical compare started\n original: " + ical_original.length + ", new: " + ical_new.length);
	
	a = [{ value:"4a55eff3-1e0d-4a81-9105-3ddd7521d642", display:"Jamsheer"}, { value:"644838b3-604d-4899-8b78-09e4799f586f", display:"Muhammed"}, { value:"b6ee537a-375c-45bd-b9d4-4dd84a75041d", display:"Ravi"}, { value:"e97339e1-939d-47ab-974c-1b68c9cfb536", display:"Ajmal"},  { value:"a63a6f77-c637-454e-abf2-dfb9b543af6c", display:"Ryan"}]
b = [{ value:"4a55eff3-1e0d-4a81-9105-3ddd7521d642", display:"Jamsheer", $$hashKey:"008"}, { value:"644838b3-604d-4899-8b78-09e4799f586f", display:"Muhammed", $$hashKey:"009"}, { value:"b6ee537a-375c-45bd-b9d4-4dd84a75041d", display:"Ravi", $$hashKey:"00A"}, { value:"e97339e1-939d-47ab-974c-1b68c9cfb536", display:"Ajmal", $$hashKey:"00B"}]

function comparer(otherArray){
  return function(current){
    return otherArray.filter(function(other){
      return other.value == current.value && other.display == current.display
    }).length == 0;
  }
}

var onlyInA = a.filter(comparer(b));
var onlyInB = b.filter(comparer(a));

result = onlyInA.concat(onlyInB);

alert(result);

}
