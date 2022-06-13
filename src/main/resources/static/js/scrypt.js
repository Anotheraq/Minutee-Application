const categoryObject = {
    "Movie": {},
    "Games": {},
    "Job": {},
    "Family": {},
    "Sport": {},
    "Relax": {},
    "Outdoor": {},
    "Entertainment": {}
};
window.onload = function() {
    const categorySel = document.getElementById("category");
    for (const x in categoryObject) {
        categorySel.options[categorySel.options.length] = new Option(x, x);
    }
}

$(document).ready(function() {
    let visible = false;
    $('input[value="2"]').click(function(e) {
        const divs = $('div.some-class');
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