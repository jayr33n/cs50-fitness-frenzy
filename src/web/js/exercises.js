const ELEMENTS_PER_ROW = 3

$(document).ready(function () {
    $.get("http://localhost:8080/exercises", async function (data) {
        let row;
        for (let i = 0; i < data["numberOfElements"]; i++) {
            let tools = await $.get("http://localhost:8080/exercises/" + (i + 1) + "/tools")
            let equipment = "";
            for (let j = 0; j < tools["numberOfElements"]; j++)
                equipment = equipment.concat('<h6 class="w-100 fw-bold text-center">' + tools["content"][j]["name"] + '</h6>')

            if ((i % ELEMENTS_PER_ROW) === 0)
                row = $("<div class=\"d-flex align-items-center justify-content-center my-4\"></div>")
            row.append(
                '<div class="card mx-3" style="width: 17rem;">' +
                '   <div class="d-flex justify-content-center align-items-center">' +
                '       <img class="card-img-top img-fluid w-25 py-4" src="img/dumbbell.png" alt="Exercise Preview">' +
                '   </div>' +
                '   <div class="card-body px-5 py-4">' +
                '       <h6 class="card-title fw-bold text-center">' + data["content"][i]["name"] + "</h6>" +
                '       <h5 class="w-100 fw-bold text-uppercase text-center">' + data["content"][i]["difficulty"] + "</h5>" +
                '       <div class="d-flex justify-content-center align-items-center text-uppercase">' + equipment + '</div>' +
                '       </div>' +
                '</div>')
            $("main").append(row)
        }
    })

    $("#new-exercise").submit(function () {
        let name = $("#form-name").val()
        let difficulty = $("#form-difficulty").val()
        switch (difficulty) {
            case "Beginner":
                difficulty = "beg"
                break
            case "Intermediate":
                difficulty = "int"
                break
            case "Advanced":
                difficulty = "adv"
                break
        }
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/exercises",
            data: JSON.stringify({
                "name": name,
                "difficulty": difficulty
            }),
            dataType: "json",
            contentType: "application/json"
        })
    })
})
