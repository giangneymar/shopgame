<?php
    include 'connect.php';
    $page = $_GET['page'];
    $idGC = $_POST['idtheloaigame'];
    $space = 3;
    $limit = ($page - 1) * $space;

    $arrayGame = array();
    $query = "SELECT * FROM game WHERE idtheloaigame = $idGC LIMIT $limit,$space";
    $data = mysqli_query($conn, $query);

    while($row = mysqli_fetch_assoc($data)){
        array_push($arrayGame, new Game(
            $row['id'],
            $row['tengame'],
            $row['giagame'],
            $row['hinhanhgame'],
            $row['motagame'],
            $row['idtheloaigame'],
        ));
    }

    echo json_encode($arrayGame);

    class Game{
        function __construct($id, $game, $priceGame, $imageGame, $descriptionGame, $idGameCategory){
            $this -> ID = $id;
            $this-> Game = $game;
            $this -> PriceGame = $priceGame;
            $this -> ImageGame = $imageGame;
            $this -> DescriptionGame = $descriptionGame;
            $this -> IDGameCategory = $idGameCategory;
        }
    }
?>