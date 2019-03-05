<?php 

	include_once 'modelos/user_session.php';

	$userSession = new UserSession();

	if (isset($_SESSION['user']))
	{
		include_once 'vistas/inicio.php';
		
	}else if (isset($_POST['username']) && isset($_POST['password']))
	{
		$userForm = $_POST['username'];
		$passForm = $_POST['password'];

		if ($userForm == "mujica&docmac" && $passForm == "P@ssw0rdMD")
		{
			
			$userSession->setCurrentUser($userForm);
			include_once 'vistas/inicio.php';

		}else{
			$errorLogin = "Nombre de usuario y/o password es incorrecto";
			include_once 'vistas/login.php';
		}

	}else
	{
		include_once('vistas/login.php');
	}


?>