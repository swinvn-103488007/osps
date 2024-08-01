INSERT INTO parking_slot (available)
VALUES (b'1');

DELIMITER $$

CREATE PROCEDURE generate_parking_slots()
BEGIN
  DECLARE i INT DEFAULT 1;

  WHILE i <= 150 DO
    INSERT INTO parking_slot (available) VALUES (b'1');
    SET i = i + 1;
  END WHILE;
END$$

DELIMITER ;

CALL generate_parking_slots();
DROP PROCEDURE generate_parking_slots;
