-- 施設定義 (sample)
--------------
CREATE TABLE "facility" (
  "id"          INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  "location_id" VARCHAR(8)   NOT NULL,
  "name"        VARCHAR(255) NOT NULL,
  "address"     VARCHAR(255) NOT NULL,
  "description" TEXT         DEFAULT NULL,
  "updated_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "created_at"  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 施設情報 (sample)
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵1',  '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵2',  '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵3',  '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵4',  '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵5',  '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵6',  '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵7',  '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵8',  '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵9',  '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵10', '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵11', '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵12', '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵13', '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵14', '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵15', '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵16', '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵17', '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵18', '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵19', '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
INSERT INTO "facility" ("location_id", "name", "address", "description") VALUES ('22100', '駿府城下庵20', '静岡県静岡市葵区追手町1-1', '家康公所縁の宿泊施設です');
