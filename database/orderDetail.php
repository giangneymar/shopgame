<?php
    include 'connect.php';
    $json = $_POST['json'];
    $data = json_decode($json, true);
    foreach($data as $value){
        $iddonhang = $value['iddonhang'];
        $idgame = $value['idgame'];
        $tengame = $value['tengame'];
        $giagame = $value['giagame'];
        $soluonggame = $value['soluonggame'];
        
        $query = "INSERT INTO chitietdonhang (id,iddonhang,idgame,tengame,giagame,soluonggame)
                VALUES (null,'$iddonhang','$idgame','$tengame','$giagame','$soluonggame')";
        $data2 = mysqli_query($conn,$query);
    }
    if($data2){
        echo "1";
    }
    else{
        echo "0";
    }
?>