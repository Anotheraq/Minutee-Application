var categoryObject = {
    "ICON1": {},
    "ICON2": {}
}
window.onload = function() {
    var categorySel = document.getElementById("category");
    for (var x in categoryObject) {
        categorySel.options[categorySel.options.length] = new Option(x, x);
    }
}

$(document).ready(function() {
    var visible = false;
    $('input[name="sg"]').click(function() {
        var divs = $('div.some-class');
        if (visible) {
            divs.each(function() {
                this.style.display = 'none';
            });
            visible = false;
        }
        else {
            divs.each(function() {
                this.style.display = 'block';
            });
            visible = true;
        }
    });
});