	<?PHP
$hostname_localhost ="localhost";
$database_localhost ="museo";
$username_localhost ="root";
$password_localhost ="";

$json=array();

	if(isset($_GET["id"])){
		$id=$_GET["id"];
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

		$consulta="select * from opciones where id= '{$id}'";
		$resultado=mysqli_query($conexion,$consulta);
			
		if($registro=mysqli_fetch_array($resultado)){
			$result["id"]=$registro['id'];
			$result["op1Dia"]=$registro['op1Dia'];
			$result["op2Dia"]=$registro['op2Dia'];
			$result["op3Dia"]=$registro['op3Dia'];
			$result["op4Dia"]=$registro['op4Dia'];
			$result["op5Dia"]=$registro['op5Dia'];
			$result["op6Dia"]=$registro['op6Dia'];
			$result["op1Hora"]=$registro['op1Hora'];
			$result["op2Hora"]=$registro['op2Hora'];
			
			$json['idopciones'][]=$result;
		}else{
			$resultar["id"]=0;
			$resultar["op1Dia"]='no registra';
			$resultar["op2Dia"]='no registra';
			$resultar["op3Dia"]='no registra';
			$resultar["op4Dia"]='no registra';
			$resultar["op5Dia"]='no registra';
			$resultar["op6Dia"]='no registra';
			$resultar["op1Hora"]='no registra';
			$resultar["op2Hora"]='no registra';
		
			$json['idopciones'][]=$resultar;
		}
		
		mysqli_close($conexion);
		echo json_encode($json);
	}
	else{
		$resultar["success"]=0;
		$resultar["message"]='Ws no Retorna';
		$json['idopciones'][]=$resultar;
		echo json_encode($json);
	}
?>