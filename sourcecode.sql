select id, text
from Post
where user_id in (select id
             from User
             where id in (select user_id
                          from Topic
                          where name like '%Scala%')
                   and email like '%@gmail.com')
