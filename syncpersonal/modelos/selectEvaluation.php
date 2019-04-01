<?php
include_once 'db_function.php';
//Create Object for DB_Functions clas

$idUser = "";
if (!empty($_POST["idFacility"]))
{
	$idUser = $_POST["idFacility"];
}


$db = new DB_Functions(); 
//Get JSON posted by Android Application
//Remove Slashes
//Decode JSON into an Array

//Util arrays to create response JSON
$result = $db->selectEvaluation($idFacility);

 $evaluationData = array();
 $a = array();
 while($row= mysqli_fetch_array($result)){
  
  		$evaluationData["id"] = $row["id"];
  		$evaluationData["done"] = $row["done"];
  		$evaluationData["facility_id"] = $row["facility_id"];
  		array_push($a,$facilityData);
 	}
 
 	echo json_encode($a);
?>