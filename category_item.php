<?php
include 'config.php';

function getCategoryList()
{
    global $conn;

    $query = "SELECT * FROM category_item";
    $result = $conn->query($query);

    if ($result->num_rows > 0) {
        $response = array();
        while ($row = $result->fetch_assoc()) {
            $response[] = $row;
        }
        return $response;
    } else {
        return array("status" => "error", "message" => "No categories found");
    }
}

header('Content-Type: application/json');

if ($_SERVER["REQUEST_METHOD"] == "GET") {
    $result = getCategoryList();

    echo json_encode($result);
} else {
    echo json_encode(array("error" => "Invalid Request"));
}

?>