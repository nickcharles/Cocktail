var mongo = require("./database.js"),
	util  = require("./util.js");

mongo.connect(function(msg) {
	if(msg == null)
		console.log("Mongo Connected!");
	else 
		console.log(msg);
});

// think needs an array of ingredients in the body
exports.think = function(req, res){
	
	var drinks = mongo.db.collection("drinks");

	// arr of liquor options
	var options = req.body.sort();
	var query = { 
		// get all drinks which have any ingred.
		"ingredients.name": { 
			$in: options
		}
	};

	drinks.find(query, {_id:0}).toArray(function(err, matches) {
		if(err) throw err;

		for (var i in matches) {
			var drink = matches[i];

			// count number of missing ingredients
			drink.missing = util.contain(options, drink.ingredients);
		}

		matches.sort(function(a,b) {
			return a.missing > b.missing;
		})

		res.send(matches);
	});
};

// think needs an array of ingredients in the body
exports.db_query = function(name) {
	return function(req, res){
		var collection = mongo.db.collection(name);

		collection.find({}, {_id:0}).toArray(function(err, docs) {
			if(err) throw err;
			res.send(docs);
		});
	};
}
