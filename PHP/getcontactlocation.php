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

//$sql = "SELECT login,latitude, longitude FROM User, Relation WHERE login = '" . $parameters[':login'] . "' AND password = '" . $parameters[':password'] . "'";
//print_r($sql);

$sql = "SELECT * 
        FROM((SELECT login, latitude, longitude 
	FROM User 
	WHERE login = '". $parameters[':login'] ."' AND token ='". $parameters[':token'] ."')
	UNION
	(SELECT login, latitude, longitude
	FROM User 
	LEFT JOIN(SELECT CASE WHEN login_user1 = '". $parameters[':login'] ."' THEN login_user2 ELSE login_user1 END AS login
			FROM Relation 
			WHERE login_user1 = '". $parameters[':login'] ."' OR login_user2 = '". $parameters[':login'] ."') AS tblLoginContact USING (login)
    WHERE visible = true)) as tbltotal";


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
