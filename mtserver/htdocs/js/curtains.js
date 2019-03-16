// Curtain Component

CurtainsCtrl = function (params) {

    var init = function () {
        // Close curtain if initial value is true
        if (params.value) {
            close(params.element);
        }
    };

    function toggle(elmt) {
        SoundCtrl.windingSound();
        $('#' + elmt).slideToggle(SoundCtrl.WINDING_DURATION_MS);
    }


    function open(elmt) {
        SoundCtrl.windingSound();
        $('#' + elmt).slideUp(SoundCtrl.WINDING_DURATION_MS);
    }

    function close(elmt) {
        SoundCtrl.windingSound();
        $('#' + elmt).slideDown(SoundCtrl.WINDING_DURATION_MS);
    }


    return {
        init: init,
        toggle: toggle,
        open: open,
        close: close
    }
};