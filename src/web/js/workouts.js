$(document).ready(function () {
    $.get("http://localhost:8080/workouts", async function (data) {
        for (let workout of data["content"]) {
            let exercises = ""
            if (workout["exercises"] !== undefined)
                for (let exercise of workout["exercises"])
                    exercises = exercises.concat(
                        '<div class="d-flex justify-content-between px-3 py-2 border-top">' +
                        '   <img class="px-2" style="max-width: 7rem;" src="img/dumbbell.png" alt="Exercise Preview">' +
                        '   <div class="w-100 px-2">' +
                        '       <h4>Exercise Name</h4>' +
                        '       <p>' + exercise["exercise"]["name"] + '</p>' +
                        '   </div>' +
                        '   <div class="w-75 px-2">' +
                        '       <h4>Sets</h4>' +
                        '       <p>' + exercise["sets"] + '</p>' +
                        '   </div>' +
                        '   <div class="w-75 px-2">' +
                        '       <h4>Repetitions</h4>' +
                        '       <p>' + exercise["repetitions"] + '</p>' +
                        '   </div>' +
                        '   <div class="w-75 px-2">' +
                        '       <h4>Rest Interval</h4>' +
                        '       <p>' + exercise["restInterval"] + '</p>' +
                        '   </div>' +
                        '</div>')

            $("#workouts").append(
                '<div class="border d-flex flex-column mb-5">' +
                '   <div class="d-flex align-self-center">' +
                '       <h3 class="text-uppercase fw-bold p-2">' + workout["name"] + '</h3>' +
                '       <p class="align-self-end text-muted">{' + workout["author"] + '}</p>' +
                '   </div>' + exercises +
                '</div>')
        }
    })
})
