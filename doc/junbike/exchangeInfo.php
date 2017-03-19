<?php
class RSP{}
class DATA{}


$mydb = new mysqli('localhost', 'root', 'root', 'junbike');
$qsql="select carno,carpwd from junbike order by carno asc";
$result = $mydb->query($qsql);

$rsp = new RSP();
$rsp->code=200;
$rsp->desc= new DATA();
while ($row = mysqli_fetch_array($result)){
	//echo $row[0]."==".$row[1];
	$rsp->desc->$row[0] = $row[1];
}

echo json_encode($rsp);

$mydb->close();
