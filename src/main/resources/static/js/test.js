// jQuery(document).ready(function($) {
//     $("#habitForm").submit(function(event) {
//         event.preventDefault();
//         loadCreateHabitForm();
//     })
// })

// function loadCreateHabitForm() {

//     var xhttp = new XMLHttpRequest();
//     xhttp.onreadystatechange = function() {
//         if (this.readyState == 4 && this.status == 200) {
//             document.getElementById("habitform").innerHTML = this.responseText;
//             console.log("This worked!")
//         }
//     }
//     xhttp.open("GET", "./habits/create.html", true);
//     xhttp.send();
// }

function fire_ajax_submit() {

    var search = {}
    search["username"] = $("#username").val();

    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/habits/create",
        dataType: 'xml',
        cache: false,
        timeout: 600000,
        success: function(data) {

            var json = "<h4>Ajax Response</h4>&lt;pre&gt;" +
                JSON.stringify(data, null, 4) + "&lt;/pre&gt;";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);

        },
        error: function(e) {

            var json = "<h4>Ajax Response</h4>&lt;pre&gt;" +
                e.responseText + "&lt;/pre&gt;";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}