docker network create db_graf_network
docker compose up -d
docker compose stop

sudo chmod 777 -R /db_graf

sh create-db-table.sh
