DROP TABLE IF EXISTS simple_blog.post;
DROP TABLE IF EXISTS simple_blog.default_user;
DROP TABLE IF EXISTS simple_blog.comment;

CREATE TABLE IF NOT EXISTS Comment(
                                      id int primary key auto_increment,
                                      text_comment blob,
                                      like_number int,
                                      dislike_number int
) CHARACTER SET latin1;
CREATE TABLE IF NOT EXISTS Post(
                                   id int primary key ,
                                   data_text blob ,
                                   data_img blob,
                                   id_author int,
                                   title_text blob
) CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS default_user(
                                          id int primary key,
                                          data_img blob,
                                          username tinytext,
                                          psw tinyblob
) CHARACTER SET latin1;