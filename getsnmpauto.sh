list=$(nmap -sU -p 161,32150 --open --script snmp-sysdescr 192.168.10.0/24 172.16.18.0/24 -oG - | awk '/Up$/{print $2}' )

file="/var/www/html/reborn/.inventory.tmp"
echo > $file
for host in $list
do
  ip=$host
  echo $ip
  info=$(snmpwalk -v 2c -c public $host  1.3.6.1.2.1.1 | grep -E '3.6.1.2.1.1.1.0|3.6.1.2.1.1.5.0' | cut -d '"' -f 2 | sed 's/$/;/')
  if [ ! -z "$info" ]; then
    echo -e $ip\;$info >> $file
  else
    android=$(snmpwalk -v 1 -c public $host:32150  1.3.6.1.2.1.1 | grep -E '3.6.1.2.1.1.1.0|3.6.1.2.1.1.5.0' | cut -d '"' -f 2 | sed 's/$/;/')
    if [ ! -z "$android" ]; then
	echo -e $ip\;$android >> $file
    fi
  fi

  info=''
done 
mv $file /var/www/html/reborn/inventory.info
