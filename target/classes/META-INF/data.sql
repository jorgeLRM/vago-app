INSERT INTO Agave (name, status) VALUES ('Agave angustifolia', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave americana', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave potatorum', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave karwinskii', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave cupreata', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave seemanniana', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave marmorata', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave convalis', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave inaequidens', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave tequilana', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave atrovirens', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave inaequidens', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave duranguensis', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave salmiana', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave maximiliana', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave montana', 'ACTIVE');
INSERT INTO Agave (name, status) VALUES ('Agave rhodacantha', 'ACTIVE');

INSERT INTO Maguey (name, abbreviation, ageOfMaturation, idAgave, status) VALUES ('Maguey Espadin','E', 10, 1, 'ACTIVE');
INSERT INTO Maguey (name, abbreviation, ageOfMaturation, idAgave, status) VALUES ('Maguey Pelon Verde','PV', 9,  1, 'ACTIVE');
INSERT INTO Maguey (name, abbreviation, ageOfMaturation, idAgave, status) VALUES ('Maguey Sierra Negra Angustifolia','SN',15, 1, 'ACTIVE');
INSERT INTO Maguey (name, abbreviation, ageOfMaturation, idAgave, status) VALUES ('Maguey Coyote','CY',12, 1, 'ACTIVE');
INSERT INTO Maguey (name, abbreviation, ageOfMaturation, idAgave, status) VALUES ('Maguey Arroqueño','AR',15, 2, 'ACTIVE');
INSERT INTO Maguey (name, abbreviation, ageOfMaturation, idAgave, status) VALUES ('Maguey Tobala','TB',12, 3, 'ACTIVE');
INSERT INTO Maguey (name, abbreviation, ageOfMaturation, idAgave, status) VALUES ('Maguey Cuishe','CS',14, 4, 'ACTIVE');
INSERT INTO Maguey (name, abbreviation, ageOfMaturation, idAgave, status) VALUES ('Maguey Bicuishe Verde','BS',12, 4, 'ACTIVE');
INSERT INTO Maguey (name, abbreviation, ageOfMaturation, idAgave, status) VALUES ('Maguey Madrecuishe','MC',12, 4, 'ACTIVE');
INSERT INTO Maguey (name, abbreviation, ageOfMaturation, idAgave, status) VALUES ('Maguey Tepeztate','TZ',15, 7, 'ACTIVE');
INSERT INTO Maguey (name, abbreviation, ageOfMaturation, idAgave, status) VALUES ('Maguey Jabalí','JB', 10, 8, 'ACTIVE');

INSERT INTO Producer (name, abbreviation, typeProducer, telephone, status, photo) VALUES ('Aquilino Mendoza', 'A', 'BOTH', '(951) 334-9283', 'ACTIVE', 'no_image.png');
INSERT INTO Producer (name, abbreviation, typeProducer, telephone, status, photo) VALUES ('Joel Barriga', 'J', 'MEZCAL_PRODUCER', '(951) 756-3423', 'ACTIVE', 'no_image.png');
INSERT INTO Producer (name, abbreviation, typeProducer, telephone, status, photo) VALUES ('Salomon Rey', 'S', 'AGAVE_PRODUCER', '(951) 852-2345', 'ACTIVE', 'no_image.png');
INSERT INTO Producer (name, abbreviation, typeProducer, telephone, status, photo) VALUES ('Emigdio Jarquin', 'E', 'BOTH', '(951) 892-2422', 'ACTIVE', 'no_image.png');
INSERT INTO Producer (name, abbreviation, typeProducer, telephone, status, photo) VALUES ('Tio Rey', 'TR', 'MEZCAL_PRODUCER', '(951) 981-9812', 'ACTIVE', 'no_image.png');
INSERT INTO Producer (name, abbreviation, typeProducer, telephone, status, photo) VALUES ('Dos Vales S.A de C.V', 'DV', 'BOTH', '(951) 234-1423', 'ACTIVE', 'no_image.png');

INSERT INTO Estate (name, crmnumber, state, municipality, locality, idProducer, status) VALUES ('Sola 1', 24322,'Oaxaca', 'Sola de Vega', 'Santa María Lachixio', 1, 'ACTIVE');
INSERT INTO Estate (name, crmnumber, state, municipality, locality, idProducer, status) VALUES ('Sola 2', 34232,'Oaxaca', 'Sola de Vega', 'San Ildefonso Sola', 3, 'ACTIVE');
INSERT INTO Estate (name, crmnumber, state, municipality, locality, idProducer, status) VALUES ('Totolapa 1', 32342,'Oaxaca', 'San Pedro Totolapa', 'San Juan Guegoyachi', 4, 'ACTIVE');
INSERT INTO Estate (name, crmnumber, state, municipality, locality, idProducer, status) VALUES ('Dos Vales 1', 98131,'Oaxaca', 'Miahuatlan', 'Santa Catarina Roatina ', 6, 'ACTIVE');
INSERT INTO Estate (name, crmnumber, state, municipality, locality, idProducer, status) VALUES ('Miahuatlan', 23191,'Oaxaca', 'Miahuatlan', 'Guixe', 4, 'ACTIVE');

INSERT INTO Palenque (idProducer, name, state, municipality, locality, status) VALUES (2, 'Vago 1', 'Oaxaca', 'Sola de Vega', 'San Isidro Ojo de Agua', 'ACTIVE');
INSERT INTO Palenque (idProducer, name, state, municipality, locality, status) VALUES (5, 'Vago 2','Oaxaca', 'Miahuatlan', 'Pie de la Cuesta', 'ACTIVE');
INSERT INTO Palenque (idProducer, name, state, municipality, locality, status) VALUES (2, 'Vago 3','Oaxaca', 'Miahuatlan', 'Cumbre de Jonotal', 'ACTIVE');
INSERT INTO Palenque (idProducer, name, state, municipality, locality, status) VALUES (5, 'Vago 4','Oaxaca', 'Miahuatlan', 'Agua de la Peña', 'ACTIVE');

INSERT INTO Plantation (initialStock, lot, plantingDate, registrationDate, stock, unknownSpecies, idEstate, idMaguey, status) VALUES (200, '"Sola 1"-CS-04/12/2006', '2006-12-04','2021-12-01', 678, false, 1, 7, 'ACTIVE');
INSERT INTO Plantation (initialStock, lot, plantingDate, registrationDate, stock, unknownSpecies, idEstate, idMaguey, status) VALUES (135, '"Sola 1"-TB-06/05/2005', '2005-05-06','2021-12-04', 568, false, 1, 6, 'ACTIVE');
INSERT INTO Plantation (initialStock, lot, plantingDate, registrationDate, stock, unknownSpecies, idEstate, idMaguey, status) VALUES (250, '"Totolapa 1"-TZ-22/11/2007', '2007-11-22','2021-12-01', 853, false, 3, 10, 'ACTIVE');
INSERT INTO Plantation (initialStock, lot, plantingDate, registrationDate, stock, unknownSpecies, idEstate, idMaguey, status) VALUES (193, '"Totolapa 1"-MCX-24/11/2007', '2007-11-24','2021-12-01', 453, false, 3, 9, 'ACTIVE');
INSERT INTO Plantation (initialStock, lot, plantingDate, registrationDate, stock, unknownSpecies, idEstate, idMaguey, status) VALUES (185, '"Sola 2"-SN-24/11/2005', '2005-11-24','2021-12-01', 646, false, 2, 3, 'ACTIVE');
INSERT INTO Plantation (initialStock, lot, plantingDate, registrationDate, stock, unknownSpecies, idEstate, idMaguey, status) VALUES (120, '"Dos Vales 1"-CY-24/11/2008', '2008-11-24','2021-12-01', 573, false, 4, 4, 'ACTIVE');

INSERT INTO LotDetail (registrationDate, sequence, idPalenque, status) VALUES ('2022-02-08', 1, 1, 'ACTIVE');
INSERT INTO LotDetail (registrationDate, sequence, idPalenque, status) VALUES ('2022-02-08', 1, 2, 'ACTIVE');
INSERT INTO LotDetail (registrationDate, sequence, idPalenque, status) VALUES ('2022-02-08', 2, 3, 'ACTIVE');
INSERT INTO LotDetail (registrationDate, sequence, idPalenque, status) VALUES ('2022-02-08', 2, 4, 'ACTIVE');

INSERT INTO Cutting (lot, guideNumber, guideLink, cutoffDate, idLotDetail, status) VALUES ('J-01-TBCS-22', '2423423423', 'Corte-1.pdf', '2022-02-03', 1, 'ACTIVE');
INSERT INTO Cutting (lot, guideNumber, guideLink, cutoffDate, idLotDetail, status) VALUES ('TR-01-SN-22', '7474342534', 'Corte-1.pdf', '2022-02-09', 2, 'ACTIVE');
INSERT INTO Cutting (lot, guideNumber, guideLink, cutoffDate, idLotDetail, status) VALUES ('J-02-MC-22', '6234723462', 'Corte-1.pdf', '2022-02-10', 3, 'ACTIVE');
INSERT INTO Cutting (lot, guideNumber, guideLink, cutoffDate, idLotDetail, status) VALUES ('TR-02-CY-22', '9234534534', 'Corte-1.pdf', '2022-02-09', 4, 'ACTIVE');

INSERT INTO CuttingDetail (weight, numberPinneapples, idPlantation, idCutting, status) VALUES (1134, 110, 2, 1, 'ACTIVE');
INSERT INTO CuttingDetail (weight, numberPinneapples, idPlantation, idCutting, status) VALUES (1256, 120, 1, 1, 'ACTIVE');
INSERT INTO CuttingDetail (weight, numberPinneapples, idPlantation, idCutting, status) VALUES (1345, 110, 5, 2, 'ACTIVE');
INSERT INTO CuttingDetail (weight, numberPinneapples, idPlantation, idCutting, status) VALUES (1734, 145, 4, 3, 'ACTIVE');
INSERT INTO CuttingDetail (weight, numberPinneapples, idPlantation, idCutting, status) VALUES (1124, 110, 6, 4, 'ACTIVE');

INSERT INTO Tub(bottomDiameter1, bottomDiameter2, bottomDiameter3, bottomDiameter4, height1, height2, height3, height4, numberTub, topDiameter1, topDiameter2, topDiameter3, topDiameter4, idPalenque, status) VALUES (134, 134, 134, 134, 101, 101, 101, 101, 1, 126, 130, 126, 130, 1, 'ACTIVE');
INSERT INTO Tub(bottomDiameter1, bottomDiameter2, bottomDiameter3, bottomDiameter4, height1, height2, height3, height4, numberTub, topDiameter1, topDiameter2, topDiameter3, topDiameter4, idPalenque, status) VALUES (134, 134, 134, 134, 101, 101, 101, 101, 2, 124, 126, 125, 124, 1, 'ACTIVE');
INSERT INTO Tub(bottomDiameter1, bottomDiameter2, bottomDiameter3, bottomDiameter4, height1, height2, height3, height4, numberTub, topDiameter1, topDiameter2, topDiameter3, topDiameter4, idPalenque, status) VALUES (139, 138, 139, 138, 88, 88, 88, 88, 3, 134, 137, 134, 135, 1, 'ACTIVE');
INSERT INTO Tub(bottomDiameter1, bottomDiameter2, bottomDiameter3, bottomDiameter4, height1, height2, height3, height4, numberTub, topDiameter1, topDiameter2, topDiameter3, topDiameter4, idPalenque, status) VALUES (137, 139, 139, 139, 88, 88, 88, 88, 4, 135, 135, 138, 136, 1, 'ACTIVE');
INSERT INTO Tub(bottomDiameter1, bottomDiameter2, bottomDiameter3, bottomDiameter4, height1, height2, height3, height4, numberTub, topDiameter1, topDiameter2, topDiameter3, topDiameter4, idPalenque, status) VALUES (139, 139, 140, 140, 88, 88, 88, 88, 5, 136, 137, 137, 140, 1, 'ACTIVE');
INSERT INTO Tub(bottomDiameter1, bottomDiameter2, bottomDiameter3, bottomDiameter4, height1, height2, height3, height4, numberTub, topDiameter1, topDiameter2, topDiameter3, topDiameter4, idPalenque, status) VALUES (140, 139, 139, 139, 88, 88, 88, 88, 6, 135, 137, 137, 136, 1, 'ACTIVE');
INSERT INTO Tub(bottomDiameter1, bottomDiameter2, bottomDiameter3, bottomDiameter4, height1, height2, height3, height4, numberTub, topDiameter1, topDiameter2, topDiameter3, topDiameter4, idPalenque, status) VALUES (150, 149, 150, 150, 83, 83, 83, 83, 7, 145, 145, 145, 145, 1, 'ACTIVE');
INSERT INTO Tub(bottomDiameter1, bottomDiameter2, bottomDiameter3, bottomDiameter4, height1, height2, height3, height4, numberTub, topDiameter1, topDiameter2, topDiameter3, topDiameter4, idPalenque, status) VALUES (139, 140, 139, 139, 88, 88, 88, 88, 8, 135, 137, 135, 137, 1, 'ACTIVE');

INSERT INTO Production (startCoocking, endCoocking, alcoholicGradeDist1, alcoholicGradeDist2, volumeDistillation2, totalVolume, stock, lot, idLotDetail, admissionDate, location, wastage, paymentStatus, productionStatus, typeProduction, status) VALUES ('2022-02-01', '2022-02-02', 1.25, 1, 1200, 1200, 1200, 'J-01-TBCS-22', 1, '2022-02-09', 'Tinas', 0, 'APPROBAL', 'INBULK', 'NORMAL', 'ACTIVE');
INSERT INTO Production (startCoocking, endCoocking, alcoholicGradeDist1, alcoholicGradeDist2, volumeDistillation2, totalVolume, stock, lot, idLotDetail, admissionDate, location, wastage, paymentStatus, productionStatus, typeProduction, status) VALUES ('2022-02-01', '2022-02-02', 1.25, 2, 1200, 1200, 1200, 'J-02-MC-22', 3, '2022-02-09', 'Tinas', 0, 'APPROBAL', 'PREPARATION', 'NORMAL', 'ACTIVE');
--INSERT INTO Production (startCoocking, endCoocking, alcoholicGradeDist1, alcoholicGradeDist2, volumeDistillation2, totalVolume, lot, idLotDetail, admissionDate, location, wastage, paymentStatus, productionStatus, typeProduction, status) VALUES ('2022-02-03', '2022-02-04', 2.75, 1.25, 1120, 1120, 'TR-01-SN-22', 2, '2022-02-10', 'Tinas', 0, 'APPROBAL', 'PREPARATION', 'StandardProduction', 'ACTIVE');
--INSERT INTO Production (startCoocking, endCoocking, alcoholicGradeDist1, alcoholicGradeDist2, volumeDistillation2, totalVolume, lot, idLotDetail, admissionDate, location, wastage, paymentStatus, productionStatus, typeProduction, status) VALUES ('2022-02-04', '2022-02-05', 1.75, 2, 1115, 1115, 'J-02-MC-22', 3, '2022-02-11', 'Tinas', 0, 'INPROCESS', 'PREPARATION', 'StandardProduction', 'ACTIVE');
--INSERT INTO Production (startCoocking, endCoocking, alcoholicGradeDist1, alcoholicGradeDist2, volumeDistillation2, totalVolume, lot, idLotDetail, admissionDate, location, wastage, paymentStatus, productionStatus, typeProduction, status) VALUES ('2022-02-06', '2022-02-07', 1, 1, 1005, 1005, 'TR-02-CY-22', 4, '2022-02-13', 'Tinas', 0, 'INPROCESS', 'PREPARATION', 'StandardProduction', 'ACTIVE');

INSERT INTO Assay(name, allowableMinimum, maximumAllowable, unit, byDefault, status) VALUES ('Alcohol Volumen a 20 °C', 35.0, 55.0, '% Alc. Vol.', true, 'ACTIVE');
INSERT INTO Assay(name, allowableMinimum, maximumAllowable, unit, byDefault, status) VALUES ('Extracto seco', 0.0, 10.0, 'g/L', false, 'ACTIVE');
INSERT INTO Assay(name, allowableMinimum, maximumAllowable, unit, byDefault, status) VALUES ('Alcoholes superiores', 100.0, 500.0, 'mg/100 mL A.A.', false, 'ACTIVE');
INSERT INTO Assay(name, allowableMinimum, maximumAllowable, unit, byDefault, status) VALUES ('Metanol', 30.0, 300.0, 'mg/100 mL A.A.', true, 'ACTIVE');
INSERT INTO Assay(name, allowableMinimum, maximumAllowable, unit, byDefault, status) VALUES ('Furfural', 0.0, 5.0, 'mg/100 mL A.A.', true, 'ACTIVE');
INSERT INTO Assay(name, allowableMinimum, maximumAllowable, unit, byDefault, status) VALUES ('Aldehídos', 0.0, 40.0, 'mg/100 mL A.A.', false, 'ACTIVE');
INSERT INTO Assay(name, allowableMinimum, maximumAllowable, unit, byDefault, status) VALUES ('Plomo (Pb)', 0.0, 0.5, 'mg/L', false, 'ACTIVE');
INSERT INTO Assay(name, allowableMinimum, maximumAllowable, unit, byDefault, status) VALUES ('Arsénico (As)', 0.0, 0.5, 'mg/L', false, 'ACTIVE');

INSERT INTO Provider (name, telephone, email, logo, status) VALUES ('Nisl Arcu Ltd','tempus.non@yahoo.couk','(723) 225-0166', 'no_image.png', 'ACTIVE');
INSERT INTO Provider (name, telephone, email, logo, status) VALUES ('Vitae Diam LLC','vitae.risus.duis@outlook.net','(644) 654-9171', 'no_image.png', 'ACTIVE');
INSERT INTO Provider (name, telephone, email, logo, status) VALUES ('Ac Libero Nec LLC','amet.faucibus.ut@outlook.couk','(611) 107-7778', 'no_image.png', 'ACTIVE');
INSERT INTO Provider (name, telephone, email, logo, status) VALUES ('Velit Institute','eros@aol.couk','(278) 466-8506', 'no_image.png', 'ACTIVE');
INSERT INTO Provider (name, telephone, email, logo, status) VALUES ('Risus Institute','auctor.non@protonmail.ca','1-332-497-6882', 'no_image.png', 'ACTIVE');

INSERT INTO Customer (name, email, telephone, locality, municipality, state, status) VALUES ('Congue Incorporated','suspendisse.dui@hotmail.edu','1-685-314-3156','XinAn','Ben Tre','South Africa', 'ACTIVE');
INSERT INTO Customer (name, email, telephone, locality, municipality, state, status) VALUES ('Mauris Sit Associates','vivamus.non@google.net','(218) 743-6943','Van','Yunnan','Netherlands', 'ACTIVE');
INSERT INTO Customer (name, email, telephone, locality, municipality, state, status) VALUES ('Aliquam Ltd','elit@aol.com','(766) 866-9395','Azad Kashmir','Cairns','Austria', 'ACTIVE');

INSERT INTO InputCategory (name, nomenclature, status) VALUES ('Botellas de vidrio', 'BOTEV', 'ACTIVE');
INSERT INTO InputCategory (name, nomenclature, status) VALUES ('Botellas de plastico', 'BOTEP', 'ACTIVE');
INSERT INTO InputCategory (name, nomenclature, status) VALUES ('Tapones', 'TAPON', 'ACTIVE');
INSERT INTO InputCategory (name, nomenclature, status) VALUES ('Empaque', 'EMPAQ', 'ACTIVE');
INSERT INTO InputCategory (name, nomenclature, status) VALUES ('Codigos', 'CODIG', 'ACTIVE');
INSERT INTO InputCategory (name, nomenclature, status) VALUES ('Filtros', 'FILTR', 'ACTIVE');
INSERT INTO InputCategory (name, nomenclature, status) VALUES ('Etiquetas', 'ETIQU', 'ACTIVE');
INSERT INTO InputCategory (name, nomenclature, status) VALUES ('Cajas de carton', 'CAJAS', 'ACTIVE');
INSERT INTO InputCategory (name, nomenclature, status) VALUES ('Carton en general', 'CARTO', 'ACTIVE');
INSERT INTO InputCategory (name, nomenclature, status) VALUES ('Celocil', 'CELOC', 'ACTIVE');

INSERT INTO Input (code, name, specifications, stock, minimumStock, maximumStock, unit, idCategory, status) VALUES ('BOTEL-0001', 'BOTELLA BORDALESA 750ML', 'BORDALESA DELGADA 750 ML', 170, 100, 200, 'MILLILITER', 1, 'ACTIVE');
INSERT INTO Input (code, name, specifications, stock, minimumStock, maximumStock, unit, idCategory, status) VALUES ('BOTEL-0002', 'BOTELLA BORDALESA 700ML', 'BOTELLA BORDALESA 700 ML', 0, 100, 200, 'MILLILITER', 1, 'ACTIVE');
INSERT INTO Input (code, name, specifications, stock, minimumStock, maximumStock, unit, idCategory, status) VALUES ('BOTEL-0003', 'BOTELLA EUROPEA 750ML', 'EUROPEA 750 ML', 0, 100, 200, 'MILLILITER', 1, 'ACTIVE');
INSERT INTO Input (code, name, specifications, stock, minimumStock, maximumStock, unit, idCategory, status) VALUES ('TAPON-0001', 'TAPON BEIGE 31*10*20.5', 'TAPON ENBLOCK BEIGE 31X10X20.5', 200, 100, 200, 'PIECE', 3, 'ACTIVE');
INSERT INTO Input (code, name, specifications, stock, minimumStock, maximumStock, unit, idCategory, status) VALUES ('CELOC-0001', 'CELOCIL 5x9cm', 'CELOCIL TRANSPARENTE 5x9cm', 0, 100, 200, 'PIECE', 2, 'ACTIVE');
INSERT INTO Input (code, name, specifications, stock, minimumStock, maximumStock, unit, idCategory, status) VALUES ('CODIG-0001', 'CODIGO DE BARRA EL750MX', 'CÓDIGO DE BARRAS PARA ELOTE DE 750 ML NACIONAL', 300, 100, 200, 'PIECE', 7, 'ACTIVE');
INSERT INTO Input (code, name, specifications, stock, minimumStock, maximumStock, unit, idCategory, status) VALUES ('CARTO-0001', 'ESQUINEROS 1.82m', 'ESQUINEROS DE 1.82 METROS DE LARGO PARA TARIMA NACIONAL', 300, 100, 200, 'PIECE', 8, 'ACTIVE');
INSERT INTO Input (code, name, specifications, stock, minimumStock, maximumStock, unit, idCategory, status) VALUES ('FILTR-0001', 'FILTRO POLYESPUM', 'FILTRO POLYESPUM', 300, 100, 200, 'PIECE', 6, 'ACTIVE');

INSERT INTO ProviderInput (idProvider, idInput, deliveryTime, unitPrice) VALUES (1, 1, '10 dias habiles', 100);
INSERT INTO ProviderInput (idProvider, idInput, deliveryTime, unitPrice) VALUES (2, 2, 'Maximo dos meses', 100);
INSERT INTO ProviderInput (idProvider, idInput, deliveryTime, unitPrice) VALUES (3, 3, 'Si hay en existencia, entrega inmediata', 100);

INSERT INTO PurchaseInput (idProvider, idInput, amount, totalPrice) VALUES (1, 1, 10, 1000);
INSERT INTO PurchaseInput (idProvider, idInput, amount, totalPrice) VALUES (2, 2, 5, 500);

INSERT INTO adjustmentinput (status, dateofadjustment, amount, observations, reason, totalPrice, typeAdjustment, idInput, idProvider) VALUES ('ACTIVE', '2022-03-25', 8, 'El material se recibio en mal estado y no se reporto', 'Botellas rotas', 800, 'POSITIVE', 1, 1);

INSERT INTO departureinput (status, amount, dateOfDeparture, note, totalPrice, observations, idInput, idProvider) VALUES ('ACTIVE', 1200, '2022-03-31', 'N-035', 1200000, 'Salida para envasar el lote A-03-EL-22', 1, 1);

INSERT INTO calculation (status, time, alcoholicGradeDesired, idProduction) VALUES ('ACTIVE', '2022-03-18 01:41:10.0', 50.2, 1);
INSERT INTO calculation (status, time, alcoholicGradeDesired, idProduction) VALUES ('ACTIVE', '2022-03-18 01:41:10.0', 49.32, 2);

INSERT INTO rowcalculation (status, alcohol, aldehydes, furfurol, higherAlcohols, methanol, name, observations, plomo, volume, idCalculation) VALUES ('ACTIVE', 53.24, 20, 2.44, 424, 146, 'J-01-TBCS-22 Cuerpo', NULL, 0, 1200, 1);
INSERT INTO rowcalculation (status, alcohol, aldehydes, furfurol, higherAlcohols, methanol, name, observations, plomo, volume, idCalculation) VALUES ('ACTIVE', 8.24, 12.32, 0.23, 173, 244, 'Agua + Colas', NULL, 0, 50, 1);
INSERT INTO rowcalculation (status, alcohol, aldehydes, furfurol, higherAlcohols, methanol, name, observations, plomo, volume, idCalculation) VALUES('ACTIVE', 57.24, 13, 2, 320, 40, 'TR-01-SN-22 Cuerpo', NULL, 0, 1120, 2);
INSERT INTO rowcalculation (status, alcohol, aldehydes, furfurol, higherAlcohols, methanol, name, observations, plomo, volume, idCalculation) VALUES('ACTIVE', 12.24, 19, 5, 420, 7, 'Agua + Cola', NULL, 0, 150, 2);
INSERT INTO rowcalculation (status, alcohol, aldehydes, furfurol, higherAlcohols, methanol, name, observations, plomo, volume, idCalculation) VALUES('ACTIVE', 12.24, 19, 5, 420, 7, 'Agua + Cola', NULL, 0, 50, 2);

INSERT INTO productcategory (status, description, name, idProducer) VALUES ('ACTIVE', 'Mezcal proveniente de mas de un tipo de maguey', 'Ensamble Emigdio', 4);
INSERT INTO productcategory (status, description, name, idProducer) VALUES ('ACTIVE', 'Mezcal destilado con maiz tostado', 'Elote Aquilino', 1);

INSERT INTO product(status, capacity, description, mezcalCategory, mezcalClass, name, productionCost, salePrice, trademark, idCategory) VALUES ('ACTIVE', 750, 'Elote 750ml nacional', 'CRAFT', 'YOUNG', 'Elote 750ml MX', 350.56, 750.50, 'VAGO', 2);

INSERT INTO productinput (status, quantity, idInput, idProduct) VALUES ('ACTIVE', 1, 4, 1);
--INSERT INTO productinput (status, quantity, idInput, idProduct) VALUES ('ACTIVE', 1, 5, 1);
INSERT INTO productinput (status, quantity, idInput, idProduct) VALUES ('ACTIVE', 1, 6, 1);



















