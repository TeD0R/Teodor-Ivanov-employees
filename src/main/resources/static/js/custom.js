$(function () {
    $('input[type="file"]').change(function () {
        var fd = new FormData($('#fileForm')[0]);
        fd.append('file', $('#file')[0].files[0]);

        $.ajax({
            url: '/readFile',
            type: 'POST',
            data: fd,
            success: function (data) {
                console.log(data);
                let html_row = ``;

                for (let i = 0; i < data.length; i++) {
                    let row = data[i];
                    // let employees = Object.keys(row).filter(value => value.startsWith('employee_'));


                    let table_row = `
                        <tr>
                            <th scope="row">${row['employee_0']}</th>
                            <td>${row['employee_1']}</td>
                            <td>${row['project_id']}</td>
                            <td>${row['days']}</td>
                        </tr>

                    `
                    html_row += table_row;

                }

                let html = `
                    <table class="table text-white w-100">
                      <thead>
                        <tr>
                          <th scope="col">Employee ID #1</th>
                          <th scope="col">Employee ID #2</th>
                          <th scope="col">Project ID</th>
                          <th scope="col">Days Worked</th>
                        </tr>
                      </thead>
                      <tbody>
                            ${html_row}
                      </tbody>
                    </table>

                    `

                $('#table_append').append(html);
            },
            cache: false,
            contentType: false,
            processData: false
        });
    });
});