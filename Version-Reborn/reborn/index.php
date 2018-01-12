<?php
session_start();
if(isset($_SESSION['connected']) && ($_SESSION['connected'] == "yes")){
	
if(isset($_GET['logout'])){ $_SESSION['connected']="no"; header('Location: ./index.php'); } //logout
	
include("top_header.php");
	?>
	<div class="container">
	
		<div id="font-image" class="pull-right hidden-xs hidden-sm">
			<img src="<?php echo $config['url_path']; ?>src/background.png"  />
		</div>
		
		<legend style="text-align: left;">Equipements</legend>

		<?php 
		$hosts = $bdd->query('SELECT hosts.id as id, hosts.description as description,host_template.name as tmpname,status FROM hosts,host_template WHERE hosts.host_template_id=host_template.id ORDER BY id');  
		
		foreach($hosts as $host) {
	
		$img="null";
		$dashboard="windows-linux-devices";
		if($host['tmpname'] == 'Raspberry'){ $img="raspi.png"; }
		if($host['tmpname'] == 'Windows_10_Desktop'){ $img="ordifixwindows.png"; }
		if($host['tmpname'] == 'Linux_Desktop'){ $img="ordifixlinux.png"; }
		if($host['tmpname'] == 'Windows_10_Laptop'){ $img="laptopwindows.png"; }
		if($host['tmpname'] == 'Windows_7_Desktop'){ $img="ordifixwindows7.png"; }
		if($host['tmpname'] == 'Windows_7_Laptop'){ $img="laptopwindows7.png"; }
		if($host['tmpname'] == 'Linux_Laptop'){ $img="laptoplinux.png"; }
		if($host['tmpname'] == 'Phone_Android'){ $img="phoneandroid.png"; $dashboard="android-devices";}
		if($host['tmpname'] == 'Montre_Android'){ $img="montreandroid.png"; $dashboard="android-devices";}
		if($host['tmpname'] == 'Tablette_Android'){ $img="tabletteandroid.png"; $dashboard="android-devices";}
		if($host['tmpname'] == 'Cisco_Router'){ $img="cisco.png"; }
		
		if($host['disabled'] == 'on'){ $class="default"; }else{
			if($host['status'] == '0'){ $class="info"; }
			if($host['status'] == '1'){ $class="danger"; }
			if($host['status'] == '2'){ $class="warning"; }
			if($host['status'] == '3'){ $class="success"; }
		}
		

		?>
		<div class="col-sm-3">
			<div class="panel panel-<?php echo $class; ?>">
				<div class="panel-heading ">
					<h3 class="panel-title">
						
							<?php echo ucfirst(str_replace('_', ' ', $host['description'])); ?> <?php if($username == "admin"){ ?>  <a target="_blank" class="alink pull-right" href="devices.php?edit=<?php echo $host['id'];?>"><i class="icofont icofont-gear"></i></a> <?php } ?>
						
					</h3>
				</div>
				<div class="panel-body">
					<a href="http://<?php echo $_SERVER['HTTP_HOST']; ?>:8080/dashboard/db/<?php echo $dashboard ?>?orgId=1&var-deviceid=<?php echo $host['id'];?>">
						<img src="<?php echo $config['url_path']; ?>src/devices/<?php echo $img; ?>" style="height:100%; width:100%;">
					</a>
				</div>
			</div>
		</div>
		<?php } ?>
		
	</div>
	
<?php }else{ header('Location: ./login.php'); } ?>

