<?php
class DB_Functions {
    private $db;
    private $con;
    //put your code here
    // constructor
    function __construct() {
        include_once 'db_connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->con = $this->db->connect();
    }
    // destructor
    function __destruct() {
        
    }
    /**
     * Storing new user
     * returns user details
     */
    public function storePersonal($Id , $Name, $Surname,$Rut,$Email,$Phone,$State,$Created,$FacilityID) {
        // Insert user into database
    $result = mysqli_query($this->con,"INSERT INTO personal (id, name, surname, rut, email, phone, created, state, facility_id) VALUES ('$Id','$Name','$Surname','$Rut','$Email','$Phone','$Created','$State','$FacilityID');");
		
        if ($result) {
			return true;
        } else {
			if( mysqli_errno($this->con) == 1062) {
				// Duplicate key - Primary Key Violation
				return false;
			} else {
				// For other errors
				return false;
			}            
        }
    }
	 /**
     * Getting all users
     */
    public function getAllPersonal() {

        $result = mysqli_query($this->con,"SELECT * FROM personal");
        return $result;
    }


    public function updatePersonal( $rut, $estado)
    {
         $result = mysqli_query($this->con,"UPDATE personal SET state = '$estado' WHERE rut = '$rut';");
        return $result;
    }


    public function storeFacility($Id,$UserId,$Created,$Code,$Name,$Address)
    {
        $result = mysqli_query($this->con,"INSERT INTO facility (id, user_id, created, code, name, address) VALUES ('$Id','$UserId','$Created','$Code','$Name','$Address');");
        
        if ($result) {
            return true;
        } else {
            if( mysqli_errno($this->con) == 1062) {
                // Duplicate key - Primary Key Violation
                return false;
            } else {
                // For other errors
                return false;
            }            
        }
    }

    public function getFacilityForId($Id)
    {
        $name="";
         $result = mysqli_query($this->con,"SELECT name AS nombre FROM facility WHERE facility.id = '$Id'");
         while ($row = mysqli_fetch_array($result))
         {
            $name = $row["nombre"];
         }
        return $name;   
    }

    public function getAllFacility() {

        $result = mysqli_query($this->con,"SELECT * FROM facility");
        return $result;
    }

    public function getPersonalForIdFacility($Id)
    {
         $result = mysqli_query($this->con,"SELECT * FROM personal WHERE facility_id = '$Id'");
         return $result;
    }

    public function selectAllFacility($id)
    {
        $result = mysqli_query($this->con,"SELECT facilities.id AS id_facility, T1.user_id_facility AS user_id, facilities.created AS date_facility,facilities.code AS code_facility, facilities.name AS name_facility, facilities.address AS address_facility, facilities.service_id AS service_id_facility FROM facilities, (SELECT facility_id AS facility_id_users, facilities_users.user_id AS user_id_facility  FROM facilities_users WHERE  facilities_users.user_id = '$id') AS T1 WHERE  facilities.id = T1.facility_id_users;") ;
        return $result;
    
    }

    function selectAllAspect()
    {
        $result = mysqli_query($this->con,"SELECT id, name, created, approval_percentage  FROM aspects;");
        return $result;
    }

    public function login($User,$Password)
    {
        $result = mysqli_query($this->con,"SELECT name,surname,id,role FROM users WHERE password='$Password' AND username = '$User'");
        return $result;
    }

    public function  checkUser($User)
    {
        $result = mysqli_query($this->con,"SELECT name FROM users WHERE username ='$User'");
        return $result;
    } 
    public function getQuestions()
    {
        $result = mysqli_query($this->con,"SELECT description,aspect_id,point_id FROM question");
        return $result;
    }

    public function getAspect($Aspect_id)
    {
        $result = mysqli_query($this->con,"SELECT name FROM aspect WHERE id = '$Aspect_id'");
        $row = mysqli_fetch_array($result);
        return $row["name"];
    }

    public function getPoint($Point_id)
    {
        $result = mysqli_query($this->con,"SELECT name FROM point WHERE id = '$Point_id'");
        $row = mysqli_fetch_array($result);
        return $row["name"];
    }

    public function getAllSummary() 
    { 
        $result = mysqli_query($this->con,"SELECT * FROM summary"); 
        return $result; 
    } 
 
    public function storeSummary($Id,$Content,$Date,$FacilityID) 
    { 
        $result =mysqli_query($this->con,"INSERT INTO summary (id,content,created,facility_id) VALUES ('$Id','$Content','&Date','$FacilityID');"); 
        if ($result) { 
            return true; 
        } else { 
            if( mysqli_errno($this->con) == 1062) { 
                // Duplicate key - Primary Key Violation 
                return false; 
            } else { 
                // For other errors 
                return false; 
            }             
        } 
    }
}
?>