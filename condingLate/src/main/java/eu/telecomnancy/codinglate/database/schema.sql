-- Table "address" avec contrainte FOREIGN KEY
CREATE TABLE IF NOT EXISTS "address" (
    "id"      INTEGER NOT NULL UNIQUE,
    "address" TEXT NOT NULL,
    PRIMARY KEY ("id" AUTOINCREMENT)
);

-- Table "imageOffer" avec contrainte FOREIGN KEY
CREATE TABLE IF NOT EXISTS "imageOffer" (
    "id"    INTEGER UNIQUE,
    "path"  TEXT NOT NULL,
    "offer" INTEGER NOT NULL,
    PRIMARY KEY ("id" AUTOINCREMENT),
    FOREIGN KEY ("offer") REFERENCES "offer" ("id")
);

-- Table "imageUser" avec contrainte FOREIGN KEY
CREATE TABLE IF NOT EXISTS "imageUser" (
    "id"    INTEGER NOT NULL UNIQUE,
    "path"  TEXT NOT NULL,
    "user"  INTEGER NOT NULL,
    PRIMARY KEY ("id" AUTOINCREMENT),
    FOREIGN KEY ("user") REFERENCES "user" ("id")
);

-- Table "waiting" avec contrainte FOREIGN KEY
CREATE TABLE IF NOT EXISTS "waiting" (
    "id"        INTEGER UNIQUE,
    "offer"     INTEGER NOT NULL,
    "user"      INTEGER NOT NULL,
    "askingDate" TEXT NOT NULL,
    "status"    INTEGER NOT NULL,
    PRIMARY KEY ("id" AUTOINCREMENT),
    FOREIGN KEY ("offer") REFERENCES "offer" ("id"),
    FOREIGN KEY ("user") REFERENCES "user" ("id")
);

-- Table "rating" avec contrainte FOREIGN KEY
CREATE TABLE IF NOT EXISTS "rating" (
    "id"    INTEGER UNIQUE,
    "offer" INTEGER NOT NULL,
    "user"  INTEGER NOT NULL,
    "value" INTEGER NOT NULL,
    "comment" TEXT,
    PRIMARY KEY ("id" AUTOINCREMENT),
    FOREIGN KEY ("offer") REFERENCES "offer" ("id"),
    FOREIGN KEY ("user") REFERENCES "user" ("id")
);

-- Table "booking" avec contrainte FOREIGN KEY
CREATE TABLE IF NOT EXISTS "booking" (
    "id"          INTEGER UNIQUE,
    "offer"       INTEGER NOT NULL,
    "user"        INTEGER NOT NULL,
    "startingDate" TEXT,
    "endingDate"  TEXT,
    "status"      INTEGER NOT NULL,
    PRIMARY KEY ("id" AUTOINCREMENT),
    FOREIGN KEY ("offer") REFERENCES "offer" ("id"),
    FOREIGN KEY ("user") REFERENCES "user" ("id")
);

-- Table "offer" avec contrainte FOREIGN KEY
CREATE TABLE IF NOT EXISTS "offer" (
    "id"           INTEGER UNIQUE,
    "user"         INTEGER NOT NULL,
    "title"        TEXT NOT NULL,
    "description"  TEXT,
    "price"        REAL NOT NULL,
    "priceType"    INTEGER NOT NULL,
    "startingDate" TEXT,
    "endingDate"   TEXT,
    "category"     INTEGER,
    "brand"        TEXT,
    "model"        TEXT,
    "condition"    INTEGER,
    "year"         INTEGER,
    "service"      INTEGER,
    PRIMARY KEY ("id" AUTOINCREMENT),
    FOREIGN KEY ("user") REFERENCES "user" ("id")
);

-- Table "user" (aucune contrainte FOREIGN KEY n'est nécessaire ici)
CREATE TABLE IF NOT EXISTS "user" (
    "id"       INTEGER NOT NULL UNIQUE,
    "firstname" TEXT NOT NULL,
    "lastname"  TEXT NOT NULL,
    "email"    TEXT NOT NULL UNIQUE,
    "password" TEXT NOT NULL,
    "phone"    TEXT NOT NULL,
    "balance"  REAL NOT NULL,
    "admin"    INTEGER NOT NULL,
    PRIMARY KEY ("id" AUTOINCREMENT)
);
