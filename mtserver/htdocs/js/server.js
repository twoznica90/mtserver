// Server Component
//
// 
// Used to post data back to the server.
// Actually only applied to temperature and only simulated, as data persistence is not required.

Server = (function () {

    var SERVER_DATA_FILE = './data/system.json';

    // Function used to send data to the server (simulation only)
    var send = function (data_name, data_value) {

        // The post request below is purposedly commented
        //$.post( SERVER_DATA_FILE, "{" + data_name + " : " + data_value + "}");

    };

    return {
        SERVER_DATA_FILE: SERVER_DATA_FILE,
        send: send
    }

})();