SELECT *  from uber_users; where id=7;
select from drivers where id=6;

SELECT * from wallet;
SELECT setval('uber_users_id_seq', (SELECT MAX(id) FROM uber_users));             --- now on signup id is taken from current max which is present in db => so dublicacy error
