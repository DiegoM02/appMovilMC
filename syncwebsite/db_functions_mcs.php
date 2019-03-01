<?php
class DB_Functions_MCS {
    private $db;
    private $con;
    //put your code here
    // constructor
    function __construct() {
        include_once 'db_connect_mcs.php';
        // connecting to database
        $this->db = new DB_Connect_MCS();
        $this->con = $this->db->connect();
    }
    // destructor
    function __destruct() {
        
    }

    public function login($User,$Password)
    {
        $result = mysqli_query($this->con,"SELECT name,id FROM users WHERE password='$Password' AND username = '$User'");
        return $result;
    }
}
?>
