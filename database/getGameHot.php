<?php
    include "connect.php";
    $query = "SELECT * FROM game ORDER BY id DESC LIMIT 6";
    $data = mysqli_query($conn, $query);
    $arrayGameHot = array();

    while($row = mysqli_fetch_assoc($data)){
        array_push($arrayGameHot, new GameHot(
            $row['id'],
            $row['tengame'],
            $row['giagame'],
            $row['hinhanhgame'],
            $row['motagame'],
            $row['idtheloaigame'],
        ));
    }

    echo json_encode($arrayGameHot);

    class GameHot{
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