var clockWedges = 24;


document.addEventListener("DOMContentLoaded", async (e) => {
    var page = document.getElementById('page')
    var wheelbox = document.getElementById('wheel-box')
    var locationbox = document.getElementById('locations')
    var wheel = document.getElementById('wheel')
    var radius = 150;
    var r = radius;

    const url = "http://localhost:8080/oneDayChart/get";
    const timeSlices = await fetch(url).then(x => x.json()).then((data) => data);

    timeSlices.forEach(function (slice) {
        r = radius-10

        var x1 = Math.floor(r * Math.sin(slice.start * -2 * Math.PI/clockWedges + Math.PI + 3/clockWedges));
        var y1 = Math.floor(r * Math.cos(slice.start * -2 * Math.PI/clockWedges + Math.PI + 3/clockWedges));
        var x2 = Math.floor(r * Math.sin(slice.end * -2 * Math.PI/clockWedges + Math.PI + 3/clockWedges));
        var y2 = Math.floor(r * Math.cos(slice.end * -2 * Math.PI/clockWedges + Math.PI + 3/clockWedges));

        var timeslice = document.createElementNS("http://www.w3.org/2000/svg", "path");
        var largeArcFlag = slice.end - slice.start > clockWedges/2? 1:0

        var arc = 'M ' + x1 + ' ' + y1 + ' A ' + r + ' ' + r + ' 0 ' + largeArcFlag + ' 1 ' + x2 + ' ' + y2 + ' L 0 0 Z'


        timeslice.setAttribute('d', arc)
        timeslice.setAttribute('class', slice.className)
        wheel.appendChild(timeslice)

    })

    for (var i=0; i < clockWedges; i++) {
        r = radius
        var theta = i * -2*Math.PI/clockWedges + Math.PI;
        var x = Math.floor((r-40) * Math.sin(theta));
        var y = Math.floor((r-40) * Math.cos(theta));
        var x1 = Math.floor(r * Math.sin(theta));
        var y1 = Math.floor(r * Math.cos(theta));
        var x2 = Math.floor((r-20) * Math.sin(theta));
        var y2 = Math.floor((r-20) * Math.cos(theta));
        var line = document.createElementNS("http://www.w3.org/2000/svg", "line");
        line.setAttribute('x1', x1)
        line.setAttribute('y1', y1)
        line.setAttribute('x2', x2)
        line.setAttribute('y2', y2)

        var hourText = document.createElementNS("http://www.w3.org/2000/svg", "text");
        hourText.setAttribute('x', x)
        hourText.setAttribute('y', y)
        hourText.innerHTML = i

        wheel.appendChild(hourText)
    }





});

function rotateTimewheel(e) {
    var wheel = document.getElementById('wheel')
    var reg = /rotateZ\((.*)rad\)/g;
    var regMatches = reg.exec(wheel.style.transform)
    var rads = regMatches? Number(regMatches[1]) : 0
    var newRads = rads

    switch (e.keyCode) {
        case 37:
            newRads -= 2*Math.PI/clockWedges
            break
        case 39:
            newRads += 2*Math.PI/clockWedges
            break
    }

    wheel.style.transform = 'rotateZ(' + newRads + 'rad)'
}

document.addEventListener("keydown", function (e) {
    throttle(rotateTimewheel, e);
}, false);


var throttle = (function () {
    var timeWindow = 200; // time in ms
    var lastExecution = new Date((new Date()).getTime() - timeWindow);

    return function (fn, e) {
        if ((lastExecution.getTime() + timeWindow) <= (new Date()).getTime()) {
            lastExecution = new Date();
            fn(e)
        }
    };
}());
