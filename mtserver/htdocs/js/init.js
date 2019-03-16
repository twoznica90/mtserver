$(document).ready(function () {

    // Get the server simulated data
    // to initialize each component inside the $.getJSON promise handler
    // and create a user click handle for each component to bind it to the corresponding action


    $.getJSON(Server.SERVER_DATA_FILE, function (data) {


        /////////////////////////////////////////////////
        // Upstairs lights
        /////////////////////////////////////////////////

        var upstairsLights = LightsCtrl({
            element: 'upstairs-window',
            value: data.u_light
        });
        upstairsLights.init();

        $("#upstairs-lights-button").click(function () {
            SoundCtrl.clickSound();
            upstairsLights.toggle('upstairs-window');
        });


        /////////////////////////////////////////////////
        // Downstairs lights (Living room and lobby)
        /////////////////////////////////////////////////

        var livingRoomLights = LightsCtrl({
            element: 'living-room-window',
            value: data.d_light
        });
        livingRoomLights.init();

        var lobbyLights = LightsCtrl({
            element: 'lobby-window',
            value: data.d_light
        });
        lobbyLights.init();

        $("#downstairs-lights-button").click(function () {
            SoundCtrl.clickSound();
            livingRoomLights.toggle('living-room-window');
            lobbyLights.toggle('lobby-window');
        });


        /////////////////////////////////////////////////
        // Upstairs curtain
        /////////////////////////////////////////////////

        var upstairsCurtain = CurtainsCtrl({
            element: 'upstairs-curtain',
            value: data.u_curtain
        });
        upstairsCurtain.init();

        //$("#upstairs-curtain-button").click(upstairsCurtain.toggle('upstairs-curtain'));
        $("#upstairs-curtain-button").click(function () {
            upstairsCurtain.toggle('upstairs-curtain');
        });

        /////////////////////////////////////////////////
        // Downstairs curtains (living room and lobby)
        /////////////////////////////////////////////////

        var livingRoomCurtain = CurtainsCtrl({
            element: 'living-room-curtain',
            value: data.d_curtain
        });
        livingRoomCurtain.init();

        var lobbyCurtain = CurtainsCtrl({
            element: 'lobby-curtain',
            value: data.d_curtain
        });
        livingRoomCurtain.init();

        $("#downstairs-curtain-button").click(function () {
            livingRoomCurtain.toggle('living-room-curtain');
            lobbyCurtain.toggle('lobby-curtain');
        });


        /////////////////////////////////////////////////
        // Temperature
        /////////////////////////////////////////////////

        var tempCtrl = TempCtrl({
            value: data.temperature
        });
        tempCtrl.init();

        $(".temp-up-button").click(function () {
            tempCtrl.up();
        });

        $(".temp-down-button").click(function () {
            tempCtrl.down();
        });


    });

});