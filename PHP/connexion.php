<?php
	$host_name = "db554207254.db.1and1.com";
    $database = "db554207254";
    $dsn = 'mysql:dbname='.$database.';host='.$host_name;
    $user_name = "dbo554207254";
    $pwd = "if26ccool";
	try {
		$bdd = new PDO($dsn, $user_name, $pwd);
	} catch (PDOException $e) {
		echo 'Connexion échouée : ' . $e->getMessage();
	}
?>