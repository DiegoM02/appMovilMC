<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script>
    function refreshPage(){
      location.reload();
          }
    </script>
    <title>Personal</title>
  </head>
  <body>
  <nav class="navbar navbar-expand-sm bg-primary navbar-dark">
  <!-- Brand -->
  <a class="navbar-brand" href="#">Mujica & Docmac</a>

  <!-- Links -->
  <ul class="navbar-nav ">
    <li class="nav-item">
      <a class="nav-link" href="http://localhost/syncpersonal/vistas/inicio.php">Inicio</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="http://localhost/syncpersonal/vistas/personal.php">Personal</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="http://localhost/syncpersonal/vistas/summary.php">Resumen Evaluaciones</a>
    </li>
    <li class="nav-item active">
      <a class="nav-link" href="#">Preguntas</a>
    </li>
  </ul>
  <div class="collapse navbar-collapse flex-grow-1 text-right" id="myNavbar">
<ul class="navbar-nav ml-auto flex-nowrap"> 

    <li class="nav-item">
      <a class="nav-link" href="http://localhost/syncpersonal/modelos/logout.php">Cerrar sesión</a>
    </li> 
    
  </ul>
</div>
</nav>

</nav>

<div class="container align-content-center mt-5">
  <div class="card  flex-wrap-reverse">

   <!-- <div class="card-header bg-primary">
      <div class="container align-content-center">
        <h3 class="text-center text-white"> Personal </h3>
    </div>
    </div>-->
  

<div class="container w-100 align-content-center mt-2" >



<div class="container row w-100 mt-2 mb-2 align-content-center">
  <div class="col-11">
      <input class="form-control" id="myInput" type="text" placeholder="Busca una pregunta..">
  </div>
 
    <div class="col-1 align-content-end">
      <button class="btn btn-primary" onclick="refreshPage()">Actualizar</button>
    </div>
      
</div>
  <?php require '../modelos/viewQuestion.php';?>
</div>
  
</div>
</div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<script>
$(document).ready(function(){
  $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});
</script>
  </body>
</html>