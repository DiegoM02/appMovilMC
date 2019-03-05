


$(document).ready(function(){
  $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#tabla_centro tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });


  $("#select_centro").change(function()
	{
		$.post('../modelos/filtrar_centro.php',
			{id: $("#select_centro").val()},
			function(data)
			{
				$("#resultado").html(data);
			}




			);
});



   $("#select_estado").change(function()
	{
		$.post('../modelos/filtrar_centro.php',
			{state: $("#select_centro").val()},
			function(data)
			{
				$("#resultado").html(data);
			}




			);
});

});



