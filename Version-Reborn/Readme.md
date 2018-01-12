Version développée entièrement par la team Bygmops 

- Lancement du poller
A interger dans /etc/crontab
```
*/5 *   * * *   pi bash /home/pi/getsnmp.sh > /var/log/cactireborn.log
```
