const acc = document.getElementsByClassName("accordion");
const HEIGHT_OFFSET = 20;

for (let i = 0; i < acc.length; i++) {
    acc[i].addEventListener("click", function() {
        this.classList.toggle("active");
        const panel = this.nextElementSibling;
        if (panel.style.height) {
            panel.style.height = null;
        } else {
            panel.style.height = panel.scrollHeight + HEIGHT_OFFSET + "px";
        }
    });
}