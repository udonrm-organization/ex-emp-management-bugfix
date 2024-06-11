$(function(){
$('#searchName').on("keyup", function(){
    $.ajax({
        url: "/employee/autoComplete",
        dataType: "json",
        type: 'GET',
        data: {
          name: $('#searchName').val()
        }
    }).then(function(searchResult){
        $('#searchName').autocomplete({
            source : searchResult,
        });
    });
  })
});
