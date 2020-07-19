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