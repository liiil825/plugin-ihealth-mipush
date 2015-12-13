/*global cordova, module*/
var MipushManagerCordova = function() {};  
  
MipushManagerCordova.prototype.register = function(msg, successCallback, errorCallback) {  
    cordova.exec(successCallback, errorCallback, "MipushManagerCordova", "register", [msg]);
};
  
var mipushManagerCordova = new MipushManagerCordova();  
module.exports = mipushManagerCordova; 
