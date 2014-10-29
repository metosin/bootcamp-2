# Bootcamp

## Frontend

### Dev
```
$ lein repl
server=> (run)
```

### Server
```
$ lein with-profile frontend uberjar
$ java -cp target/bootcamp.jar -Dis_dev=false server
```
