$(document).ready(function() {

    $("#loginSubmitBtn").click(function(e) {
        //$("input#loginStr").val();
        var userEmail = $('#loginStr').val();
        //var json = { "userEmail" : userEmail};

        $.ajax({
            url: "login",
            data: JSON.stringify(userEmail),
            type: "POST",

            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },

            success: function(data) {
                alert(data);
            },
            error:function(data,status,er) {
                alert("error: " + data + " status: " + status + " er:" + er);
            }
        });

          //e.preventDefault();
    });

});

/*$( "loginSubmitBtn" ).click(function() {
    sendAjax();
});

function sendAjax() {

// get inputs
    var userInput = $('#loginStr').val();

    $.ajax(
        {
            url: "/login",
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(userInput),
            contentType: 'application/json',
            mimeType: 'application/json',

            success: function (data) {
                alert("success! " + data);
            },
            error: function (data, status, er) {
                alert("error: " + data + " status: " + status + " er:" + er);
            }
        });
}*/

/*$("#loginSubmitBtn").click(function(e){

    //get the form data and then serialize that
    //dataString = $("#myAjaxRequestForm").serialize();

    //get the form data using another method
    var userEmail = $("input#loginStr").val();
    var dataString = "userEmail=" + userEmail;

    //make the AJAX request, dataType is set to json
    //meaning we are expecting JSON data in response from the server
    $.ajax({
        type: "POST",
        url: "login",
        data: dataString,
        dataType: "json",
        contentType: 'application/json',
        mimeType: 'application/json',

        //if received a response from the server
        success: function( data, textStatus, jqXHR) {
            //our country code was correct so we have some information to display
            if(data.success){
                alert("success:" + textStatus);
            }
            //display error message
            else {
                alert("failure:" + textStatus);
            }
        },

        //If there was no resonse from the server
        error: function(jqXHR, textStatus, errorThrown){
            alert("Something really bad happened " + textStatus + " some more: " + jqXHR.responseText);
        },

        //capture the request before it was sent to server
        beforeSend: function(jqXHR, settings){
            //adding some Dummy data to the request
            //settings.data += "&dummyData=whatever";
            //disable the button until we get the response
            $('#loginSubmitBtn').attr("disabled", true);
        },

        //this is called after the response or error functions are finsihed
        //so that we can take some action
        complete: function(jqXHR, textStatus){
            //enable the button
            $('#loginSubmitBtn').attr("disabled", false);
        }

    });
});*/
