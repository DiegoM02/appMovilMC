<?php
include_once 'db_functions_mcs.php';
//Create Object for DB_Functions clas
$db = new DB_Functions_MCS(); 
//Get JSON posted by Android Application
$json = $_POST["loginJSON"];
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

	$res = $db->login($data[$i]->user,$data[$i]->password);
	//Based on inserttion, create JSON response
	$result = mysqli_fetch_array($res);
	$b["name"] = $result["name"];
	$b["id"] = $result["id"];
	array_push($a,$b);
	
}
//Post JSON response back to Android Application
echo json_encode($a);
?>