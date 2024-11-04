<?php
require_once 'config.php';

function login($username, $password)
{
    global $conn;

    $username = mysqli_real_escape_string($conn, $username);
    $password = mysqli_real_escape_string($conn, $password);

    $query = "SELECT * FROM user WHERE username = '$username' AND password = '$password'";
    $result = $conn->query($query);

    if ($result->num_rows > 0) {
        $user = $result->fetch_assoc();
        $custname = getCustName($user['username']);
        return array("status" => "success", "message" => "Welcome, " . $custname);
    } else {
        return array("status" => "error", "message" => "Invalid username or password");
    }
}

function getCustName($username)
{
    global $conn;

    $username = mysqli_real_escape_string($conn, $username);

    $query = "SELECT custname FROM user WHERE username = '$username'";
    $result = $conn->query($query);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        return $row['custname'];
    } else {
        return "Undefined";
    }
}

header('Content-Type: application/json');

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $inputUsername = $_POST["username"];
    $inputPassword = $_POST["password"];

    $result = login($inputUsername, $inputPassword);

    echo json_encode($result);
} else {
    echo json_encode(array("error" => "Invalid Request"));
}
?>