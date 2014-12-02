<?php

require 'BDD.php';


$db = new BDD();

$login = "login";
$mdp="mdp";
$token="1";
$update="2014/12/02";
$coordonnees="12:23";

$newuser =$db->insert("User",$table,$login,$mdp,$token,$update,$coordonnees);


?>