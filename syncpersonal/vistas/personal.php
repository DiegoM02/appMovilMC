
<?php  session_start(); ?>

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
  <body class="bg-light">

   


  <nav class="navbar navbar-expand-sm bg-primary navbar-dark ">
  <!-- Brand -->
  <a class="navbar-brand" href="#">Mujica & Docmac</a>

  <!-- Links -->
  <ul class="navbar-nav ">
    <li class="nav-item">
      <a class="nav-link" href="http://localhost/syncpersonal/vistas/inicio.php">Inicio</a>
    </li>
    <li class="nav-item active">
      <a class="nav-link" href="#">Personal</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">Resumen Evaluaciones</a>
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




<div class="row">


<div class="col-4">
  <div class="card w-75  mt-5 mx-auto">

  <div class="card-header bg-primary">
    <div class="display-5 text-white font-weight-bold">
      Filtros
    </div>
  </div>
  <div class="card-body">

    <div class="display-5 text-dark font-weight-bold">
      Centro de costos
    </div>
    <div class="dropdown-divider">
    </div>
    <?php require '../modelos/select_centro.php'?>
 <div class="display-5 text-dark font-weight-bold mt-2">
      Estado de personal
    </div>  
     <div class="dropdown-divider">
    </div>
     <select class="custom-select mt-2" id="select_estado">
        <option selected value="-1">Elija un estado</option>
        <option value="1">Habilitado</option>
        <option value="2">Deshabilitado</option>
  </select>


<div class="display-5 text-dark font-weight-bold mt-2">
      Periodo de contratación
    </div>  
     <div class="dropdown-divider">
    </div>
     <select class="custom-select mt-2">
        <option selected>Elija un periodo</option>
        <option value="1">1 dia</option>
        <option value="2">1 semana</option>
        <option value="2">1 mes</option>
        <option value="2">1 año</option>
  </select>
 



  </div>

  
    
  </div>
  
</div>

  <div class="align-content-center mt-5 col-8">
  <div class="card w-100 float-right mr-5">

   <!-- <div class="card-header bg-primary">
      <div class="container align-content-center">
        <h3 class="text-center text-white"> Personal </h3>
    </div>
    </div>-->
  

<div class="container w-100 align-content-center mt-2" >



<div class="container row w-100 mt-2 mb-2 align-content-center ml-2">
  <div class="col-10">
      <input class="form-control" id="myInput" type="text" placeholder="Busca una persona..">
  </div>
 
    <div class="col-2 align-content-end pr-4">
      <button class="btn btn-primary" onclick="refreshPage()">Actualizar</button>
    </div>
      
</div>
<div id="resultado">
   <?php require 'table_personal.php';?>
</div>
 
</div>
  
</div>
</div>
</div>


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javaScript" src="http://localhost/syncpersonal/js/filtro_centro.js"></script>

<script>

</script>
  

  </body>
</html>