<?php
include_once 'db_functions_mcs.php';
$json = $_POST["loginJSON"];
//Create Object for DB_Functions clas
$db = new DB_Functions_MCS(); 

//Get JSON posted by Android Application


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
	$checkUser = $db->checkUser($data[$i]->user);
	if($checkUser->num_rows>0)
	{
		$res = $db->login($data[$i]->user,$data[$i]->password);
		if($res->num_rows >0)
		{
			$result = mysqli_fetch_array($res);
			$b["name"] = $result["name"];
			$b["id"] = $result["id"];
			$b["role"] = $result["role"];
			$b["surname"] = $result["surname"];
			array_push($a,$b);		
		}
		else
		{
			$b["name"] = "NoPass";
			$b["id"] = "-3";
			$b["role"] = "-1";
			$b["surname"] = "";
			array_push($a,$b);
		}	
	}
	else
	{
		$b["name"] = "NoUser";
		$b["id"]  = "-1";
		$b["role"] = "-1";
		$b["surname"] = "";
		array_push($a,$b);
	}

	
	//Based on inserttion, create JSON response
	
	
}
//Post JSON response back to Android Application
echo json_encode($a);
?>