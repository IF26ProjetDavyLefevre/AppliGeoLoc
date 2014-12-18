<?php

require 'BDD.php';

$parameters = array
    (
    'login'=>null,
    'visible' => null
        
);

$db = new BDD();
$db->DB();

foreach ($_GET as $key => $value) {
    $parameters["$key"] = $value;
}


$visible = $parameters['$visible'];
$login = $parameters['login'];


print_r($parameters);

$db->setVisible($login, $visible);
