// Sound effects Component

SoundCtrl = (function () {
    
    var WINDING_DURATION_MS = 3000; // Actual duration of the sound played by windingSound

    var clickSound = function () {

        var audioElement = document.createElement('audio');
        audioElement.setAttribute('src', './assets/Button-click-sound.mp3');
        audioElement.play();

    };


    var windingSound = function () {

        var audioElement = document.createElement('audio');
        audioElement.setAttribute('src', './assets/Winding-sound.mp3');
        audioElement.play();
    }

    return {
        WINDING_DURATION_MS: WINDING_DURATION_MS,
        clickSound: clickSound,
        windingSound: windingSound
    }

})();