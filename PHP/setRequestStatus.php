<?php

require 'BDD.php';

$parameters = array
    (
    'login1' => null,
    'login2' => null,
    'status' => null
);

$db = new BDD();
$db->DB();

foreach ($_GET as $key => $value) {
    $parameters["$key"] = $value;
}



$login1 = $parameters['login1'];
$login2 = $parameters['login2'];
$status = $parameters['status'];

print_r($parameters);

http://pierredavy.com/setRequestStatus?login1=Thor&login2=TheHulk&status=Accepted
$db->setRequestStatus($login1, $login2, $status);
