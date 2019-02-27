<?php 
session_start();
define("USERNAME", "mujica&docmac");
define("PASSWORD", "P@ssw0rdMD");


$user = $_POST['user'];
$password = $_POST['password'];


if ($user == USERNAME && $password == PASSWORD)
{

	$_SESSION["autorizado"] = true;
	 session_regenerate_id();
	header('Location: http://localhost/syncpersonal/inicio.php');
}else
{
	$_SESSION["autorizado"] = false;
	header('Location: http://localhost/syncpersonal/login.html');
}

?>