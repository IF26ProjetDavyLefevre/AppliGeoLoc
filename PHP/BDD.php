<?php

//$BDD = new BDD();
//$BDD->DB();
//$BDD->find();

class BDD {

    private $pdo;
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

    public function search($class, $table, $where, $whereArgs = array()) {
        
    }
 
    public function insert($login, $mdp, $token, $update, $coordonnees) {
        $fields = '';
        $values = '';
        $whereArgs = array();
        foreach ($model->toDB() as $name => $value) {
            if ($fields != '') {
                $fields .= ', ';
                $values .= ', ';
            }
            $fields .= $name;
            $values .= ":$name";
            $whereArgs[":$name"] = $value;
        }
        $sql = "INSERT INTO $table ($fields) VALUES ($values)";
        $pdoStatement = $this->pdo->prepare($sql);
        $result = $pdoStatement->execute($whereArgs);
        if (!$result) {
            return false;
        }
        return $this->pdo->lastInsertId();
    }

    public function update($model, $table, $where, $whereArgs = array()) {
        
    }

    public function delete($table, $where, $whereArgs = array()) {
        
    }

}

?>