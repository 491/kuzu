<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="sheep-icon.png">
<title>Kuzu Search Engine</title>
<style>
body {

color: #000;
background-color: #FFF;
        font-family: Arial, Helvetica, sans-serif;

}

a {
        color: #039;
        font-size: 13,5pt;
        font-family: Arial, Helvetica, sans-serif;
font-weight: bold;
        text-decoration: none;
        margin: 0px;
        padding: 0px;
        line-height: 5px;
}

br {
margin: 0px;
padding: 0px;
}

br2 {
color: #0F0;
margin: 0px;
padding: 0px;
}

/*-------------------------------------*/

.cf:before, .cf:after{
content:"";
display:table;
}

.cf:after{
        clear:both;
}

.cf{
        zoom:1;
}

/*-------------------------------------*/

.form-wrapper {
        width: 450px;
padding: 10px;
-moz-border-radius: 10px;
-webkit-border-radius: 10px;
border-radius: 10px;
-moz-box-shadow: 0 1px 1px rgba(0,0,0,.4) inset, 0 1px 0 rgba(255,255,255,.2);
-webkit-box-shadow: 0 1px 1px rgba(0,0,0,.4) inset, 0 1px 0 rgba(255,255,255,.2);
box-shadow: 0 1px 1px rgba(0,0,0,.4) inset, 0 1px 0 rgba(255,255,255,.2);
background-color: rgba(102,102,102,0.2);
margin: 0px;
}

.form-wrapper input {
        width: 330px;
height: 20px;
padding: 10px 5px;
float: left;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 16px;

border: 0;
background: #eee;
-moz-border-radius: 3px 0 0 3px;
-webkit-border-radius: 3px 0 0 3px;
border-radius: 3px 0 0 3px;
}

.form-wrapper input:focus {
        outline: 0;
background: #fff;
-moz-box-shadow: 0 0 2px rgba(0,0,0,.8) inset;
-webkit-box-shadow: 0 0 2px rgba(0,0,0,.8) inset;
box-shadow: 0 0 2px rgba(0,0,0,.8) inset;
        font-family: Arial, Helvetica, sans-serif;
        font-size: 16px;

}

.form-wrapper input::-webkit-input-placeholder {
        color: #999;
font-weight: normal;
font-style: italic;
}

.form-wrapper input:-moz-placeholder {
        color: #999;
font-weight: normal;
font-style: italic;
}

.form-wrapper input:-ms-input-placeholder {
        color: #999;
font-weight: normal;
font-style: italic;
}

.form-wrapper button {
        overflow: visible;
position: relative;
float: right;
border: 0;
padding: 0;
cursor: pointer;
height: 40px;
width: 110px;
        font-family: Arial, Helvetica, sans-serif;
font: bold 15px/40px;
color: #fff;
text-transform: uppercase;
background: #d83c3c;
-moz-border-radius: 0 3px 3px 0;
-webkit-border-radius: 0 3px 3px 0;
border-radius: 0 3px 3px 0;
text-shadow: 0 -1px 0 rgba(0, 0 ,0, .3);
}

.form-wrapper button:hover{
        background: #e54040;
}

.form-wrapper button:active,
.form-wrapper button:focus{
        background: #c42f2f;
}

.form-wrapper button:before {
        content: '';
position: absolute;
border-width: 8px 8px 8px 0;
border-style: solid solid solid none;
border-color: transparent #d83c3c transparent;
top: 12px;
left: -6px;
}

.form-wrapper button:hover:before{
        border-right-color: #e54040;
}

.form-wrapper button:focus:before{
        border-right-color: #c42f2f;
}

.form-wrapper button::-moz-focus-inner {
        border: 0;
padding: 0;
}
body,td,th {

color: #000;
font-size: 16px;
}
.form-wrapper.cf button {
                font-family: Arial, Helvetica, sans-serif;
        color: #000;
        font-size: 18px;
}


b {
                font-family: Arial, Helvetica, sans-serif;
        font-size: 11pt;
        color: #090;
        margin: 0px;
        padding: 0px;
}
b2 {
        font-family: Arial, Helvetica, sans-serif;
        font-size: 11pt;
        margin: 0px;
        padding: 0px;
}

</style>
<meta charset="utf-8">
</head>

<body>
<table border="0">
  <tr>
     <td><img src="kuzu.jpg" width="150" height="95" alt="kuzuk"></td>
     <td><img src="yaz42.png" width="320" height="32" alt="yaz">
<form action="./search.php" method="get" class="form-wrapper cf" id="keyword">
 <input type='text'  name='keyword' value= '<?php echo $_GET ['keyword']; ?>'  />
