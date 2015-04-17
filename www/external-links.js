var contactExport = {};

contactExport.openIntent = function(phoneNumber, successCallback, errorCallback) {
    if (typeof phoneNumber !== 'string' || !(phoneNumber)) {
        if (errorCallback) {
            errorCallback({
                code: "INVALID_INPUT",
                message: "Invalid Input"
            });
        }
        return;
    }
           
    cordova.exec(successCallback, errorCallback, "ExternalLinks", "openPhone", [phoneNumber.replace(/[^0-9]/gi, '')]);
};

contactExport.openMap = function(param, successCallback, errorCallback) {
    if (typeof param !== 'object') {
        if (errorCallback) {
            errorCallback({
                code: "INVALID_INPUT",
                message: "Invalid Input"
            });
        }
        return;
    }
           
    cordova.exec(successCallback, errorCallback, "ExternalLinks", "openMap", [param]);
};

module.exports = contactExport;
