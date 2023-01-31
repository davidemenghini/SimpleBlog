INSERT INTO simple_blog.post (id,data_text,data_img,id_author,title_text) VALUES (1,'Test','null',1,x'6369616f2071756573746120c3a820756e612070726f76612e');
INSERT INTO simple_blog.post (id,data_text,data_img,id_author,title_text) VALUES (2,'Test1','null',1,'test1.');
INSERT INTO simple_blog.default_user ( id,data_img,psw, username, role_user,enabled) VALUES (1,'null','prova1','utente1','user',0);
INSERT INTO simple_blog.default_user ( id,data_img,psw,username,role_user,enabled) VALUES (2,'null','prova2','utente2','user',0);
INSERT INTO simple_blog.comment (id, dislike_number, like_number, text_comment,ida,idp) VALUES (1,1,1,x'636f6d6d656e746f31',1,1);
INSERT INTO simple_blog.comment (id, dislike_number, like_number, text_comment,ida,idp) VALUES (2,0,0,x'636f6d6d656e746f32',1,1);
