const ELEMENTS_PER_ROW = 3

$(document).ready(function () {
    $.get("http://localhost:8080/tools", function (data) {
        let row;
        for (let i = 0; i < data["numberOfElements"]; i++) {
            if ((i % ELEMENTS_PER_ROW) === 0)
                row = $("<div class=\"d-flex align-items-center justify-content-center my-4\"></div>")

            row.append(
                '<div class="card mx-3" style="width: 17rem;">' +
                '   <div class="d-flex justify-content-center align-items-center">' +
                '       <img class="card-img-top img-fluid w-25 py-4" src="img/wrench.png" alt="Tool Preview">' +
                '   </div>' +
                '   <div class="card-body px-5 py-4">' +
                '       <h6 class="card-title fw-bold text-center">' + data["content"][i]["name"] + "</h6>" +
                '   </div>' +
                '</div>')
            $("main").append(row)
        }
    })

    $("#new-tool").submit(function () {
        let name = $("#form-name").val()
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/tools",
            data: JSON.stringify({
                "name": name,
            }),
            dataType: "json",
            contentType: "application/json"
        });
    })
})
