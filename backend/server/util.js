
// options is an object {"vodka":true, ...}
module.exports.contain = function(options, arr) {
	var out = 0;
	for (var i in arr) {
		if(options[arr[i].name]) {
			arr[i].have = true;
		} else {
			arr[i].have = false;
			out++;
		}
	}
	return out;
};