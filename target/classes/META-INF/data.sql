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

INSERT INTO Production (startCoocking, endCoocking, alcoholicGradeDist1, alcoholicGradeDist2, volumeDistillation2, totalVolume, lot, idLotDetail, admissionDate, location, wastage, paymentStatus, productionStatus, typeProduction, status) VALUES ('2022-02-01', '2022-02-02', 1.25, 1, 1200, 1200, 'J-01-TBCS-22', 1, '2022-02-09', 'Tinas', 0, 'APPROBAL', 'PREPARATION', 'StandardProduction', 'ACTIVE');
INSERT INTO Production (startCoocking, endCoocking, alcoholicGradeDist1, alcoholicGradeDist2, volumeDistillation2, totalVolume, lot, idLotDetail, admissionDate, location, wastage, paymentStatus, productionStatus, typeProduction, status) VALUES ('2022-02-03', '2022-02-04', 2.75, 1.25, 1120, 1120, 'TR-01-SN-22', 2, '2022-02-10', 'Tinas', 0, 'APPROBAL', 'PRELIMINARYANALYSIS', 'StandardProduction', 'ACTIVE');
INSERT INTO Production (startCoocking, endCoocking, alcoholicGradeDist1, alcoholicGradeDist2, volumeDistillation2, totalVolume, lot, idLotDetail, admissionDate, location, wastage, paymentStatus, productionStatus, typeProduction, status) VALUES ('2022-02-04', '2022-02-05', 1.75, 2, 1115, 1115, 'J-02-MC-22', 3, '2022-02-11', 'Tinas', 0, 'INPROCESS', 'REPROBATE', 'StandardProduction', 'ACTIVE');
INSERT INTO Production (startCoocking, endCoocking, alcoholicGradeDist1, alcoholicGradeDist2, volumeDistillation2, totalVolume, lot, idLotDetail, admissionDate, location, wastage, paymentStatus, productionStatus, typeProduction, status) VALUES ('2022-02-06', '2022-02-07', 1, 1, 1005, 1005, 'TR-02-CY-22', 4, '2022-02-13', 'Tinas', 0, 'INPROCESS', 'INBULK', 'StandardProduction', 'ACTIVE');

