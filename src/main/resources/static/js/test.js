$(document).ready(function() {
    var visible = false;
    $('input[name="sg2"]').click(function() {
        var divs = $('div.total-time');
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