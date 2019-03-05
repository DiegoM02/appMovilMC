<?php
include_once 'db_functions.php';
//Create Object for DB_Functions clas
$db = new DB_Functions(); 
//Get JSON posted by Android Application
$json = $_POST["personalJSON"];
//Remove Slashes
if (get_magic_quotes_gpc()){
$json = stripslashes($json);
}
//Decode JSON into an Array
$data = json_decode($json);
//Util arrays to create response JSON
$a=array();
$b=array();
//Loop through an Array and insert data read from JSON into MySQL DB
for($i=0; $i<count($data) ; $i++)
{
//Store User into MySQL DB

$res = $db->storePersonal($data[$i]->idPersonal,$data[$i]->namePersonal,$data[$i]->surnamePersonal,
$data[$i]->rutPersonal,$data[$i]->emailPersonal,$data[$i]->phonePersonal,$data[$i]->statePersonal,
$data[$i]->dateCreated,$data[$i]->facilityId);
	//Based on inserttion, create JSON response
	if($res){
		
		$b["id"] = $data[$i]->idPersonal;
		$b["status"] = 'yes';
		array_push($a,$b);
	}else{

		$b["id"] = $data[$i]->idPersonal;
		$b["status"] = 'no';
		array_push($a,$b);
	}
}
//Post JSON response back to Android Application
echo json_encode($a);
?>