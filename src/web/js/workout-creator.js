let exerciseIndex = 0;
let exercises = null;
let options = ""

function getExerciseIdFromName(name) {
    for (let exercise of exercises) {
        if (exercise["name"] === name)
            return exercise["id"]
    }
}

$(document).ready(function () {
    $.get("http://localhost:8080/exercises", async function (data) {
        exercises = data["content"];
        for (let exercise of exercises)
            options = options.concat("<option>" + exercise["name"] + "</option>")
    })

    $("#add-exercise").click(function () {
        let section =
            '<div class="form-group my-3">' +
            '   <div class="d-flex form-labels justify-content-center align-items-center">' +
            '       <label class="fw-bold w-100" ' +
            '           for="form-exercise-name-' + exerciseIndex + '">Name</label>' +
            '       <label class="fw-bold w-50" ' +
            '           for="form-exercise-sets-' + exerciseIndex + '">Sets</label>' +
            '       <label class="fw-bold w-75" ' +
            '           for="form-exercise-repetitions-' + exerciseIndex + '">Repetitions</label>' +
            '       <label class="fw-bold w-100" ' +
            '           for="form-exercise-rest-interval-' + exerciseIndex + '">Rest Interval</label>' +
            '   </div>' +
            '   <div class="d-flex justify-content-center align-items-center">' +
            '       <select class="form-control w-100" ' +
            '           id="form-exercise-name-' + exerciseIndex + '">' + options + '</select>' +
            '       <input autocomplete="off" class="form-control w-50" ' +
            '           id="form-exercise-sets-' + exerciseIndex + '" placeholder="Sets" type="number">' +
            '       <input autocomplete="off" class="form-control w-75" ' +
            '           id="form-exercise-repetitions-' + exerciseIndex + '" placeholder="Repetitions" type="number">' +
            '       <input autocomplete="off" class="form-control w-100" ' +
            '           id="form-exercise-rest-interval-' + exerciseIndex + '" placeholder="Seconds" type="number">' +
            '   </div>' +
            '</div>'
        $("#form-exercises").append(section)
        exerciseIndex++
    })

    $("#workout-creator").submit(function () {
        let addedExercises = []
        for (let i = 0; i < exerciseIndex; i++) {
            let id = getExerciseIdFromName($("#form-exercise-name-" + i).val());
            let sets = $("#form-exercise-sets-" + i).val()
            let repetitions = $("#form-exercise-repetitions-" + i).val()
            let restInterval = $("#form-exercise-rest-interval-" + i).val()
            addedExercises.push({
                exerciseId: id,
                sets: sets,
                repetitions: repetitions,
                restInterval: restInterval,
            })
        }
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/workouts",
            data: JSON.stringify({
                "name": $("#form-workout-name").val(),
                "author": $("#form-workout-author").val(),
                "exercises": addedExercises
            }),
            dataType: "json",
            contentType: "application/json"
        })
    })
})
