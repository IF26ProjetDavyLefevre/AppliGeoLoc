<?php

$BDD = new BDD();
$BDD->DB();

//$BDD->AddNewUser('login', 'mdp', '1222', '2014-12-02', 12, 23, 34, 45);
//$BDD->updatelatlng('davypier', "12", "12");

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

    //ajoute un nouvel utilisateur dans la base de données
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

    //permet de changer les coordonnées d'un utilisateur dans la base de données
    public function updatelatlng($login, $latitude, $longitude) {
        $req = $this->pdo->prepare('UPDATE User SET latitude= :latitude , longitude = :longitude, last_update = DATE(NOW()) WHERE login = :login');
        $result = $req->execute(array(
            ':login' => $login,
            ':latitude' => $latitude,
            ':longitude' => $longitude
        ));

        if (!$result) {
            return false;
        }
    }

    //Permet de changer la variable visible a true or false
    public function setVisible($login, $visible) {
        $req = $this->pdo->prepare('UPDATE User Set visible = :visible WHERE login = :login');
        $result = $req->execute(array(
            ':visible' => $visible,
            ':login' => $login
        ));
        print_r($req);

        if (!$result) {
            return false;
        }
    }

    //Ajoute une nouvelle relation entre 2 users
    public function addNewRelation($login1, $login2) {
        $req = $this->pdo->prepare('INSERT INTO Relation(login_user1,login_user2) VALUES(:login_user1, :login_user2 )');
        $result = $req->execute(array(
            ':login_user1' => $login1,
            ':login_user2' => $login2
        ));
        if (!$result) {
            return false;
        }
    }

    //ajoute une requete entre 2 users
    public function addRequest($login, $login2) {
        $req = $this->pdo->prepare('INSERT INTO Request(login_user_request,login_user_request_receiver, date, status) VALUES(:login_user_request, :login_user_request_receiver, DATE(NOW()), :status )');
        $result = $req->execute(array(
            ':login_user_request' => $login,
            ':login_user_request_receiver' => $login2,
            ':status' => 'Pending'
        ));
        if (!$result) {
            return false;
        }
    }

    //Permet de changer la le status de la requete
    public function setRequestStatus($login, $login2, $status) {
        $req = $this->pdo->prepare('UPDATE Request SET  status = :status WHERE login_user_request = :login AND login_user_request_receiver = :login2');
        $result = $req->execute(array(
            ':login' => $login,
            ':login2' => $login2,
            ':status' => $status
        ));
        
        // Si la requete est acceptée, ajouter une nouvelle relation
        if ($status =='Accepted'){
            $this->addNewRelation($login, $login2);
        }
        else{
            $this
        }
        
        if (!$result) {
            return false;
        }
    }

}

?>