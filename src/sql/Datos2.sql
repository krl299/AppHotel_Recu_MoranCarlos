CREATE TABLE PROVINCIA(
ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1),
CODIGO CHAR(3),
NOMBRE VARCHAR(30) NOT NULL,

CONSTRAINT ID_PROVINCIA_PK PRIMARY KEY(ID)
);

INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES ('ACO','A CORUÑA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES ('ALA','ÁLAVA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES ('ALB','ALBACETE');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES ('ALI','ALICANTE');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES ('ALM','ALMERIA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES ('AST','ASTURIAS');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES ('AVI','ÁVILA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES ('BAD','BADAJOZ');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES ('BAL','BALEARES');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES ('BAR','BARCELONA');

INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('BUR','BURGOS');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('CAC','CÁCERES');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('CAD','CÁDIZ');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('CAN','CANTÁBRIA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('CAS','CASTELLÓN');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('CIU','CUIDAD REAL');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('COR','CÓRDOBA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('CUE','CUENCA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('GIR','GIRONA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('GRA','GRANADA');

INSERT INTO PROVINCIA (CODIGO, NOMBRE) VALUES ('GU', 'GUADALAJARA');
INSERT INTO PROVINCIA (CODIGO, NOMBRE) VALUES ('GIP', 'GIPUZKOA');
INSERT INTO PROVINCIA (CODIGO, NOMBRE) VALUES ('HUE', 'HUELVA');
INSERT INTO PROVINCIA (CODIGO, NOMBRE) VALUES ('HUS', 'HUESCA');
INSERT INTO PROVINCIA (CODIGO, NOMBRE) VALUES ('JAE', 'JAEN');
INSERT INTO PROVINCIA (CODIGO, NOMBRE) VALUES ('LAR', 'LA RIOJA');
INSERT INTO PROVINCIA (CODIGO, NOMBRE) VALUES ('LAP', 'LAS PALMAS');
INSERT INTO PROVINCIA (CODIGO, NOMBRE) VALUES ('LEO', 'LEON');
INSERT INTO PROVINCIA (CODIGO, NOMBRE) VALUES ('LER', 'LERIDA');
INSERT INTO PROVINCIA (CODIGO, NOMBRE) VALUES ('LUG', 'LUGO');

INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('MAD','MADRID');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('MAL','MÁLAGA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('MUR','MURCIA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('NAV','NAVARRA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('OUR','OURENSE');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('PAL','PALENCIA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('PON','PONTEVEDRA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('SAL','SALAMANCA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('SEG','SEGOVIA');
INSERT INTO PROVINCIA(CODIGO, NOMBRE) VALUES('SEV','SEVLLA');

INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('SOR','SORIA');
INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('TAR','TARRAGONA');
INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('SCT', 'SANTA CRUZ DE TENERIFE');
INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('TER', 'TERUEL');
INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('TOL', 'TOLEDO');
INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('VAL', 'VALENCIA');
INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('VAD', 'VALLADOLID');
INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('VIZ', 'VIZCAYA');
INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('ZAM', 'ZAMORA');
INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('ZAR', 'ZARAGOZA');
INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('CEU', 'CEUTA');
INSERT INTO PROVINCIA (CODIGO,NOMBRE) VALUES ('MEL', 'MELILLA');

CREATE TABLE CLIENTE(
DNI VARCHAR(9) NOT NULL,
NOMBRE VARCHAR(150) NOT NULL,
TELEFONO VARCHAR(15),
DIRECCION VARCHAR(40),
LOCALIDAD VARCHAR(30),
PROVINCIA INTEGER,

CONSTRAINT DNI_CLIENTE_PK PRIMARY KEY (DNI),
CONSTRAINT PROV_CLIENTE_FK FOREIGN KEY (PROVINCIA) REFERENCES PROVINCIA
(ID)
);

INSERT INTO CLIENTE VALUES ('00000000A', 'Isabel Jiménez','123456789' , 
'Calle Paco','Localidad de Prueba', 9);

CREATE TABLE RESERVASALON(
    ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1),
    DNI VARCHAR(9) NOT NULL,
    EVENTO VARCHAR(15),
    FECHA DATE NOT NULL,
    N_PERSONAS INTEGER NOT NULL,
    COMIDA VARCHAR (30),
    HABITACIONES INTEGER,
    N_DIAS INTEGER,
    
    CONSTRAINT DNI_RESERVASALON_PK PRIMARY KEY (ID),
    CONSTRAINT NUM_RESERVASALON_PK FOREIGN KEY (DNI) REFERENCES CLIENTE(DNI)
);

CREATE TABLE RESERVAHABITACION(
    ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1),
    DNI VARCHAR(9) NOT NULL,
    LLEGADA DATE NOT NULL,
    SALIDA DATE NOT NULL,
    N_HABITACIONES INTEGER NOT NULL,
    TIPO VARCHAR(15),
    FUMADOR VARCHAR(10),
    REGIMEN VARCHAR(25),
    
    CONSTRAINT RESERVAHABITACION PRIMARY KEY(ID),
    CONSTRAINT DNI_RESERVAHABITACION_FK FOREIGN KEY (DNI) REFERENCES CLIENTE(DNI)
);
