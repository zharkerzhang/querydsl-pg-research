

insert into verb(t_id, id, name, attributes) values ((select uuid_generate_v1()),'edit','edit','{"att":"bb"}');

insert into resource(t_id, id, asset, uri, attributes, name) values
((select uuid_generate_v1()),'test resource','asset','urillll','{"attr":"22"}','test resource');

insert into resource(t_id, id, asset, uri, attributes, name) values
((select uuid_generate_v1()),'test resource2','asset','urillll','{"attr":"22"}','test resource2');

insert into resource_verb_relation(t_id, resource_id, verb_id) values
((select uuid_generate_v1()),
 (select t_id from resource where id = 'test resource2' limit 1),
 (select t_id from verb where id = 'edit' limit 1)
);

insert into resource(t_id, id, asset, uri, attributes, name) values
((select uuid_generate_v1()),'test resource3','asset','urillll3','{"attr":"22"}','test resource3');

insert into resource_verb_relation(t_id, resource_id, verb_id) values
((select uuid_generate_v1()),
 (select t_id from resource where id = 'test resource3' limit 1),
 (select t_id from verb where id = 'edit' limit 1)
);