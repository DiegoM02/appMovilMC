<?php
include_once 'db_function.php';
//Create Object for DB_Functions clas

$idUser = "";
if (!empty($_POST["idUser"]))
{
	$idUser = $_POST["idUser"];
}


$db = new DB_Functions(); 
//Get JSON posted by Android Application
//Remove Slashes
//Decode JSON into an Array

//Util arrays to create response JSON
$result = $db->selectAllFacility($idUser);

 $facilityData = array();
 $a = array();
 while($row= mysqli_fetch_array($result)){
  
  		$facilityData["id_facility"] = $row["id_facility"];
  		$facilityData["user_id"] = $row["user_id"];
  		$facilityData["date_facility"] = $row["date_facility"];
  		$facilityData["code_facility"] = $row["code_facility"];
  		$facilityData["name_facility"] = $row["name_facility"];
  		$facilityData["address_facility"] = $row["address_facility"];
  		$facilityData["service_id_facility"] = $row["service_id_facility"];
  		array_push($a,$facilityData);
 	}
 
 	echo json_encode($a);
?>