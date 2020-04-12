function selectTag(e) {
    var previous = $("#tag").val();
    var value = e.getAttribute("data-tag");
    if (previous) {
        $("#tag").val(previous + "," + value);
    } else {
        $("#tag").val(value);
    }

}

function showSelectTag() {
    $("#select-tag").show();
}