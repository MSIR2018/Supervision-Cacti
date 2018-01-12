<?php 
session_start();
if(isset($_POST['login'])){
	if(($_POST['login_username'] == "admin") && ($_POST['login_password'] == "cpir")){ $_SESSION['connected']="yes"; $_SESSION['user']=$_POST['login_username']; header('Location: ./index.php'); }
	if(($_POST['login_username'] == "guest") && ($_POST['login_password'] == "guest")){ $_SESSION['connected']="yes"; $_SESSION['user']=$_POST['login_username']; header('Location: ./index.php'); }
}

?>
<html>
<head>
	<title>Login to Cacti</title>
	<meta http-equiv='Content-Type' content='text/html;charset=utf-8'>
	<meta http-equiv='X-UA-Compatible' content='IE=Edge,chrome=1'>
	<meta content='width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0' name='viewport'>
	<meta name='apple-mobile-web-app-capable' content='yes'>
	<meta name='mobile-web-app-capable' content='yes'>
	<meta name='robots' content='noindex,nofollow'>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="<?php echo $config['url_path']; ?>src/bootstrap.min.css" >
	<link rel="stylesheet" href="<?php echo $config['url_path']; ?>src/icofont/css/icofont.css">
	<link rel="stylesheet" href="<?php echo $config['url_path']; ?>src/desert_landscape/style.css">
	<link rel="icon" href="src/cacti_logo.gif" />
	<style>
	#font-image {
	position:fixed;
	right: 0px;
	bottom: 0px;
	width: 200px;
	}
	#font-image img {
	height:50%; 
	width:100%;
	}
	.alink, .alink:hover, .alink:visited, .alink:active, .alink:link { text-decoration: none; color: inherit; }
	.loginBody{ background-color: #f9f9f9;}
	.cactiLogin {color: #FFF;}
	.titlecacti{ color: #FFF;}
	#route {
	position:absolute;
	height:100%; 
	width:100%;
	overflow: hidden;
	}
	</style>
	
</head>
	
<body class='loginBody'>

	<?php
	include('src/desert_landscape/index.html'); 
	?>	
	
	<div class='loginLeft'></div>
	<div class='loginCenter col-sm-4 col-sm-offset-4' style="margin-top:200px">
	<div class='loginArea'>
	
		<div class='cactiLoginLogo2'></div>
			<legend class="titlecacti"><?php //print __('User Login');?>Cacti reborn</legend>
			<form  method='post' action='login.php'>
				<input type='hidden' name='action' value='login'>
				<div class='cactiLogin'>
			        <label for='login_username'>Utilisateur</label>
					<input class="form-control" type='text' id='login_username' name='login_username' value='<?php print htmlspecialchars($username, ENT_QUOTES); ?>' placeholder='Utilisateur'>					
				
					<label for='login_password'>Mot de Passe</label>
					<input class="form-control" type='password' id='login_password' name='login_password' placeholder='********'>
					
					<div class="checkbox-inline">
						<input class="checkbox " type='checkbox' id='remember_me' name='remember_me'>
						<label for='remember_me' class="">Rester connecté</label>
					</div>
				
					<input class="form-control" name='login' type='submit' value='Se Connecter'>
				
  
					
					
				</div>
			</form>
		</div>
		<div class='versionInfo'><h6>Version 1.0 - Made by Bygmops's team</h6></div>
	</div>
</body>
</html>