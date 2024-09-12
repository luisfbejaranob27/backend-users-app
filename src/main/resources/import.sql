INSERT INTO MODULES (name , base_path) VALUES ('MODULE' , '/modules');
INSERT INTO MODULES (name , base_path) VALUES ('ROLE' , '/roles');
INSERT INTO MODULES (name , base_path) VALUES ('OPERATION' , '/operations');
INSERT INTO MODULES (name , base_path) VALUES ('PERMISSION' , '/permissions');
INSERT INTO MODULES (name , base_path) VALUES ('AUTHENTICATION' , '/auth');
INSERT INTO MODULES (name , base_path) VALUES ('USER' , '/users');


INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('LIST_MODULES' , '' , 'GET' , false , 1);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('FIND_MODULE_BY_ID' , '/id/[0-9]*' , 'GET' , false , 1);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('FIND_MODULE_BY_NAME' , '/name/[a-zA-Z_ ]*' , 'GET' , false , 1);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('CREATE_MODULE' , '' , 'POST' , false , 1);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('UPDATE_MODULE' , '' , 'PUT' , false , 1);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('DELETE_MODULE' , '/[0-9]*' , 'DELETE' , false , 1);

INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('LIST_ROLES' , '' , 'GET' , false , 2);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('FIND_ROLE_BY_ID' , '/id/[0-9]*' , 'GET' , false , 2);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('FIND_ROLE_BY_NAME' , '/name/[a-zA-Z_ ]*' , 'GET' , false , 2);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('CREATE_ROLE' , '' , 'POST' , false , 2);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('UPDATE_ROLE' , '' , 'PUT' , false , 2);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('DELETE_ROLE' , '/[0-9]*' , 'DELETE' , false , 2);

INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('LIST_OPERATIONS' , '' , 'GET' , false , 3);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('FIND_OPERATION_BY_ID' , '/id/[0-9]*' , 'GET' , false , 3);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('FIND_OPERATION_BY_NAME' , '/name/[a-zA-Z_ ]*' , 'GET' , false , 3);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('CREATE_OPERATION' , '' , 'POST' , false , 3);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('UPDATE_OPERATION' , '' , 'PUT' , false , 3);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('DELETE_OPERATION' , '/[0-9]*' , 'DELETE' , false , 3);

INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('LIST_PERMISSIONS' , '' , 'GET' , false , 4);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('FIND_PERMISSION' , '/[0-9]*' , 'GET' , false , 4);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('CREATE_PERMISSION' , '' , 'POST' , false , 4);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('DELETE_PERMISSION' , '/[0-9]*' , 'DELETE' , false , 4);

INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('AUTHENTICATE' , '/authenticate' , 'POST' , true , 5);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('REFRESH_TOKEN' , '/refresh-token' , 'POST' , true , 5);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('VALIDATE_TOKEN' , '/validate-token' , 'GET' , true , 5);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('LOGOUT' , '/logout/.*' , 'GET' , true , 5);

INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('REGISTER' , '/register' , 'POST' , true , 6);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('READ_PROFILE' , '/profile' , 'GET' , false , 6);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('READ_ALL_USERS' , '' , 'GET' , false , 6);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('READ_USER_BY_ID' , '/.*' , 'GET' , false , 6);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('READ_USER_BY_USERNAME' , '/username/.*' , 'GET' , false , 6);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('EXIST_USER' , '/exists/.*/.*' , 'GET' , true , 6);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('CREATE_USER' , '' , 'POST' , false , 6);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('UPDATE_USER' , '/.*' , 'PUT' , false , 6);
INSERT INTO OPERATIONS (name, path , method , permit_all , module_id) VALUES ('DELETE_USER' , '/.*' , 'DELETE' , false , 6);


INSERT INTO ROLES (name) VALUES ('ADMINISTRATOR');
INSERT INTO ROLES (name) VALUES ('HUMAN_RESOURCES');
INSERT INTO ROLES (name) VALUES ('USER');


INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 1);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 2);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 3);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 4);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 5);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 6);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 7);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 8);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 9);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 10);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 11);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 12);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 13);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 14);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 15);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 16);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 17);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 18);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 19);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 20);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 21);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 22);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 23);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 24);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 25);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 26);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 27);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 28);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 29);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 30);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 31);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 32);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 33);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 34);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (1 , 35);


INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (2 , 23);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (2 , 24);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (2 , 25);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (2 , 26);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (2 , 27);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (2 , 28);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (2 , 29);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (2 , 30);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (2 , 31);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (2 , 32);

INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (3 , 23);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (3 , 24);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (3 , 25);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (3 , 26);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (3 , 27);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (3 , 28);
INSERT INTO GRANTED_PERMISSIONS (role_id , operation_id) VALUES (3 , 32);

INSERT INTO users (id, name, username, password, email, role_id, create_at) VALUES ('6b27e2dc-9afb-36f3-a433-add532882f70','Luis Ferley Bejarano Buritica', 'luisfbejaranob', '$2a$12$HYwD9bIF0BZkXaE4j5jZ7O/V233ILTxHVLlSR.674B3m4XMrxLj3q', 'luisfbejaranob@outlook.com', 1, now());