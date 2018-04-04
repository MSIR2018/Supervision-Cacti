#!/bin/bash

dir="/var/www/html/cacti/rra/"
host=0
system=""
x=6

list=($(echo "SELECT hosts.id,hostname,snmp_community,host_template.name,snmp_port,snmp_version FROM hosts,host_template WHERE hosts.host_template_id=host_template.id ;"| mysql -ucacti -pcpir cacti))
nbhost=$(echo "SELECT count(id) FROM hosts ;"| mysql -ucacti -pcpir cacti |tail -n1)
nbselect=6 #selected item in $list query

while [ $host -lt $nbhost ]; do

id=${list[$x]}
ip=${list[$x+1]}:${list[$x+4]}
commune=${list[$x+2]}
host_template=${list[$x+3]}
port=${list[$x+4]}
version=${list[$x+5]}
if [ "$version" == "2" ]; then version="2c" ;fi

#### Template association
if [ "$host_template" == "Windows_10_Desktop" ];then system="windows"; fi
if [ "$host_template" == "Windows_7_Desktop" ];then system="windows"; fi
if [ "$host_template" == "Windows_10_Laptop" ];then system="windows"; fi
if [ "$host_template" == "Windows_7_Laptop" ];then system="windows"; fi
if [ "$host_template" == "Cisco_Router" ];then system="cisco"; fi
if [ "$host_template" == "Linux_Laptop" ];then system="linux"; fi
if [ "$host_template" == "Linux_Desktop" ];then system="linux"; fi
if [ "$host_template" == "Raspberry" ];then system="linux"; fi
if [ "$host_template" == "Phone_Android" ];then system="android"; fi
if [ "$host_template" == "Montre_Android" ];then system="android"; fi
if [ "$host_template" == "Tablette_Android" ];then system="android"; fi


online=$(snmpwalk -v $version -c $commune $ip 1.3.6.1.2.1.1.3)
if [ "$online" != "" ]; then 
echo "UPDATE hosts SET status = 3 WHERE id = $id" | mysql -u cacti -pcpir cacti;

if [ "$system" == "windows" ];then
	hrtable=($(snmpwalk -v $version -c $commune $ip 1.3.6.1.2.1.25.2.3 | grep -E "1.4.1|1.5.1|1.6.1|1.4.4|1.5.4|1.6.4" | awk '{print $4}'))
	listcpu=($(snmpwalk -v $version -c $commune $ip 1.3.6.1.2.1.25.3.3.1.2 | awk '{print $4}'))
	nbprocess=$(snmpwalk -v $version -c $commune $ip .1.3.6.1.2.1.25.1.6.0 | awk '{print $4}')
	processlist=($(snmpwalk -v $version -c $commune $ip 1.3.6.1.2.1.25.4.2.1 | grep -E "4.2.1.1|4.2.1.2" | awk '{print $4}'))
	processressouces=($(snmpwalk -v $version -c $commune $ip 1.3.6.1.2.1.25.5.1.1 | cut -c26- | awk '{print $4}'))
        

	cpu=$(avg=0; i=0; for cpu in ${listcpu[@]}; do avg=$((avg+cpu)); i=$((i+1)) ;done ;echo $((avg/i));)
	disktotal=$((${hrtable[0]}*${hrtable[2]}))
	diskused=$((${hrtable[0]}*${hrtable[4]}))
	ramtotal=$((${hrtable[1]}*${hrtable[3]}))
	ramused=$((${hrtable[1]}*${hrtable[5]}))
fi
if [ "$system" == "linux" ];then
	hrtable=($(snmpwalk -v $version -c $commune $ip 1.3.6.1.2.1.25.2.3 | grep -E "1.4.1 |1.5.1 |1.6.1 |1.4.31 |1.5.31 |1.6.31 " | awk '{print $4}'))
	listcpu=($(snmpwalk -v $version -c $commune $ip 1.3.6.1.2.1.25.3.3.1.2 | awk '{print $4}'))
	nbprocess=$(snmpwalk -v $version -c $commune $ip .1.3.6.1.2.1.25.1.6.0 | awk '{print $4}')
	processlist=($(snmpwalk -v $version -c $commune $ip 1.3.6.1.2.1.25.4.2.1 | grep -E "4.2.1.1|4.2.1.2" | awk '{print $4}'))
	processressouces=($(snmpwalk -v $version -c $commune $ip 1.3.6.1.2.1.25.5.1.1 | cut -c26- | awk '{print $4}'))
	cpu=$(avg=0; i=0; for cpu in ${listcpu[@]}; do avg=$((avg+cpu)); i=$((i+1)) ;done ;echo $((avg/i));)
	disktotal=$((${hrtable[1]}*${hrtable[3]}))
	diskused=$((${hrtable[1]}*${hrtable[5]}))
	ramtotal=$((${hrtable[0]}*${hrtable[2]}))
	ramused=$((${hrtable[0]}*${hrtable[4]}))
