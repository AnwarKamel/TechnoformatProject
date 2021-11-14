<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
// json response array
$response = array("error" => FALSE);
if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {
 
    // receiving the post params
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    // check if user is already existed with the same email
    if ($db->isUserExisted($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        $response["error_code"] = 1;
        echo json_encode($response);
    } else {
  // create a new user
        $user = $db->storeUser($name, $email, $password);
        if ($user) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["name"] = $user["name"];
            $response["email"] = $user['email'];
            $response["created_at"] = $user["created_at"];
            $response["updated_at"] = $user["updated_at"];
            $response["error_code"] = 0; // sucssus

            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_code"] = 2; // Unknown error occurred in registration
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
}
else {
    $response["error"] = TRUE;
    $response["error_code"] = 2;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}
?>