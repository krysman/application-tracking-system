/**
 * Created by Ivan on 4/2/2015.
 */

$(document).ready(function() {

    $("#submitNewApplicant").click(function(e) {
        //$("input#loginStr").val();
        var firstName = $('#InputFirstName').val();
        var lastName = $('#InputLastName').val();

        var json = { "firstName" : firstName, "lastName" : lastName};

        $.ajax({
            url: "createApplicant",
            data: JSON.stringify(json),
            type: "POST",

            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },

            success: function(data) {
                swal(data, "Applicant info added!", "success")
            },
            error:function(data,status,er) {
                sweetAlert("Oops...", "error: " + data + " status: " + status + " er:" + er, "error");
                //alert("error: " + data + " status: " + status + " er:" + er);
            }
        });

        //e.preventDefault();
    });

});