<!doctype html>
<html>
<head>
<meta charset="utf-8">
<link rel="shortcut icon" href="sheep-icon.png">
<title>KUZU Search Engine</title>
<style type="text/css">
    body {

	color: #eee;
	text-align: center;
	background-color: #FFF;
		font-family: Arial, Helvetica, sans-serif;
    }
    
    a {
        color: #ccc;
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
			font-family: Arial, Helvetica, sans-serif;
	width: 450px;
	padding: 10px;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	border-radius: 10px;
	-moz-box-shadow: 0 1px 1px rgba(0,0,0,.4) inset, 0 1px 0 rgba(255,255,255,.2);
	-webkit-box-shadow: 0 1px 1px rgba(0,0,0,.4) inset, 0 1px 0 rgba(255,255,255,.2);
	box-shadow: 0 1px 1px rgba(0,0,0,.4) inset, 0 1px 0 rgba(255,255,255,.2);
	background-color: rgba(102,102,102,0.2);
	margin-top: 20px;
	margin-right: auto;
	margin-bottom: 20px;
	margin-left: auto;
    }
    
    .form-wrapper input {
		
	width: 330px;
	height: 20px;
	padding: 10px 5px;
	float: left;
	font-family: Arial, Helvetica, sans-serif;
	border: 0;
	background: #eee;
	-moz-border-radius: 3px 0 0 3px;
	-webkit-border-radius: 3px 0 0 3px;
	border-radius: 3px 0 0 3px;
	font-size: 16px;
	font-style: italic;	
    }
    
    .form-wrapper input:focus {
			font-family: Arial, Helvetica, sans-serif;
	outline: 0;
	background: #fff;
	-moz-box-shadow: 0 0 2px rgba(0,0,0,.8) inset;
	-webkit-box-shadow: 0 0 2px rgba(0,0,0,.8) inset;
	box-shadow: 0 0 2px rgba(0,0,0,.8) inset;
	font-family: Tahoma, Geneva, sans-serif;
	font-size: 16px;
	font-style: normal;
	
    }
    
    .form-wrapper input::-webkit-input-placeholder {
			font-family: Arial, Helvetica, sans-serif;
       color: #999;
       font-weight: normal;
       font-style: italic;
    }
    
    .form-wrapper input:-moz-placeholder {
			font-family: Arial, Helvetica, sans-serif;
        color: #999;
        font-weight: normal;
        font-style: normal;
    }
    
    .form-wrapper input:-ms-input-placeholder {
			font-family: Arial, Helvetica, sans-serif;
        color: #999;
        font-weight: normal;
        font-style: normal;
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
	font-family: Arial, Helvetica, sans-serif;
	text-align: right;
	color: #000;
}
    .form-wrapper.cf button {
	font-family: Arial, Helvetica, sans-serif;
	color: #000;
	font-size: 18px;
}
#container {
	width: 500px;
	margin-top: 130px;
	margin-right: auto;
	margin-left: auto;
}
#container #iki {
	float: left;
	width: 100px;
	text-align: center;
}
#container #bir {
	float: left;
	margin: auto;
	width: 100px;
	text-align: center;
}
#container #uc {
	float: left;
	width: 100px;
	text-align: center;
}
#container #dort {
	float: left;
	width: 100px;
	text-align: center;
}
#container #bes {
	float: left;
	width: 100px;
	text-align: center;
}
#container #alti {
	width: 100px;
	float: right;
}
#zero {
	padding-right: 100px;
	padding-left: 100px;
	padding-bottom: 40px;
}
#bes2 {
	float: left;
	width: 100px;
}
#bes3 {
	float: right;
	width: 500px;
}
</style>
</head>

<body>
<div id="container">
  <div id="zero"><img src="kuzu.jpg" width="300" height="195" alt="kuzu"></div>
  <div id="bir"> <a href="https://my.ku.edu.tr/"><img src="web.png" alt="myKU" name="myKU" width="51" height="51" id="myKU" title="myKU"></a> </div>
  <div id="iki"> <a href="https://glogin.ku.edu.tr/sso/?SAMLRequest=fVJNTxsxEL1X4j9Yvu9XVCiysotSEGokaFdk4cDN2OPEwevZeuyk%2FfdsNiDooVyf37yP8cwv%2FvSO7SCQRV%2FzKi85A69QW7%2Bu%2BX13nZ3zi%2Bbky5xk7waxSHHj7%2BB3AopsnPQkpoeap%2BAFSrIkvOyBRFRitbi9EbO8FEPAiAodZ8urmg9Kg0G5MQMqbc1aq61HD6Z3T9pqg%2BjtVhq15ezhLdbsEGtJlGDpKUofR6iszrLyNCtnXfVNnJ6L2ddHztpXp%2B%2FWHxt8FuvpSCLxo%2BvarP216iaBndUQfo7smq8R1w5yhf3BvpVEdjfCRjoCzhZEEOIY8BI9pR7CCsLOKri%2Fu6n5JsaBRFHs9%2Fv8XaaQxXPKQac8hkIq4s20WDF1Cx82%2Bnly%2BebMm3ftefFBqnn9sEOP5VWLzqq%2FbOEc7i8DyDiWiCGNHa4x9DL%2B363KqwmxOjMTVSRPAyhrLGjOiubo%2Bu9ljPfyAg%3D%3D&RelayState=https%3A%2F%2Fwww.google.com%2Fa%2Fku.edu.tr%2FServiceLogin%3Fservice%3Dmail%26passive%3Dtrue%26rm%3Dfalse%26continue%3Dhttps%253A%252F%252Fmail.google.com%252Fmail%252F%26ss%3D1%26ltmpl%3Ddefault%26ltmplcache%3D2%26emr%3D1%26osid%3D1"><img src="back.png" width="51" height="51" alt="back" title="KUmail"></a> </div>
  <div id="uc"> <a href="https://kusis.ku.edu.tr/psp/ps/?cmd=login"><img src="people.png" width="51" height="51" alt="people" title="KUSIS"></a> </div>
  <div id="dort"> <a href="http://registrar.ku.edu.tr/academic-calendar/?lang=tr"><img src="dates.png" width="51" height="51" alt="dates" title="Academic Calendar"></a> </div>
  <div id="bes"> <a href="http://ogs.ku.edu.tr/tr/facilities-management/shuttles"><img src="transport.png" width="51" height="51" alt="transport" title="Shuttle/Bus Hour"></a></div>
 
  <div id="bes3"><form action="./search.php" method="get" class="form-wrapper cf" id="keyword">
      <input type="text" name='keyword' autofocus="autofocus"placeholder="Mee here..." required>
      <button type="submit">KUZU</button>
    </form>
</div>
</div>

</body>

</html>
