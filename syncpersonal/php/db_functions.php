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

    public function getFacility($FacilityID)
    {
        $result =mysqli_query($this->con,"SELECT name FROM facility where id = '$FacilityID'");
        return $result;
    }
}
?>