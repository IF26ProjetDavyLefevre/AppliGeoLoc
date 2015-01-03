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
    'precise' => null,
	'salt' => null
);

$db = new BDD();
$db->DB();

foreach ($_GET as $key => $value) {
    $parameters["$key"] = $value;
}


//http://pierredavy.com/addnew.php?login=123&password=456&token=1&update=2014/12/02&latitude=12&longitude=34&altitude=56&precise=78



$login = $parameters['login'];
$mdp = $parameters['password'];
$token = hash('sha256', time().$login.$password);
$update = $parameters['update'];
$latitude = $parameters['latitude'];
$longitude = $parameters['longitude'];
$altitude = $parameters['altitude'];
$precise = $parameters['precise'];
$salt = $parameters['salt'];


//si le contact n'existe pas on créer un nouveau contact sinon on renvoie faux
$sql = "SELECT  login FROM User WHERE login = '" . $login. "'";

$req = $db->pdo->query($sql);
$result = $req->fetchall(PDO::FETCH_ASSOC);

$errors = array_filter($result);
if (!empty($errors)) {

    echo'false';
    
    
}
else{
    print_r($parameters);
    $db->AddNewUser($login, $mdp, $token, $update, $latitude, $longitude, $altitude, $precise, $salt);
}




?>