/**
 * Created by ShenShuaihu on 2018/7/17.
 */


/*$("li").onmouseover(
    function () {
        alert("s    ")
        $("li").css("background-color","yellow");
       // $("p").hide();
        $("p").show();
    }
);*/
$("ul").mouseenter(function(){
    alert("s    ")
    $("ul").css("background-color","yellow");
});
$("ul").mouseleave(function(){
    $("ul").css("background-color","#E9E9E4");
});