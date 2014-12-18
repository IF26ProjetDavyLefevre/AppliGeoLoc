<?php

require 'BDD.php';

$parameters = array
    (
    'login' => null,
    'login2' => null,
    'status' => null
        
);

//3 possiblités de status pour la requete : attente, acceptee,refusee

$db = new BDD();
$db->DB();

foreach ($_GET as $key => $value) {
    $parameters["$key"] = $value;
}


//http://pierredavy.com/addnew.php?login=login&password=mdp&token=1&update=2014/12/02&latitude=12&longitude=34&altitude=56&precision=78
//http://pierredavy.com/addnew.php?login=123&password=456&token=1&update=2014/12/02&latitude=12&longitude=34&altitude=56&precise=78



$login = $parameters['login2'];
$login2 = $parameters['login2'];
$status = $parameters['status'];
print_r($parameters);

//$db->AddNewUser('$login', '$mdp', '$token', '$update', '$latitude','$longitude','$altitude','$precision');
$db->addRequest($login, $login2,$status);

?>