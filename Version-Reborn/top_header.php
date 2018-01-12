<?php
global $config, $menu;
?>
<!DOCTYPE html>
<html>
<head>
	<meta content='width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0' name='viewport'>
	<title><?php echo $page_title; ?></title>
	<meta http-equiv='Content-Type' content='text/html;charset=utf-8'>
	<script src="<?php echo $config['url_path']; ?>src/jquery-1.12.4.min.js"></script>
	<script src="<?php echo $config['url_path']; ?>src/bootstrap.min.js" ></script>	
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="<?php echo $config['url_path']; ?>src/bootstrap.min.css" >
	<link rel="stylesheet" href="<?php echo $config['url_path']; ?>src/icofont/css/icofont.css">
	<link rel="icon" href="src/cacti_logo.gif" />
	<style>
	#font-image {
	position:fixed;
	right: 0px;
	bottom: 0px;
	width: 400px;
	}
	#font-image img {
	height:50%; 
	width:100%;
	}
	.alink, .alink:hover, .alink:visited, .alink:active, .alink:link { text-decoration: none; color: inherit; }
	</style>
	
</head>
<body>
<div>
<?php

$username = $_SESSION['user'];
$bdd = new PDO('mysql:host=localhost;dbname=cacti', 'cacti', 'cpir');
?>


<div id="navbar">
		<nav class="navbar navbar-default" style="margin-bottom: 0px;">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="./index.php">
					<div class="d-inline-block">
						<img src="<?php echo $config['url_path']; ?>src/cacti_logo.png" border="0" style="margin-top:-5px; height: 30px; width: 30px;"> Cacti Reborn
					</div>
					</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<?php if($username == "admin"){ ?> 
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="icofont icofont-chart-bar-graph"></i>  Visualisation <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="http://<?php echo $_SERVER['HTTP_HOST']; ?>:8080/">Graphana</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="icofont icofont-computer"></i> Equipements <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<?php echo $config['url_path']; ?>devices.php?add">Ajouter</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="icofont icofont-database"></i>  Données<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="http://<?php echo $_SERVER['HTTP_HOST']; ?>/phpmyadmin" target="_blank"><i class="icofont icofont-database"></i>  Phpmyadmin</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="icofont icofont-page"></i>  Templates<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="<?php echo $config['url_path']; ?>templates.php">Grestion des Templates</a></li>
							</ul>
						</li>
						<?php } ?>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li>
							<div class="btn-group">
								<button type="button" class="btn btn-default navbar-btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									<i class="icofont icofont-ui-user"></i>  <?php echo $username; ?> <span class="caret"></span>
								</button>
								<ul class="dropdown-menu">
									<?php if($username == "admin"){ ?> 
										<li><a href="<?php echo $config['url_path']; ?>users.php"><i class="icofont icofont-users-social"></i> Gestion des Utilisateurs</a></li>
									<li role="separator" class="divider"></li>
									<?php } ?>
									<li><a href="<?php echo $config['url_path']; ?>index.php?logout"><i class="icofont icofont-logout"></i>  Deconnexion</a></li>
									<li><center><h6>Version 1.0</h6></center></li>
								</ul>
							</div>
						</li>

					</ul>
				</div><!-- /.navbar-collapse -->
			</div><!-- /.container-fluid -->
		</nav>
	</div>



