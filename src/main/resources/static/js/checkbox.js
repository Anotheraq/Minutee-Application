$(document).ready(function() {
    let visible = false;

    $('input[value="1"]').click(function() {
        let divs = $('div.total-time');
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