INSERT INTO user (active, created, updated, confirmationcode, email, enabled, firstname, lastname, password, role) VALUES (true, '2017-02-27 18:16:15', null, '4b737bb4-b0dc-4ec2-8215-bf64f4ca7b71', 'admin@gmail.com', true, 'admin', 'admin', '$2a$10$jzbI37cBk/Yx.5Y6uwB1X.FUbbNBdij9Eq3Ef2UEiUzuc3VzjdPmK', 'ADMIN');
INSERT INTO user (active, created, updated, confirmationcode, email, enabled, firstname, lastname, password, role) VALUES (true, '2017-02-27 18:17:09', null, null, 'sys@gmail.com', true, 'sys', 'sys', '$2a$10$TgoFsWUBYuItW6LHLb9Mv.yoeACx6CvKcA8OwVyGD3NtaPBAzSqlC', 'SYSTEM_MANAGER');
INSERT INTO user (active, created, updated, confirmationcode, email, enabled, firstname, lastname, password, role) VALUES (true, '2017-02-27 18:19:02', null, null, 'res@gmail.com', true, 'res', 'res', '$2a$10$zKaAHkXjgh01B5E3PFXVS.5711Q0S/gHc5FWFO/yk6Fcm2Gq4OFnG', 'MANAGER');
INSERT INTO user (active, created, updated, confirmationcode, email, enabled, firstname, lastname, password, role) VALUES (true, '2017-02-27 18:21:02', null, null, 'sel@gmail.com', true, 'sel', 'sel', '$2a$10$ydvqdW4kp9pwBWk9dyzZOOERIci8a.ajJ4SLxCxQR9eqi3Lh/QtSS', 'SELLER');
INSERT INTO user (active, created, updated, confirmationcode, email, enabled, firstname, lastname, password, role) VALUES (true, '2017-02-27 21:38:51', null, '0756b6de-6040-4e60-b771-9f2d3bb3580c', 'aleksandar.kahriman@gmail.com', true, 'Aleksandar', 'Kahriman', '$2a$10$qhwBd0pPF00aNU1kmbqCz.Wihj3mhjP.B2rWZC/.2r4lZZmQZALFC', 'GUEST');
INSERT INTO user (active, created, updated, confirmationcode, email, enabled, firstname, lastname, password, role) VALUES (true, '2017-02-27 21:39:06', null, '05bb039b-a176-4e36-a130-5eef3f2bd81a', 'akahriman@execom.eu', true, 'Aleksandar', 'Kahriman', '$2a$10$cKTGQ6D82GJjdks6pkGVQ.gtOPx1BqTbWspAGkDwPxdNzmIWtwLZK', 'GUEST');
INSERT INTO user (active, created, updated, confirmationcode, email, enabled, firstname, lastname, password, role) VALUES (true, '2017-02-27 23:38:22', null, '373e7bbf-7d0d-4937-9348-d6864fcb2a99', 'alek@gmail.com', true, 'Alek', 'Radosevic', '$2a$10$u3o2TuM8rjkOfxMfJFd5y.5SesqpGfx0iP9nIoSZQJjelJIgU21km', 'GUEST');
INSERT INTO user (active, created, updated, confirmationcode, email, enabled, firstname, lastname, password, role) VALUES (true, '2017-02-27 23:38:48', null, '7f2b8e66-c060-48b8-bf73-768a6cd31515', 'bojan@gmail.com', true, 'Boki', 'Boki', '$2a$10$vBEniicn.Zu7i0vhylhBdeV1flKVwvh2gpgPYS73q6677aQu.tijC', 'GUEST');

INSERT INTO guest (id) VALUES (1);
INSERT INTO guest (id) VALUES (5);
INSERT INTO guest (id) VALUES (6);
INSERT INTO guest (id) VALUES (7);
INSERT INTO guest (id) VALUES (8);

INSERT INTO friendship (active, created, updated, status, originator_id, recipient_id) VALUES (true, '2017-02-27 23:41:27', '2017-02-27 23:41:47', 'ACCEPTED', 5, 6);
INSERT INTO friendship (active, created, updated, status, originator_id, recipient_id) VALUES (true, '2017-02-27 23:41:32', '2017-02-27 23:42:49', 'ACCEPTED', 5, 8);
INSERT INTO friendship (active, created, updated, status, originator_id, recipient_id) VALUES (true, '2017-02-27 23:42:15', null, 'PENDING', 8, 6);
INSERT INTO friendship (active, created, updated, status, originator_id, recipient_id) VALUES (true, '2017-02-27 23:42:37', null, 'PENDING', 8, 7);