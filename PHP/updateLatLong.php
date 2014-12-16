<?php

require 'BDD.php';

$parameters = array
    (
    'login' => null,
    'latitude' => null,
    'longitude' => null,
        
);

$db = new BDD();
$db->DB();

foreach ($_GET as $key => $value) {
    $parameters["$key"] = $value;
}


$login = $parameters['login'];
$latitude = $parameters['latitude'];
$longitude = $parameters['longitude'];

print_r($parameters);

//$db->AddNewUser('$login', '$mdp', '$token', '$update', '$latitude','$longitude','$altitude','$precision');
$db->updatelatlng($login, $latitude, $longitude);


