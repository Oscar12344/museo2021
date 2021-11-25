<?php

$hostname="localhost";
$database="museo";
$username="root";
$password="";
$json=array();

if( isset($_GET["nombre"])&& isset($_GET["correo"])){
    
    $nombre=$_GET["nombre"];
    $correo=$_GET["correo"];
    
    $conexion=mysqli_connect($hostname,$username,$password,$database);
    $insert="insert into usuarios(nombre,correo) values('{$nombre}','{$correo}')";
	
    $resultado_insert=mysqli_query($conexion,$insert);
    if($resultado_insert)
    {
        $consulta="select*from usuarios where nombre='{$nombre}'";
        $resultado=mysqli_query($conexion,$consulta);

        if($registro=mysqli_fetch_array($resultado))
        {
            $json['users'][]=$registro;

        }
        mysqli_close($conexion);
        echo json_encode($json);
    }
    else{
        
        $resultado["nombre"]="no registra";
        $json['users'][]=$resultado;
        echo json_encode($json);

    }
    }
    else
    {
        
        $resultado["nombre"]="WS no devuelve";
        $json['users'][]=$resultado;
        echo json_encode($json);
    }
?>