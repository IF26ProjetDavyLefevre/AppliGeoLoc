<?php

require 'BDD.php';

foreach ($_GET as $key => $value) {
    $parameters[":$key"] = $value;
}

$json = array(
    'error' => true
);

$db = new BDD();
$db->DB();



$sql = "SELECT visible FROM User WHERE login ='" . $parameters[':login'] . "';";
$req = $db->pdo->query($sql);
$result = $req->fetch(PDO::FETCH_ASSOC);

//print_r($result);
if ($result !== false) {

    $json = array(
        'error' => false,
        'visible' => $result
    );
}


echo json_encode($json);
