INSERT INTO simple_blog.Post (id,data_text,data_img,id_author,title_text,like_number,dislike_number) VALUES (1,'Test','',1,x'6369616f2071756573746120c3a820756e612070726f76612e',1,1);
INSERT INTO simple_blog.Post (id,data_text,data_img,id_author,title_text,like_number,dislike_number) VALUES (2,'Test1','',1,'test1.',0,0);
INSERT INTO simple_blog.default_user ( id,data_img,psw, username, role_user,enabled,salt,session_id,expiration_time) VALUES (1,'null','683ca0111aee25516ac67f7f3d8d2717f6c075940ec2ceaa8f4d251f491d9adf','utente1','user',0,'pippo',null,null);
INSERT INTO simple_blog.default_user ( id,data_img,psw,username,role_user,enabled,salt,session_id,expiration_time) VALUES (2,'null','d2dd9de29adf6e60b5e9bb97f1e7cce4b8d99f53c1da9eddc767b59d3c817d92','utente2','user',0,'pluto',null,null);
INSERT INTO simple_blog.Comment (id, dislike_number, like_number, text_comment,ida,idp) VALUES (1,0,1,x'636f6d6d656e746f31',1,1);
INSERT INTO simple_blog.Comment (id, dislike_number, like_number, text_comment,ida,idp) VALUES (2,1,0,x'636f6d6d656e746f32',1,1);
INSERT INTO simple_blog.like_post(id,id_user,id_post) VALUES (1,1,1);
INSERT INTO simple_blog.dislike_post(id,id_user,id_post) VALUES (2,2,1);
INSERT INTO simple_blog.like_comment(id,id_user,id_comment) VALUES (1,1,1);
INSERT INTO simple_blog.dislike_comment(id,id_user,id_comment) VALUES (1,1,2);