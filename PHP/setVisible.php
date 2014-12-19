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


$visible = $parameters['visible'];
$login = $parameters['login'];

print_r($parameters);

http://pierredavy.com/setVisible.php?login=Thor&visible=1
    // marche seulement avec 0, 1 et false, pas true...


$db->setVisible($login, $visible);
