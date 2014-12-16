<?php

$BDD = new BDD();
$BDD->DB();
//$BDD->AddNewUser('login', 'mdp', '1222', '2014-12-02', 12, 23, 34, 45);

class BDD {

    public $pdo;
    private $host_name = "db554207254.db.1and1.com";
    private $database = "db554207254";
    private $dsn = 'mysql:dbname=db554207254;host=db554207254.db.1and1.com';
    private $user_name = "dbo554207254";
    private $pwd = "if26ccool";

    public function DB() {
        try {
            $this->pdo = new PDO('mysql:host=' . $this->host_name . ';dbname=' . $this->database, $this->user_name, $this->pwd);
        } catch (PDOException $e) {
            echo 'Connexion échouée : ' . $e->getMessage();
        }
    }

//pour l'instant permet de renvoyer les différents utilisateurs de la BDD
    public function find() {
        $resultats = $this->pdo->query("SELECT login FROM User WHERE 1");
        $resultats->setFetchMode(PDO::FETCH_OBJ);
        while ($resultat = $resultats->fetch()) {
            echo 'Utilisateur : ' . $resultat->login . '<br>';
        }
        $resultats->closeCursor();
    }

    public function AddNewUser($login, $mdp, $token, $update, $latitude, $longitude, $altitude, $precise) {
        $req = $this->pdo->prepare('INSERT INTO User(login,password, token, last_update,latitude,longitude,altitude,precise,visible) VALUES(:login, :password, :token, :last_update, :latitude, :longitude, :altitude, :precise, :visible)');
        $result = $req->execute(array(
            'login' => $login,
            'password' => $mdp,
            'token' => $token,
            'last_update' => $update,
            ':latitude' => $latitude,
            ':longitude' => $longitude,
            ':altitude' => $altitude,
            ':precise' => $precise,
            ':visible' => 1
        ));
        if (!$result) {
            return false;
        }
        
    }

    public function search($class, $table, $where, $whereArgs = array()) {
        
    }

    public function update($model, $table, $where, $whereArgs = array()) {
        
    }

    public function delete($table, $where, $whereArgs = array()) {
        
    }

}
?>