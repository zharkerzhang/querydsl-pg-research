insert into "user"(t_id, id, status)
values ('c79f77ed-5e07-4153-bf2b-b53befbc6fac', 'test_external_user_id', 'external_user');

insert into "user"(t_id, id, status)
values ('c79f77ed-5e07-4153-bf2b-b53befbc6fad', 'test_user_id', 'external_user');


--for search test
insert into "user"(t_id, id, status, customized_attributes)
values ('c79f77ed-5e07-4153-bf2b-b53befbc6fc1', 'test_user_id4', 'external_user',
        '{
          "a": 2333,
          "b": [
            444,
            555,
            {
              "c": "d"
            }
          ],
          "e": true,
          "f": "hhhh"
        }');

--for test string_array query
--Driver, ServiceUser, VehicleOwner, CommonUser
insert into "user"(t_id, id, status, user_types, num_arr)
values ('c79f77ed-5e07-4153-bf2b-b53befbc6ca0', 'test_user_types_1', 'external_user', array ['Driver'],
        array [1,2,3,4]);

insert into "user"(t_id, id, status, user_types, num_arr)
values ('c79f77ed-5e07-4153-bf2b-b53befbc6ca1', 'test_user_types_2', 'external_user', array ['ServiceUser'],
        array [4,5,6,7]);

insert into "user"(t_id, id, status, user_types, num_arr)
values ('c79f77ed-5e07-4153-bf2b-b53befbc6ca2', 'test_user_types_3', 'external_user', array ['VehicleOwner'],
        array [7,8,9,0]);

insert into "user"(t_id, id, status, user_types, num_arr)
values ('c79f77ed-5e07-4153-bf2b-b53befbc6ca3', 'test_user_types_4', 'external_user', array ['CommonUser'],
        array [0, -1, -2,1]);


insert into "user"(t_id, id, gender,age,age_range)
values ('c79f77ed-5e07-4153-bf2b-b53befbc6ca4', 'test_enum_1', 'male',11,'12_CHILD');
