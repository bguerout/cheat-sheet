var express = require('express');
var app = express();

var bodyParser = require('body-parser')
app.use( bodyParser.json() );       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
  extended: true
})); 

app.use(function (req, res, next) {
  console.log(req.method, req.body);
  next();
});

app.get('/', function (req, res) {
  res.send('Hello World!');
});

app.post('/post', function (req, res) {
  res.send('Hello World!');
});

app.post('/other-web-server/rest/post', function (req, res) {
  res.send('Hello World!');
});

app.listen(8080, function () {
  console.log('Example app listening on port 8080!');
});
