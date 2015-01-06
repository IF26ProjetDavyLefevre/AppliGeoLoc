<?php

require 'BDD.php';

$parameters = array
    (
    'login1' => null,
    'login2' => null
);

$db = new BDD();
$db->DB();

foreach ($_GET as $key => $value) {
    $parameters["$key"] = $value;
}


$login1 = $parameters['login1'];
$login2 = $parameters['login2'];

//print_r($parameters);



$sql = "SELECT * FROM `Relation` WHERE `login_user1`='" . $login1 . "' AND `login_user2`='" . $login2 . "' OR `login_user1`='" . $login2 . "' AND `login_user2`='" . $login1 . "';";

$req = $db->pdo->query($sql);
$result = $req->fetchall(PDO::FETCH_ASSOC);

print_r($result);


$errors = array_filter($result);
if (!empty($errors)) {
    echo'relation existe deja';
} else {
    //si une relation n'existe pas
    echo 'relation crée';
    $db->addNewRelation($login1, $login2);
}


//  http://pierredavy.com/addNewRelation.php?login1=davypier&login2=IronMan
?>