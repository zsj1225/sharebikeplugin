<?php
class RSP{}

if(!isset($_POST["info"]) && empty($_POST["info"])){
	$rsp = new RSP();
	$rsp->code=500;
	$rsp->desc="参数缺失1";
	echo json_encode($rsp);
	return;
}

$postParam=$_POST["info"];
$arrRequest = json_decode($postParam,true);
$requestData = $arrRequest["data"];
if(empty($requestData)){
	$rsp = new RSP();
	$rsp->code=500;
	$rsp->desc="参数缺失2";
	echo json_encode($rsp);
	return;
}


$mydb = new mysqli('localhost', 'root', 'root', 'junbike');

function checkIllageStr($str){
	if(!strpos($str,"(") && !strpos($str," ") && !strpos($str,")") && !strpos($str,",")){  //没有非法字符
		return true;
	}
	return false;
}

foreach($requestData as $carno=>$carpwd){
	$qsql="select Id,carno,carpwd from junbike where carno='".$carno."' limit 1";
	$result = $mydb->query($qsql);
	if($result && $result->num_rows > 0){
		//echo "已经有该号码了";
	}else{
		if(checkIllageStr($carno) && checkIllageStr($carpwd)){
			$isql = "insert into junbike(carno,carpwd) values ('".$carno."','".$carpwd."')";
			//echo $isql;
			$mydb->query($isql);
		}else{
			//echo "参数含有非法字符";
		}
		
	}
}

$rsp = new RSP();
$rsp->code=200;
$rsp->desc="更新成功";
echo json_encode($rsp);

$mydb->close();
