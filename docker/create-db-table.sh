curl -i -X POST http://localhost:8086/query --data-urlencode "q=CREATE DATABASE mydb"
curl -i -X POST 'http://localhost:8086/write?db=mydb' --data-binary 'cpu_load_short,host=server01,region=us-west value=0.64 1434055562000000000'

