<?php

require 'BDD.php';

foreach ($_GET as $key => $value) {
    $parameters[":$key"] = $value;
}

$json = array(
    'error' => true
);

print_r($parameters);

$db = new BDD();
$db->DB();

$sql = "SELECT token FROM User WHERE login = '" . $parameters[':login'] . "' AND password = '" . $parameters[':password']."'";
echo $sql;
$req = $db->pdo->prepare($sql);
$result = $req->execute($whereArgs);

if ($result !== false) {

    $json = array(
        'error' => false,
        'token' => $token
    );
}


echo json_encode($json);


