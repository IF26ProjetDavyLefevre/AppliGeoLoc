<?php
	include_once 'connexion.php';
	/* récupération des données en GET
	$login = $_GET['login'];
	$mdp = $_GET['mdp'];
	$token = $_GET['token']; 
	$update = $_GET['date']; //il nous faudrait plutôt un datetime ou timestamps histoire de vérifier si la dernière update date de moins de Xmn
	$latitude = $_GET['latitute']; 
	$longitude = $_GET['longitude']; 
	$altitude = $_GET['altitude']; 
	$precision = $_GET['precision'];
	*/
	$login = 'login';
	$mdp = 'mdp';
	$token = '1222'; 
	$update = '2014-12-02';
	$latitude = 12; 
	$longitude = 23; 
	$altitude = 34; 
	$precision = 45;
	
	$sql = 'INSERT INTO User (login, password, token, last_update, latitude, longitude, altitude, precision) VALUES(:login, :password, :token, :last_update, :latitude, :longitude, :altitude, :precision)';
	$req = $bdd->prepare($sql);
	try{
		$result = $req->execute(array(
			':login' => $login,
			':password' => $mdp,
			':token' => $token,
			':last_update' => $update,
			':latitude' => $latitude,
			':longitude' => $longitude,
			':altitude' => $altitude,
			':precision' => $precision
		));
	} catch (PDOException $e) {
		echo 'Connexion échouée : ' . $e->getMessage();
	}
?>