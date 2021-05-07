INSERT INTO users
    (active, email, name, password)
VALUES
    (true, 'aessam72@gmail.com', "Ahmed Essam", '123456');

INSERT INTO devices
    (device_unique_id, meta_tag, os, type, user_user_id)
VALUES
    (uuid(), 'meta_tag1', 'ios', 'iPhone', 1),
    (uuid(), 'meta_tag2', 'android', 'Samsung', 1);

INSERT INTO e_sims
    (iccid, imsi, activation_code, e_id, device_device_id)
VALUES
    ('iccid1', 'imsi1', '0000', uuid(), 1),
    ('iccid2', 'imsi2', '0001', uuid(), 1),
    ('iccid3', 'imsi3', '0002', uuid(), 2);