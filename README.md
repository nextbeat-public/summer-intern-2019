
# Quick Start

## Mac

### DBの作成

#### H2 Database (RDB) の起動

```
  $ cd database
  $ ./h2.sh &
```

#### ユーザ・DBの作成

※ 環境に合わせて各自調整しても問題ありません。  
![H2DB 設定サンプル](./readme_images/h2db_setting_sample.png)  

### PlayFramework サーバーの起動

```
  $ sbt run
```

### Typescript / Scss のコンパイル (with ファイル変更監視)

```
  $ cd ui
  $ yarn install
  $ yarn run build.watch
```
