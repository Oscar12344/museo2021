<?php

$hostname="localhost";
$database="museo";
$username="root";
$password="";
$json=array();

if(isset($_GET["nombre"])&& isset($_GET["dia"])&& 
isset($_GET["hora"]))
{	
	$nombre=$_GET["nombre"];
    $dia=$_GET["dia"];
    $hora=$_GET["hora"];
	
    
    $conexion=mysqli_connect($hostname,$username,$password
,$database);
    $insert="insert into reserva(nombre,dia
    ,hora) values
    ('{$nombre}','{$dia}','{$hora}')";
    $resultado_insert=mysqli_query($conexion,$insert);
    if($resultado_insert)
    {
        $consulta="select*from reserva where nombre='{$nombre}'";
        $resultado=mysqli_query($conexion,$consulta);

        if($registro=mysqli_fetch_array($resultado))
        {
            $json['idreserva'][]=$registro;

        }
        mysqli_close($conexion);
        echo json_encode($json);
    }
    else{
		
        $resultado["nombre"]="no registra";
        $resultado["dia"]="no registra";
		$resultado["hora"]="no registra";
		
        $json['idreserva'][]=$resultado;
        echo json_encode($json);

    }
    }
    else
    {
		$resultado["nombre"]="no registra";
        $resultado["dia"]="no registra";
		$resultado["hora"]="no registra";
		
        $json['idreserva'][]=$resultado;
        echo json_encode($json);
    }
?>
 