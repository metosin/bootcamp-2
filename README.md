# Bootcamp

## Frontend

### Dev
```
# Start Cljx compiler and Figwheel (Cljs compiler + live reload)
$ lein pdo cljx auto, figwheel app
# Start repl and http server
$ lein repl
bootcamp.server=> (start)
```

### Server
```
$ lein with-profile frontend uberjar
$ java -cp target/bootcamp.jar -Dis_dev=false server
```
