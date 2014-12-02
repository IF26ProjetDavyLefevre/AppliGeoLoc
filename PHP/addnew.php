<?php

require 'BDD.php';



$db = new BDD();
$db->DB();

$login = "login";
$mdp="mdp";
$token="1";
$update="2014/12/02";
$coordonnees="12:23";

$db->AddNewUser($login,$mdp,$token,$update,$coordonnees);


?>