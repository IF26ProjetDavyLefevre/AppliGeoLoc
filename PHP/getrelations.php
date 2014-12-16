<?php

require 'BDD.php';

foreach ($_GET as $key => $value) {
    $parameters[":$key"] = $value;
}

$json = array(
    'error' => true
);


$db = new BDD();
$db->DB();


$sql = "SELECT * 
        FROM((SELECT login, latitude, longitude
	FROM (SELECT CASE WHEN login_user1 = '". $parameters[':login'] ."' THEN login_user2 ELSE login_user1 END AS login
			FROM Relation 
			WHERE login_user1 = '". $parameters[':login'] ."' OR login_user2 = '". $parameters[':login'] ."') AS tblLoginContact 
	LEFT JOIN User USING (login)
        )) as tbltotal";


$req = $db->pdo->query($sql);
$result = $req->fetchall(PDO::FETCH_ASSOC);

//print_r($result);
if ($result !== false) {

    $json = array(
        'error' => false,
        'user' => $result
    );
}


echo json_encode($json);
