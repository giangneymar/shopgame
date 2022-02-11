<?php
    include 'connect.php';

    $cusName = $_POST['tenkhachhang'];
    $cusPhone = $_POST['sdt'];
    $cusEmail = $_POST['email'];

    if(strlen($cusName) > 0 && strlen($cusEmail) > 0 && strlen($cusPhone) > 0){
        $query = "INSERT INTO donhang(id,tenkhachhang,sdt,email) VALUES (null,'$cusName','$cusPhone','$cusEmail')";
        if(mysqli_query($conn, $query)){
            $idOrder = $conn -> insert_id;
            echo $idOrder;
        }
        else{
            echo "Thất bại";
        }
    }
    else{
        echo "Bạn hãy kiểm tra lại dữ liệu";
    }
?>