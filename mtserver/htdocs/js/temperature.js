// Temperature Control Component

TempCtrl = function (params) {

    var CONST = {
        TEMP_MIN : 4,
        TEMP_MAX : 25
    }
    
    var temp;
    
    var init = function(){
        // Set initial value
        set(params.value);
    };

    function up() {
        if (temp < CONST.TEMP_MAX)
        {
            temp++;
            $('.temp-digits span').text(formatTemp(temp));
        }
    }

    function down() {
        if (temp > CONST.TEMP_MIN)
        {
            temp--;
            $('.temp-digits span').text(formatTemp(temp));
        }
    }

    function set(value) {
        $('.temp-digits span').text(formatTemp(value));
    }

    
    function formatTemp(val) {
        temp = val;
        
        // Send the set value to the server
        Server.send("temperature", temp);
        
        if (temp < 10) {
            return "0" + temp + "°C";
        } else {
            return temp + "°C";
        }
    }


    return {
        init: init,
        up: up,
        down: down,
        set: set
    }

};