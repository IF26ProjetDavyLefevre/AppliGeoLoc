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


$sql="SELECT DISTINCT login_user_request FROM Request WHERE login_user_request_receiver = '". $parameters[':login'] ."' AND status='". $parameters[':status'] ."'";

$req = $db->pdo->query($sql);
$result = $req->fetchall(PDO::FETCH_ASSOC);

//print_r($result);

if ($result !== false) {

    $json = array(
        'error' => false,
        'users' => $result
    );
}


echo json_encode($json);

// http://pierredavy.com/getRequestPending?login=davypier&status=Pending
