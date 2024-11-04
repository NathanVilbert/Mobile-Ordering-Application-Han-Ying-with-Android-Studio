<?php
include 'config.php';

function getPopularList()
{
    global $conn;

    $query = "SELECT foodname, foodpic, fooddesc, foodprice FROM menu_item";
    $result = $conn->query($query);

    if ($result->num_rows > 0) {
        $response = array();
        while ($row = $result->fetch_assoc()) {
            $response[] = $row;
        }
        return $response;
    } else {
        return array("status" => "error", "message" => "No popular items found");
    }
}

header('Content-Type: application/json');

if ($_SERVER["REQUEST_METHOD"] == "GET") {
    $result = getPopularList();

    echo json_encode($result);
} else {
    echo json_encode(array("error" => "Invalid Request"));
}

?>