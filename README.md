
# Quick Start

### required

- docker
- docker-compose


### run

```
 $ docker-compose up
```

### rerun sbt
```
 $ docker-compose exec scala sbt run
```

### login scala container
```
 $ docker-compose exec scala bash
```

### rerun ui build
```
 $ docker-compose exec ui yarn build.watch
```

### login scala container
```
 $ docker-compose exec ui bash
```


### connect db by browser

JDBC URL:   jdbc:h2:tcp://localhost/./nextbeat;MODE=MySQL  
ユーザ名:   nextbeat  
パスワード: pass  

![H2DB 接続画面](./readme_images/h2db_connect.png)  

DB作成・接続情報は以下に設定されています。  
任意に変更可能ですが、慣れていない場合にはそのままにすることを推奨します。

### see web app
```
 http://localhost:9000/
```

### see migration
```
 http://localhost:9000/@flyway
```