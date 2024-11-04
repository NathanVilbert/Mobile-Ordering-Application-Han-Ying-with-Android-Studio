<?php
require_once 'config.php';

function register($username, $custname, $custemail, $custphone, $password, $role = 'user')
{
    global $conn;

    $username = mysqli_real_escape_string($conn, $username);
    $custname = mysqli_real_escape_string($conn, $custname);
    $custemail = mysqli_real_escape_string($conn, $custemail);
    $custphone = mysqli_real_escape_string($conn, $custphone);
    $password = mysqli_real_escape_string($conn, $password);
    $role = mysqli_real_escape_string($conn, $role);

    $query = "INSERT INTO user (username, custname, custemail, custphone, password, role) VALUES ('$username', '$custname', '$custemail', '$custphone', '$password', '$role')";

    if ($conn->query($query)) {
        return array("status" => "success", "message" => "Account created successfully!");
    } else {
        return array("status" => "error", "message" => "Failed to create account");
    }
}

header('Content-Type: application/json');

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $inputUsername = $_POST["username"];
    $inputCustname = $_POST["custname"];
    $inputCustemail = $_POST["custemail"];
    $inputCustphone = $_POST["custphone"];
    $inputPassword = $_POST["password"];

    $result = register($inputUsername, $inputCustname, $inputCustemail, $inputCustphone, $inputPassword);

    echo json_encode($result);
} else {
    echo json_encode(array("error" => "Invalid Request"));
}
?>