<?php

require 'BDD.php';

$parameters = array
    (
    'login' => null,
    'password' => null,
    'token' => null,
    'update' => null,
    'latitude' => null,
    'longitude' => null,
    'altitude' => null,
    'precise' => null
        
);

$db = new BDD();
$db->DB();

foreach ($_GET as $key => $value) {
    $parameters["$key"] = $value;
}


//http://pierredavy.com/addnew.php?login=login&password=mdp&token=1&update=2014/12/02&latitude=12&longitude=34&altitude=56&precision=78
//http://pierredavy.com/addnew.php?login=123&password=456&token=1&update=2014/12/02&latitude=12&longitude=34&altitude=56&precise=78



$login = $parameters['login'];
$mdp = $parameters['password'];
$token = $parameters['token'];
$update = $parameters['update'];
$latitude = $parameters['latitude'];
$longitude = $parameters['longitude'];
$altitude = $parameters['altitude'];
$precise = $parameters['precise'];
print_r($parameters);

//$db->AddNewUser('$login', '$mdp', '$token', '$update', '$latitude','$longitude','$altitude','$precision');
$db->AddNewUser($login, $mdp, $token, $update, $latitude, $longitude, $altitude, $precise);

?>