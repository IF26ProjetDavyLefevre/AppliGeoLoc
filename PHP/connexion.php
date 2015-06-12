<?php
	$host_name = "";
    $database = "";
    $dsn = ;
    $user_name = "";
    $pwd = "";
	try {
		$bdd = new PDO($dsn, $user_name, $pwd);
	} catch (PDOException $e) {
		echo 'Connexion échouée : ' . $e->getMessage();
	}
?>