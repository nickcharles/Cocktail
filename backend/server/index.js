
var express   = require('express')
  , app       = express()
  , colors    = require('colors')
  , router    = require('./router.js')
  , config    = require('./config.js')
  , http      = require('http');

// setup
config(app);

// define API routes
app.post('/think', router.think);

// simple get endpoints
var points = ["liquor", "mixers", "drinks"];
for (var i in points) {
	app.get('/'+points[i], router.db_query(points[i]));
}

// start the server
http.createServer(app).listen(app.get('port'), function(){
  console.log(("Express server listening on port " + app.get('port')).blue);
});
