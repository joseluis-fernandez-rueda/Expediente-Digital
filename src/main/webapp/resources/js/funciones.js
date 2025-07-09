
$( document ).ready(function() {

    $('#myModal').modal('toggle');

    $(".textarea-label-in1").focus(function() {
        $(".label-in1").css("background-color", "#4e73df");
        $(".label-in1").css("border","solid 2px #4e73df");
        $(".label-in1").css("transform", "scale(1.02)");
        $(".label-in1").css("transition", "all .4s");
    });

    $(".textarea-label-in1").blur(function() {
        $(".label-in1").css("background-color", "#aaa");
        $(".label-in1").css("border","solid 2px #aaa");
        $(".label-in1").css("transform", "scale(1)");
        $(".label-in1").css("transition", "all .4s");
    });
    
    $(".textarea-label-in2").focus(function() {
        $(".label-in2").css("background-color", "#4e73df");
        $(".label-in2").css("border","solid 2px #4e73df");
        $(".label-in2").css("transform", "scale(1.02)");
        $(".label-in2").css("transition", "all .4s");
    });

    $(".textarea-label-in2").blur(function() {
        $(".label-in2").css("background-color", "#aaa");
        $(".label-in2").css("border","solid 2px #aaa");
        $(".label-in2").css("transform", "scale(1)");
        $(".label-in2").css("transition", "all .4s");
    });

    $(".textarea-label-in3").focus(function() {
        $(".label-in3").css("background-color", "#4e73df");
        $(".label-in3").css("border","solid 2px #4e73df");
        $(".label-in3").css("transform", "scale(1.02)");
        $(".label-in3").css("transition", "all .4s");
    });

    $(".textarea-label-in3").blur(function() {
        $(".label-in3").css("background-color", "#aaa");
        $(".label-in3").css("border","solid 2px #aaa");
        $(".label-in3").css("transform", "scale(1)");
        $(".label-in3").css("transition", "all .4s");
    });

    $(".select-label-in").focus(function() {
        $(".label-in-select").css("background-color", "#4e73df");
        $(".label-in-select").css("border","solid 2px #4e73df");
        $(".label-in-select").css("transform", "scale(1.02)");
        $(".label-in-select").css("transition", "all .4s");
    });

    $(".select-label-in").blur(function() {
        $(".label-in-select").css("background-color", "#aaa");
        $(".label-in-select").css("border","solid 2px #aaa");
        $(".label-in-select").css("transform", "scale(1)");
        $(".label-in-select").css("transition", "all .4s");
    });
});

