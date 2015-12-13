/*global cordova, module*/
var MipushManagerCordova = function() {};  
  
MipushManagerCordova.prototype.register = function(msg, successCallback, errorCallback) {  
    cordova.exec(successCallback, errorCallback, "MipushManagerCordova", "register", [msg]);
};

MipushManagerCordova.prototype.registerEx = function(successCallback, errorCallback, id, secret) {  
    cordova.exec(successCallback, errorCallback, "MipushManagerCordova", "registerEx", [id, secret]);
};
  
var mipushManagerCordova = new MipushManagerCordova();  
module.exports = mipushManagerCordova; 
