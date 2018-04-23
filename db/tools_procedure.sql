DELIMITER $
DROP PROCEDURE IF EXISTS usp_location_getLocationIdByGeo;
CREATE PROCEDURE usp_location_getLocationIdByGeo(OUT loca_id BIGINT, IN latitude double, IN longitude DOUBLE)  
BEGIN
    DECLARE ranger,lngr, min_lat,max_lat,min_lng,max_lng DOUBLE; 
    DECLARE lens INT DEFAULT 20;
    myloop: LOOP
        SET ranger = 180 / PI() * lens / 6372.797;
        SET lngr = ranger / COS(latitude * PI() / 180.0);
        SET max_lat = latitude + ranger;
        SET min_lat = latitude - ranger;
        SET max_lng = longitude + lngR;
        SET min_lng = longitude - lngR;
            
    SELECT id INTO loca_id 
    FROM (
        SELECT id,
               (6372.797 * ACOS(COS( RADIANS(latitude)) * COS(RADIANS(`lat`)) * COS(RADIANS(`long`) - RADIANS(longitude)) + SIN(RADIANS(latitude)) * SIN(RADIANS(`lat`)))) AS distance
        FROM `etarget_report`.`meta_location` 
        WHERE ((`lat` BETWEEN min_lat AND max_lat) AND (`long` BETWEEN min_lng AND max_lng)) 
        ORDER BY distance LIMIT 1
    ) AS temp;
        IF loca_id IS NOT NULL THEN  
            LEAVE myloop;
        ELSEIF lens > 100 THEN
            SET loca_id = 0;
            LEAVE myloop;
        END IF;     
            SET lens = lens * 2;
    END LOOP myloop;
END;$
DELIMITER ;