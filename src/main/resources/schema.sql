DROP TABLE IF EXISTS simple_blog.post;
DROP TABLE IF EXISTS simple_blog.default_user;
DROP TABLE IF EXISTS simple_blog.comment;
DROP TABLE IF EXISTS simple_blog.my_user_csrf_token;

CREATE TABLE IF NOT EXISTS Comment(
                                      id int primary key auto_increment,
                                      text_comment blob,
                                      like_number int,
                                      dislike_number int,
                                      ida int,
                                      idp int
) CHARACTER SET utf8;
CREATE TABLE IF NOT EXISTS Post(
                                   id int primary key ,
                                   data_text blob ,
                                   data_img blob,
                                   id_author int,
                                   title_text blob,
                                   like_number int,
                                   dislike_number int
) CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS default_user(
                                          id int primary key,
                                          data_img blob,
                                          username varchar(45),
                                          psw varchar(64),
                                          role_user varchar(45),
                                          enabled tinyint,
                                          salt varchar(64),
                                          session_id varchar(128),
                                          expiration_time datetime
) CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS my_user_csrf_token(
    idu int primary key,
    token varchar(128)
) CHARACTER SET utf8;


CREATE TABLE IF NOT EXISTS like_post(
    id int primary key,
    id_user int,
    id_post int
)character set utf8;

CREATE TABLE IF NOT EXISTS dislike_post(
    id int primary key,
    id_user int,
    id_post int
)character set utf8;

CREATE TABLE IF NOT EXISTS like_comment(
                                        id int primary key,
                                        id_user int,
                                        id_comment int
)character set utf8;

CREATE TABLE IF NOT EXISTS dislike_comment(
                                           id int primary key,
                                           id_user int,
                                           id_comment int
)character set utf8;

