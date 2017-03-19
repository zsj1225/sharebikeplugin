<?php

class RSP{}
class DATA{}

function checkIllageStr($str){
	if(!strpos($str,"(") && !strpos($str," ") && !strpos($str,")") && !strpos($str,",")){  //û�зǷ��ַ�
		return true;
	}
	return false;
}

if(!isset($_GET["carno"]) && empty($_GET["carno"]) && !checkIllageStr($_GET["carno"])){
	$rsp = new RSP();
	$rsp->code=500;
	$rsp->desc="��������";
	echo json_encode($rsp);
	return;
}

$carno = $_GET["carno"];
$mydb = new mysqli('localhost', 'root', 'root', 'junbike');
$qsql="select carno,carpwd from junbike where carno='".$carno."' limit 1";
$result = $mydb->query($qsql);

$rsp = new RSP();
$rsp->code=200;
$rsp->desc= new DATA();
while ($row = mysqli_fetch_array($result)){
	$rsp->desc->$row[0] = $row[1];
}
echo json_encode($rsp);
$mydb->close();