fi

if [ "$system" == "android" ];then
	processlist=($(snmpwalk -v $version -c $commune $ip .1.3.6.1.4.1.12619.1.2.2 | grep -v ".1.2.2.1.3" | awk '{print $4}'))
        nbprocess=($(snmpwalk -v $version -c $commune $ip .1.3.6.1.4.1.12619.1.2.1 | awk '{print $4}'))
        
	ram=$(snmpwalk -v $version -c $commune $ip 1.3.6.1.4.1.12619.9.1 | awk '{print $4}')
	disk=$(snmpwalk -v $version -c $commune $ip 1.3.6.1.4.1.12619.9.2 | awk '{print $4}')	
        cpu=0

	echo 'host '$ip
	echo 'Template '$host_template
	echo 'diskutilise '$disk
	echo 'ramutilise '$ram

	echo "INSERT INTO logressources (deviceid,cpu,ram,disk) VALUES ($id, $cpu, $ram, $disk);" | mysql -u cacti -pcpir cacti;

	echo "DELETE FROM logprocess WHERE deviceid = $id;" | mysql -u cacti -pcpir cacti;

        i=0
	while [ $i -lt $nbprocess ]; do
	  pid=${processlist[$i]};
	  name="${processlist[$((i+nbprocess))]}"
	  name=$(echo $name | tr -d '"')
	  processram=${processlist[$((i+nbprocess+nbprocess))]};
	  processcpu=0

	  #echo $pid
	  #echo $name
	  #echo $processram

	  if [ $id ] && [ $pid ] && [ $name ] && [ $processcpu ] && [ $processram ]; then
  	   echo "INSERT INTO logprocess (deviceid,pid,name,cpu,ram) VALUES ($id, $pid, '$name', $processcpu, $processram);" | mysql -u cacti -pcpir cacti;
  	  fi	 
	i=$((i+1)) 
	done
fi

if [ "$system" == "windows" ] || [ "$system" == "linux" ];then
echo 'host '$ip
echo 'Template '$host_template
echo 'disktotal '$disktotal
echo 'diskutilise '$diskused
echo 'ramtotal '$ramtotal
echo 'ramutilise '$ramused
echo 'cpu utilise '$cpu'%'

perramused=$(((ramused*100)/ramtotal))
perdiskusedused=$(((diskused*100)/disktotal))

echo "INSERT INTO logressources (deviceid,cpu,ram,disk) VALUES ($id, $cpu, $perramused, $perdiskusedused);" | mysql -u cacti -pcpir cacti;

echo "DELETE FROM logprocess WHERE deviceid = $id;" | mysql -u cacti -pcpir cacti;

i=0
while [ $i -lt $nbprocess ]; do
  pid=${processlist[$i]};
  name="${processlist[$((i+nbprocess))]}"
  name=$(echo $name | tr -d '"')
  processcpu=${processressouces[$((i))]}
  processcpu=$((processcpu*100)); #centi Seconde to Sec
  processram=${processressouces[$((i+nbprocess))]}
  
  #debug
#  echo "pid: $pid"
#  echo "name: " $name
#  echo 'processcpu: '$processcpu
#  echo 'processram: '$processram

  if [ $id ] && [ $pid ] && [ $name ] && [ $processcpu ] && [ $processram ]; then  
    echo "INSERT INTO logprocess (deviceid,pid,name,cpu,ram) VALUES ($id, $pid, '$name', $processcpu, $processram);" | mysql -u cacti -pcpir cacti;
  fi

  i=$((i+1))
done
fi

else
  echo "UPDATE hosts SET status = 1 WHERE id = $id" | mysql -u cacti -pcpir cacti;
fi

echo -e "\n ############ \n"
system=""
x=$((x+nbselect))
host=$((host+1))
done
