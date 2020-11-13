$(document).ready(() => {
    console.log('ready');
    $('#get').click(() => {
        console.log('get click');
        $.ajax({
            url: "getGreet"
        }).then(function(data) {
            console.log(data);
        });
    });

    $('#post').click(() => {

        let formData = {
            id: 100,
            name: '吧！'
        };
        console.log(formData);
        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "putGreet",
            data : JSON.stringify(formData),
            dataType : 'json',
            success : function(result) {
                console.log(result);
            },
            error : function(e) {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    })
});

