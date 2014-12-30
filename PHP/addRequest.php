

<?php

require 'BDD.php';

$parameters = array
    (
    'login1' => null,
    'login2' => null
        
);

//3 possiblités de status pour la requete : pending, accepted, refused

$db = new BDD();
$db->DB();

foreach ($_GET as $key => $value) {
    $parameters["$key"] = $value;
}


$login = $parameters['login1'];
$login2 = $parameters['login2'];
//print_r($parameters);


//on vérifie que l'utilisateur existe bien
$sql = "SELECT * FROM User WHERE login ='". $parameters['login2']."'";
$req = $db->pdo->query($sql);
$result = $req->fetch(PDO::FETCH_ASSOC);

if ($result !== false) {

$db->addRequest($login, $login2);
}
 else {
// si le login n'existe pas    
     echo 'false';
}


?>