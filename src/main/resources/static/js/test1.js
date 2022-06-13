// responseObj = readJsonFromUrl('http://localhost:8080/oneDayChart');
$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/oneDayChart/get"
    }).then(function(data) {
        const timeSlices = JSON.parse(data)


        for(var c = 0; c < timeSlices.length; c++) {
            console.log(timeSlices[c]["end"]);
        }

    });
});
