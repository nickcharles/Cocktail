
// options is an object {"vodka":true, ...}
module.exports.contain = function(options, arr) {
	var out = 0;
	console.log(options, arr);
	for (var i in arr) {
		if(options[arr[i].name]) {
			arr[i].have = false;
			out++;
		} else {
			arr[i].have = true;
		}
	}
	return out;
};