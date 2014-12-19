<?php

require 'BDD.php';

$parameters = array
    (
    'login_user1' => null,
    'login_user2' => null
);

$db = new BDD();
$db->DB();

foreach ($_GET as $key => $value) {
    $parameters["$key"] = $value;
}


$login1 = $parameters['login_user1'];
$login2 = $parameters['login_user2'];

print_r($parameters);

$db->addNewRelation($login1,$login2);

http://pierredavy.com/addNewRelation.php?login_user1=Thor&login_user2=IronMan
?>