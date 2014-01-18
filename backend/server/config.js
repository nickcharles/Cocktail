
var express = require("express")
  , path = require('path');

module.exports = function configure(app) {
  app.configure(function(){

    app.set('port', process.env.PORT || 1024);
    app.set('views', __dirname + '/../views');
    app.use(express.logger('dev'));
    app.use(express.bodyParser());
    app.use(express.methodOverride());
    app.use(app.router);

    // required password for admins
    app.use('/admin', express.basicAuth(function(user, pass){
      return 'user' == user & 'pass' == pass;
    }));
  });

  app.configure('development', function(){
    app.use(express.errorHandler());
  });

}