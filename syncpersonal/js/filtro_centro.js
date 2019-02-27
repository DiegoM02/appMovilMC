jQuery(document).ready(function($) {
	$("#select_centro").change(function()
	{

		var centro = $(this).val();

		$('#tabla_centro tr').each(function(){
 
        /* Obtener todas las celdas */
        var celdas = $(this).find('td');
 
        /* Mostrar el valor de cada celda */
        celdas.each(function(){ 

        	
        		
        	if (centro != $(celdas[4]).html())
        	{
        		$('#tabla_centro tr').show();
        	}
        		
        		
        	
        		

         });
        	
 
    });
	

		});


	});
