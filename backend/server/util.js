

module.exports.contain = function(haystak, arr) {
	var out = 0;
	console.log(haystak, arr);
	for (var i in arr) {
		if(binarySearch(haystak, arr[i].name) == -1) {
			out++;
		}
	}
	return out;
};
// check if this[] contains all of arr[]


//Copyright 2009 Nicholas C. Zakas. All rights reserved.
//MIT-Licensed, see source file
function binarySearch(items, value){

    var startIndex  = 0,
        stopIndex   = items.length - 1,
        middle      = Math.floor((stopIndex + startIndex)/2);

    while(items[middle] != value && startIndex < stopIndex){

        //adjust search area
        if (value < items[middle]){
            stopIndex = middle - 1;
        } else if (value > items[middle]){
            startIndex = middle + 1;
        }

        //recalculate middle
        middle = Math.floor((stopIndex + startIndex)/2);
    }

    //make sure it's the right value
    return (items[middle] != value) ? -1 : middle;
}