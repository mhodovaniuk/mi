select topic_name from topics
where id in (select id
             from posts
             where author_id in (select author_id
                                 from topics
                                 where topic_name='Scala'))