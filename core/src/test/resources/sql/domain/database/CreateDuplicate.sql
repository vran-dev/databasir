INSERT INTO databasir.database_type (id, database_type, icon, description, jdbc_driver_file_url, jdbc_driver_class_name,
                                     jdbc_protocol, url_pattern)
VALUES (-1000, 'ut-mysql', '', 'system default mysql', 'N/A', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql',
        '{{jdbc.protocol}}://{{db.url}}/{{db.name}}');
