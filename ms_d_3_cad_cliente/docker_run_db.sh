#sudo docker pull mysql/sqlserver:5.7.22
sudo docker run --name mysqldb -p 3306:3306 -d --network grupo_microsservicos mysql/mysql-server:5.7.22

#docker logs
#create database clientdt
#create user 'usuariocli'@'%' identified by 'senhacli';
#grant all on *.* to 'usuariocli'@'%';