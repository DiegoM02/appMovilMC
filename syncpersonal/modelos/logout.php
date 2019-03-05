<?php 

include_once 'user_session.php';

$session = new UserSession();

$session->closeSession();

header ("location: ../index.php");






?>