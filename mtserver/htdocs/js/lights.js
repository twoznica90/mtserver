// Lights Component
// Toggles background color of window frames to simulate lighting

LightsCtrl = function (params) {

    var CONST = {
        YELLOW: "rgb(255, 255, 0)",
        WHITE: "rgb(255, 255, 255)"
    }

    var init = function () {
        // Switch on the light if initial value is true
        if (params.value) {
            switchOn(params.element);
        }
    };


    function switchOn(elmt) {
        var frames = $('#' + elmt + ' div');
        frames.each(function (index, div) {
            $(div).css("background-color", CONST.YELLOW);
        });
    }


    function switchOff(elmt) {
        var frames = $('#' + elmt + ' div');
        frames.each(function (index, div) {
            $(div).css("background-color", CONST.WHITE);
        });
    }


    function toggle(elmt) {
        var frames = $('#' + elmt + ' div');
        frames.each(function (index, div) {

            if ($(div).css("background-color") != CONST.YELLOW) {
                $(div).css("background-color", CONST.YELLOW);
            } else {
                $(div).css("background-color", CONST.WHITE);
            }

        });
    }


    return {
        init: init,
        switchOn: switchOn,
        switchOff: switchOff,
        toggle: toggle
    }
};