<?php
$servername = "localhost";
$username = "root";
$password = "";
$database = "hanying";

$conn = new mysqli($servername, $username, $password, $database);

if ($conn->connect_error) {
    die(json_encode(array("error" => "Connection failed: " . $conn->connect_error)));
}
?>