<?php

    include "connect.php";
    $query = "SELECT * FROM theloaigame";
    $data = mysqli_query($conn, $query);
    $arrayGameCategory = array();

    while($row = mysqli_fetch_assoc($data)){
        array_push($arrayGameCategory, new GameCategory(
            $row['id'],
            $row['tentheloaigame'],
            $row['hinhanhtheloaigame']
        ));
    }

    echo json_encode($arrayGameCategory);

    class GameCategory{
        function __construct($id, $gameCategoryName, $gameCategoryImage){
            $this -> ID = $id;
            $this -> GameCategoryName = $gameCategoryName;
            $this -> GameCategoryImage = $gameCategoryImage;
        }
    }
?>