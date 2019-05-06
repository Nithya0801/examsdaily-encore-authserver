
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('fooClientIdPassword', '$2a$10$8Eckl5ktkxuWFLko39AqtugrVNagwJno6b9TyUiFCLNG0ORVTRdWy', 'foo,read,write',
	'password,authorization_code,refresh_token', null, null, 86400, 86400, null, true);
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('sampleClientId', '$2a$10$8Eckl5ktkxuWFLko39AqtugrVNagwJno6b9TyUiFCLNG0ORVTRdWy', 'read,write,foo,bar',
	'implicit', null, null, 86400, 86400, null, false);
INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('barClientIdPassword', '$2a$10$8Eckl5ktkxuWFLko39AqtugrVNagwJno6b9TyUiFCLNG0ORVTRdWy', 'bar,read,write',
	'password,authorization_code,refresh_token', null, null, 86400, 86400, null, true);
	
