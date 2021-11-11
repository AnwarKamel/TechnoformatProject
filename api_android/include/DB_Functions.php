<?php
class DB_Functions {
    private $conn; 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password) {
 
    $stmt = $this->conn->prepare("SELECT * FROM users  WHERE email = ? and password = ?");
 
    $stmt->bind_param("ss", $email, $password);
    $stmt->execute();
    $user = $stmt->get_result()->fetch_assoc();
         
    if ($user) {
        // user authentication details are correct     
        return $user;
        } 
        else {
           return false;
       }
 }
 }
?>