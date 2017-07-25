/**
 * Created by Logan.Moen on 6/27/2017.
 */
"use strict";
var makeObject = {
    "Ford": ["Fusion", "Ranger"],
    "Chevy": ["Equinox", "S10"],
    "Mazda": ["Tribute", "B4000"],
    "Hyundai": ["Tiburon", "Elantra"],
    "Volkswagen": ["Jetta", "Beetle"]
};
window.onload = function() {
    var makeSel = document.getElementById("selectMake"),
        modelSel = document.getElementById("selectModel");
    for(var make in makeObject) {
        makeSel.options[makeSel.options.length] = new Option(make, make);
    }
    makeSel.onchange = function() {
        modelSel.length = 1;
        var models = makeObject[this.value];
        for(var i = 0; i < models.length; i++) {
            modelSel.options[modelSel.options.length] = new Option(models[i], models[i]);
        }
    }
};