<button type="submit">KUZU</button>
</form></td>
  </tr>
</table>


<hr />


<p>

<?php

$keyword = $_GET['keyword'];
$terms = explode(" " , $keyword);

$query = "SELECT count(*) FROM search2 WHERE ";
	$i = 0;
	foreach ($terms as $each) {
		$i++;
		if ($i == 1)
			$query .= "keywords LIKE '%$each%' ";
		else
			$query .= "AND keywords LIKE '%$each%' ";
	}
	
	$li = mysqli_connect("localhost","","","");
	mysqli_set_charset($li, "utf8");	
$query = mysqli_query($li, $query);
$row = mysqli_fetch_row($query);
// Here we have the total row count
$numrows = $row[0];


	$page_rows = 10;
	$last = ceil($numrows/$page_rows);
	if($last<1)
		$last=1;
	$pagenum = 1;
	
	if(isset($_GET['pn'])){
		$pagenum = preg_replace('#[^0-9]#', '', $_GET['pn']);
	}
	
	if ($pagenum < 1) { 
    	$pagenum = 1; 
	} else if ($pagenum > $last) { 
    	$pagenum = $last; 
	}
	
	$limit = 'LIMIT ' .($pagenum - 1) * $page_rows .',' .$page_rows;
	
	
	$query = "SELECT * FROM search2 WHERE ";


	$i = 0;
	foreach ($terms as $each) {
		$i++;
		if ($i == 1)
			$query .= "keywords LIKE '%$each%' ";
		else
			$query .= "AND keywords LIKE '%$each%' ";
	}

	$query .= "ORDER BY Popularity DESC $limit";

	$query = mysqli_query($li, $query);
	
	$textline1 = "Total Page(<b>$numrows</b>)";
	$textline2 = "Page <b>$pagenum</b> of <b>$last</b>";
	
	$paginationCtrls = '';
// If there is more than 1 page worth of results
if($last != 1){
	/* First we check if we are on page one. If we are then we don't need a link to 
	   the previous page or the first page so we do nothing. If we aren't then we
	   generate links to the first page, and to the previous page. */
	if ($pagenum > 1) {
        $previous = $pagenum - 1;
		$paginationCtrls .= '<a href="'.$_SERVER['PHP_SELF'].'?keyword='.$keyword.'&pn='.$previous.'">Previous</a> &nbsp; &nbsp; ';
		// Render clickable number links that should appear on the left of the target page number
		for($i = $pagenum-4; $i < $pagenum; $i++){
			if($i > 0){
		        $paginationCtrls .= '<a href="'.$_SERVER['PHP_SELF'].'?keyword='.$keyword.'&pn='.$i.'">'.$i.'</a> &nbsp; ';
			}
	    }
    }
	// Render the target page number, but without it being a link
	$paginationCtrls .= ''.$pagenum.' &nbsp; ';
	// Render clickable number links that should appear on the right of the target page number
	for($i = $pagenum+1; $i <= $last; $i++){
		$paginationCtrls .= '<a href="'.$_SERVER['PHP_SELF'].'?keyword='.$keyword.'&pn='.$i.'">'.$i.'</a> &nbsp; ';
		if($i >= $pagenum+4){
			break;
		}
	}
	// This does the same as above, only checking if we are on the last page, and then generating the "Next"
    if ($pagenum != $last) {
        $next = $pagenum + 1;
        $paginationCtrls .= ' &nbsp; &nbsp; <a href="'.$_SERVER['PHP_SELF'].'?keyword='.$keyword.'&pn='.$next.'">Next</a> ';
    }
}
	
$list = '';
while($row = mysqli_fetch_array($query, MYSQLI_ASSOC)){
	$id = $row['id'];
			$title = $row['title'];
			$description = $row['description'];
			$keywords = $row['keywords'];
			$link = $row['link'];
			$list .= "<p><a href='$link'>$title </a>
	<br><b>$link </b>
	 <br><b2>$description</b2> </p>";
	 
	 
	//  $list .= '<p><a href="search.php?id='.$id.'">'.$title.' 
// 	 Testimonial</a> - Click the link to view this testimonial<br>Written </p>';
}



mysqli_close($li);

?>
</p>





<table border="0">
  <tr>
    <td width="150">&nbsp;</td>
    <td width="574">

    <div>

    <p><?php echo $textline2; ?></p>
  <p><?php echo $list; ?></p>
  <div ><?php echo $paginationCtrls; ?>
  <p><?php echo $textline1; ?> </p>


</div>
</td>
  </tr>
</table>
</body>
</html>
