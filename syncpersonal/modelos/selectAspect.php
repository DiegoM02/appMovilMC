<?php
include_once 'db_function.php';
//Create Object for DB_Functions clas

/*$idAspect = "";
if (!empty($_POST["idAspect"]))
{
	$idAspect = $_POST["idAspect"];
}*/


$db = new DB_Functions(); 
//Get JSON posted by Android Application
//Remove Slashes
//Decode JSON into an Array

//Util arrays to create response JSON
$result = $db->selectAllAspect();

 $aspectData = array();
 $a = array();
 while($row= mysqli_fetch_array($result)){
  
  		$aspectData["id"] = $row["id"];
  		$aspectData["name"] = $row["name"];
  		$aspectData["created"] = $row["created"];
  		$aspectData["approval_percentage"] = $row["approval_percentage"];
  		array_push($a,$aspectData);
 	}
 
 	echo json_encode($a);
?>