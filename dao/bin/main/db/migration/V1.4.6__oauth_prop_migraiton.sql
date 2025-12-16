insert into oauth_app_property (oauth_app_id, name, value)
select id, 'auth_host', auth_url
from oauth_app
where auth_url is not null;

insert into oauth_app_property (oauth_app_id, name, value)
select id, 'resource_host', resource_url
from oauth_app
where resource_url is not null;

insert into oauth_app_property (oauth_app_id, name, value)
select id, 'client_id', client_id
from oauth_app
where client_id is not null;

insert into oauth_app_property (oauth_app_id, name, value)
select id, 'client_secret', client_secret
from oauth_app
where client_secret is not null;

alter table oauth_app
    drop column auth_url;
alter table oauth_app
    drop column resource_url;
alter table oauth_app
    drop column client_id;
alter table oauth_app
    drop column client_secret;
alter table oauth_app
    drop column scope;
