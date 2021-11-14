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


     /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name, $email, $password) {
       
        $stmt = $this->conn->prepare("INSERT INTO users(name, email, password, created_at) VALUES( ?, ?, ?, NOW())");
        $stmt->bind_param("sss", $name, $email, $password);
        $result = $stmt->execute();
        $stmt->close();
 
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM users WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }
    }


     /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $stmt = $this->conn->prepare("SELECT email from users WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }

    
    
 }






?>