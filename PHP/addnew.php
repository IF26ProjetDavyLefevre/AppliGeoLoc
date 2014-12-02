<?php

require 'BDD.php';

$parameters = array
    (
    'login' => null,
    'password' => null,
    'token'=>null,
    'update'=>null,
    'latitude'=>null
    
    //ajouer les nouvelles valeurs
);

$db = new BDD();
$db->DB();

foreach ($_GET as $key => $value) {
    $parameters["$key"] = $value;
}


//http://pierredavy.com/addnew.php?login=login&password=mdp&token=1&update=2014/12/02&latitude=12&longitude=34&altitude=56&precision=78

$login = $parameters['login'];
$mdp = $parameters['password'];
$token = $parameters['token'];
$update = $parameters['update'];
$latitude = $parameters['latitude'];
$longitude = $parameters['longitude'];
$altitude = $parameters['altitude'];
$precision = $parameters['precision'];
print_r($parameters);

//$db->AddNewUser('$login', '$mdp', '$token', '$update', '$latitude','$longitude','$altitude','$precision');
$db->AddNewUser($login, $mdp, $token, $update, $latitude,$longitude,$altitude,$precision);
?>