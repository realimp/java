$(document).ready(function () {
    $("#add-task").submit(function (event) {
        event.preventDefault();
        if ($("#name").val != null || $("#name").val !="")
        fire_ajax_submit();
    });

    $('#items-list').on('click', '.delete-item', function(){
        var id = $(this).data('val');
        delete_item(id);
    });

    $('#items-list').on('click', '.item', function(){
            var id = $(this).attr('data-val');
            fire_ajax_get_item(id);
        });

    $("#show-list").click(function (){
        fire_ajax_get();
    });
});

function fire_ajax_submit() {

    var task = {};
    $("#add-task").find("input").each(function(){
        var inputType = this.tagName.toUpperCase() === "INPUT" && this.type.toUpperCase();
            if (inputType !== "BUTTON" && inputType !== "SUBMIT") {
                task[this.name] = $(this).val();
            }
    });

    $("#submit-task").prop("disabled", true);

    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "/to-do-items",
        data: JSON.stringify(task),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log("SUCCESS : ", task);
            $("#submit-task").prop("disabled", false);
            $("#add-task").find("input[type=text]").val(null);
            fire_ajax_get();
        },
        error: function (e) {
            console.log("ERROR : ", e);
            $("#submit-task").prop("disabled", false);
        }
    });
}

function fire_ajax_get(){
    $.getJSON("/to-do-items", function(data){
        $('#items-list').empty();
        $.each(data, function (index, value) {
             var taskCode = '<table class="item" data-val="' + value.id + '"><col width="276"><col width="24">' +
                '<tr><td><h2>' + value.name + '</h2></td>' +
                '<td><button class="btn delete-item" data-val="' + value.id + '"><i class="fa fa-trash"></i></button></td></tr></table>';
             $('#items-list').append('<div>' + taskCode + '</div>');
        });
    });
}

function fire_ajax_get_item(id){
    $.getJSON("/to-do-items/" + id, function(data){
        $('#item-details').empty();
        $('#item-details').append(data.details);
    });
}

function delete_item(id){
    $.ajax({
        method: "DELETE",
        url: "/to-do-items/" + id,
        success: function () {
            console.log("SUCCESS : item with id " + id + " deleted!")
            fire_ajax_get();
        }
    });
}

