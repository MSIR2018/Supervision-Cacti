<?php 
session_start();
if(isset($_SESSION['connected']) && ($_SESSION['connected'] == "yes")){

include("top_header.php");

if(isset($_POST['form'])){
	
	if($_POST['action'] == "edit"){ //mode edit
		$update = $bdd->prepare('UPDATE hosts SET description = :description, hostname = :hostname, host_template_id = :host_template_id, snmp_community = :snmp_community, snmp_version = :snmp_version, snmp_port = :snmp_port WHERE id = :id ');
		$update->execute(array(
		'id' => $_POST['id'],
		'description' => $_POST['description'],
		'hostname' => $_POST['hostname'],
		'host_template_id' => $_POST['host_template_id'],
		'snmp_community' => $_POST['snmp_community'],
		'snmp_version' => $_POST['snmp_version'],
		'snmp_port' => $_POST['snmp_port']));
	}
	if($_POST['action'] == "add"){ //mode add
		$insert = $bdd->prepare('INSERT INTO hosts ( description ,hostname, host_template_id,snmp_community,snmp_version, snmp_port, status) VALUES (:description, :hostname, :host_template_id, :snmp_community, :snmp_version, :snmp_port, 0) ');
		$insert->execute(array(
		'description' => $_POST['description'],
		'hostname' => $_POST['hostname'],
		'host_template_id' => $_POST['host_template_id'],
		'snmp_community' => $_POST['snmp_community'],
		'snmp_version' => $_POST['snmp_version'],
		'snmp_port' => $_POST['snmp_port']));
	}
	if($_POST['action'] == "delete"){ //mode delete
		$delete = $bdd->prepare('DELETE FROM hosts WHERE id = :id');
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
		if(isset($_GET['add'])){
		$templateslist = $bdd->query('SELECT id,name FROM host_template');
		?>
		<form action="devices.php?add" method="post">
		<legend style="text-align: left;">Ajouter Equipement</legend>
		
			<label>Name:</label>
            <input type="text" id="description" name="description" required="required" maxlength="255" class="form-control " value="PC Formateur" />
            </br>

            <label>Adresse ip ou Hostname:</label>
            <input type="text" id="hostname" name="hostname" required="required" maxlength="255" class="form-control " value="192.168.20.1" />
            </br>
			
			<label>Template:</label>
			<select id="host_template_id" name="host_template_id" required="required" class="form-control">
			  <?php foreach($templateslist as $templates){ ?>
			    <option value="<?php echo $templates['id']; ?>"><?php echo $templates['name']; ?></option>
			  <?php } ?>
			</select>
            </br>
			
			<label>SNMP Comunnity:</label>
            <input type="text" id="snmp_community" name="snmp_community" required="required" maxlength="255" class="form-control " value="public"/>
            </br>
			
			<label>SNMP Version:</label>
			 <select id="snmp_version" name="snmp_version" required="required" class="form-control">
			  <option value="1">1</option>
			  <option value="2c" selected>2c</option>
			</select> 
            </br>
			
			<label>SNMP Port:</label>
            <input type="number" id="snmp_port" name="snmp_port" required="required" maxlength="255" class="form-control" value="161" />
            </br>
			<input type="hidden" name="action" id="action" value="add" />
			<input name="form" class="btn btn-primary" type="submit" value="Enregistrer" />
			<a href="index.php"><input class="btn btn-default" type="button" value="Retour" /></a>
			
			
		<?php } ?>
		
		<?php 
		if(isset($_GET['edit'])){
		$templateslist = $bdd->query('SELECT id,name FROM host_template');
		$hosts = $bdd->query('SELECT id , description ,hostname, host_template_id,snmp_community,snmp_version, snmp_port FROM hosts WHERE id='.$_GET['edit'].' ORDER BY id');
		foreach($hosts as $host){
		?>
		<form action="devices.php?edit=<?php echo $host['id']; ?>" method="post">
		<legend style="text-align: left;">Editer Equipement</legend>
		
			<label>Name:</label>
            <input type="text" id="description" name="description" required="required" maxlength="255" class="form-control " value="<?php echo $host['description']; ?>" />
            </br>

            <label>Adresse ip ou Hostname:</label>
            <input type="text" id="hostname" name="hostname" required="required" maxlength="255" class="form-control " value="<?php echo $host['hostname']; ?>" />
            </br>
			
			<label>Template:</label>
			<select id="host_template_id" name="host_template_id" required="required" class="form-control">
			  <?php foreach($templateslist as $templates){ ?>
			    <option value="<?php echo $templates['id'];?>" <?php if($templates['id'] == $host['host_template_id']){ echo "selected=selected"; } ?> ><?php echo $templates['name']; ?></option>
			  <?php } ?>
			</select>
            </br>
			
			<label>SNMP Comunnity:</label>
            <input type="text" id="snmp_community" name="snmp_community" required="required" maxlength="255" class="form-control " value="<?php echo $host['snmp_community']; ?>"/>
            </br>
			
			<label>SNMP Version:</label>
			 <select id="snmp_version" name="snmp_version" required="required" class="form-control">
			  <option value="1" <?php if($host['snmp_version'] == 1){ echo "selected=selected"; } ?> >1</option>
			  <option value="2" <?php if($host['snmp_version'] == 2){ echo "selected=selected"; } ?> >2c</option>
			</select> 
            </br>
			
			<label>SNMP Port:</label>
            <input type="number" id="snmp_port" name="snmp_port" required="required" maxlength="5" class="form-control" value="<?php echo $host['snmp_port']; ?>" />
            </br>
			
			<input type="hidden" name="id" id="id" value="<?php echo $host['id']; ?>" />
			<input type="hidden" name="action" id="action" value="edit" />
			<a href="devices.php?delete=<?php echo $host['id']; ?>"><input class="btn btn-danger" type="button" value="Supprimer l'equipement" /></a>
			<a href="index.php"><input class="btn btn-default" type="button" value="Retour" /></a>
			<input name="form" class="btn btn-primary " type="submit" value="Enregistrer" />
			
			
		<?php } } ?>
		
		<?php 
		if(isset($_GET['delete'])){
		$hosts = $bdd->query('SELECT id,description,hostname FROM hosts WHERE id='.$_GET['delete'].' ORDER BY id');
		foreach($hosts as $host){
		?>
		<form action="devices.php" method="post">
		<input type="hidden" name="action" id="action" value="delete" />
		<input type="hidden" name="id" id="id" value="<?php echo $host['id']; ?>" />
		<legend style="text-align: left;">Supprimer Equipement</legend>
		
			<div class="alert alert-warning">
				<div style="margin-bottom:10px;">
					<a href="index.php" class="pull-right"><input name="form" class="btn btn-danger" type="button" value="Non" /></a>
					<a class="pull-right"><input name="form" class="btn btn-success" type="submit" value="Oui" /></a>
					<strong>Warning:</strong> Etes vous sûr de vouloir supprimer <?php echo $host['description']; ?> (<?php echo $host['hostname']; ?>) ? </br>
					</div>
			</div>
			
		<?php } } ?>
			
		</form>
		
</div>
<?php }else{ header('Location: ./login.php'); } ?>