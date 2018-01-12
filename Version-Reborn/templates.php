<?php 
session_start();
if(isset($_SESSION['connected']) && ($_SESSION['connected'] == "yes")){
	
include("top_header.php");

if(isset($_POST['form'])){
	
	if($_POST['action'] == "add"){ //mode add
		$insert = $bdd->prepare('INSERT INTO host_template (name) VALUES (:name) ');
		$insert->execute(array(
		'name' => $_POST['name']));
	}
	if($_POST['action'] == "delete"){ //mode delete
		$delete = $bdd->prepare('DELETE FROM host_template WHERE id = :id');
		$delete->execute(array(
		'id' => $_POST['id']));
	}
	
	?>
	<div class="alert alert-success" role="alert"><a href="<?php echo $config['url_path']; ?>/reborn/" class="btn btn-xs btn-warning pull-left" style="margin-right: 20px;">Retour à l'accueil</a>  Demande prise en compte</div>
	<?php } ?>

<div class="container">
	
		<div id="font-image" class="pull-right hidden-xs hidden-sm">
			<img src="<?php echo $config['url_path']; ?>src/background.png"  />
		</div>
		
		<?php 
		if(isset($_GET['delete'])){
		$templates = $bdd->query('SELECT id,name FROM host_template WHERE id='.$_GET['delete'].' ORDER BY id');
		foreach($templates as $template){
		?>
		<form action="templates.php" method="post">
		<input type="hidden" name="action" id="action" value="delete" />
		<input type="hidden" name="id" id="id" value="<?php echo $template['id']; ?>" />
		<legend style="text-align: left;">Supprimer Equipement</legend>
		
			<div class="alert alert-warning">
				<div style="margin-bottom:10px;">
					<a href="index.php" class="pull-right"><input name="form" class="btn btn-danger" type="button" value="Non" /></a>
					<a class="pull-right"><input name="form" class="btn btn-success" type="submit" value="Oui" /></a>
					<strong>Warning:</strong> Etes vous sûr de vouloir supprimer le template <?php echo $template['name']; ?> ? </br>
					</div>
			</div>
			
		<?php } }else{ 
		$templateslist = $bdd->query('SELECT id,name FROM host_template');
		?>
		<form action="templates.php?add" method="post">
		<legend style="text-align: left;">Gestion des templates</legend>
		
			<table class="table table-striped">
				<tr><th>Nom</td><td>Action</td></tr>
				<?php foreach($templateslist as $templates){ ?>
				<tr><td><?php echo $templates['name']; ?></td><td><a href="templates.php?delete=<?php echo $templates['id']; ?>"><button type="button" class="btn btn-danger">Supprimer</button></a></td></tr>
				<?php } ?>
				<input type="hidden" name="action" id="action" value="add" />
				<tr><td><input type="text" id="name" name="name" required="required" maxlength="255" class="form-control " placeholder="Nom du template" /></td><td><input name="form" class="btn btn-primary" type="submit" value="Ajouter" /></td></tr>
				
			</table>			
			
		<?php } ?>
			
		</form>
		<a href="index.php"><input class="btn btn-default" type="button" value="Retour" /></a>
		
</div>
<?php }else{ header('Location: ./login.php'); } ?>