<?php

require 'BDD.php';

$parameters = array
    (
    'login' => null,
    'password' => null,
    'token'=>null,
    'update'=>null,
    'coordonnees'=>null
);

$db = new BDD();
$db->DB();

foreach ($_GET as $key => $value) {
    $parameters["$key"] = $value;
}


//http://pierredavy.com/addnew.php?login=login&password=mdp&token=1&update=2014/12/02&coordonnees=12:23

$login = $parameters['login'];
$mdp = $parameters['password'];
$token = $parameters['token'];
$update = $parameters['update'];
$coordonnees = $parameters['coordonnees'];
print_r($parameters);




//latitude , longitude, altitude, précision



/*
$login = "login";
$mdp = "mdp";
$token = "1";
$update = "2014/12/02";
$coordonnees = "12:23";*/

$db->AddNewUser($login, $mdp, $token, $update, $coordonnees);
?>