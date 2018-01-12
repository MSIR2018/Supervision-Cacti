<?php 
session_start();
if(isset($_SESSION['connected']) && ($_SESSION['connected'] == "yes")){
	
include("top_header.php");
 ?>

<div class="container">
	
		<div id="font-image" class="pull-right hidden-xs hidden-sm">
			<img src="<?php echo $config['url_path']; ?>src/background.png"  />
		</div>
		<form action="users.php" method="post">
		<legend style="text-align: left;">Gestion des utilisateurs</legend>
		
			<table class="table table-striped">
				<tr><th>Nom</td><td>Action</td></tr>
				<tr><td>Admin</td><td><a href="#"><button type="button" class="btn btn-primary">Editer</button></a>		<a href="#"><button type="button" class="btn btn-danger">Supprimer</button></a></td></tr>
				<tr><td>Guest</td><td><a href="#"><button type="button" class="btn btn-primary">Editer</button></a>		<a href="#"><button type="button" class="btn btn-danger">Supprimer</button></a></td></tr>
				<tr><td></td><td></td></tr>
				<tr><td><div class="col-sm-2">Nouvel Utilisateur: </div><div class="col-sm-3"><input type="text" id="name" name="name" required="required" maxlength="255" class="form-control" placeholder="Nom Utilisateur" /></div><div class="col-sm-3"><input type="text" id="name" name="name" required="required" maxlength="255" class="form-control col-sm-5 " placeholder="Mot de passe" /></div></td><td><input name="form" class="btn btn-primary" type="submit" value="Ajouter" /></td></tr>
			</table>			
		</form>
		<a href="index.php"><input class="btn btn-default" type="button" value="Retour" /></a>
</div>
<?php }else{ header('Location: ./login.php'); } ?>