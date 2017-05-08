function sendAjax() {

    // get inputs
    var user = new Object();
    user.name =  $('#name').val();
    user.phoneNumbers = $('#pnumbers').val().split(";");

    $.ajax({
        url: "jsonservlet",
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(user),
        contentType: 'application/json',
        mimeType: 'application/json',

        success: function (data) {
            $("tr:has(td)").remove();

            $.each(data, function (index, user) {

                 var td_pnumbers = $("<td/>");
                $.each(user.phoneNumbers, function (i, pnumber) {
                    var span = $("<span class='label' style='margin:4px;padding:4px' />");
                    span.text(pnumber);
                    td_pnumbers.append(span);
                });
               var namespan = $("<span class='label' style='margin:4px;padding:4px' />");
               namespan.text(user.name)
                $("#added-users").append($('<tr/>')
                        .append(namespan)
                        .append(td_pnumbers)
                );

            });
        },
        error:function(data,status,er) {
            alert("error: "+data+" status: "+status+" er:"+er);
        }
    });
}