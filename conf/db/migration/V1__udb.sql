
-- ユーザ定義
-- ------------
CREATE TABLE "udb_user" (
  "id"         INT          NOT     NULL AUTO_INCREMENT PRIMARY KEY,
  "name_first" VARCHAR(255) NOT     NULL,
  "name_last"  VARCHAR(255) NOT     NULL,
  "email"      VARCHAR(255) DEFAULT NULL,
  "pref"       VARCHAR(8)   DEFAULT NULL,
  "address"    VARCHAR(255) DEFAULT NULL,
  "updated_at" timestamp    NOT     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at" timestamp    NOT     NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY "udb_user_ukey01" ("email")
) ENGINE=InnoDB;

-- ユーザ・パスワード
-- --------------------
CREATE TABLE "udb_user_password" (
    "id"         INT          NOT NULL PRIMARY KEY,
    "password"   VARCHAR(255) NOT NULL ,
    "updated_at" TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    "created_at" TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ユーザ・セッション
-- --------------------
CREATE TABLE "udb_user_session" (
  "id"         INT         NOT NULL PRIMARY KEY,
  "token"      VARCHAR(64) NOT NULL,
  "exprity"    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "updated_at" TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at" TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY "udb_user_session_ukey01"("token")
) ENGINE=InnoDB;
