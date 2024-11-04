<?php
require_once 'config.php';

function getMenuList($category)
{
    global $conn;

    $query = "SELECT foodname, foodpic, fooddesc, foodprice FROM menu_item WHERE foodcat = '$category'";
    $result = $conn->query($query);

    if ($result->num_rows > 0) {
        $response = array();
        while ($row = $result->fetch_assoc()) {
            $response[] = $row;
        }
        return $response;
    } else {
        return array("status" => "error", "message" => "No menu items found for this category");
    }
}

header('Content-Type: application/json');

if ($_SERVER["REQUEST_METHOD"] == "GET") {
    if (isset($_GET["foodcat"])) {
        $category = $_GET["foodcat"];
        $result = getMenuList($category);
        echo json_encode($result);
    } else {
        echo json_encode(array("error" => "Invalid Request"));
    }
} else {
    echo json_encode(array("error" => "Invalid Request"));
}
?>