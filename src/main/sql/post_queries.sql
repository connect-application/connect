create table connect_user(userId serial PRIMARY KEY)--placeholder for now, actual table to be created by Joban

create table post(postId serial PRIMARY KEY, userId INT NOT NULL, activityId INT, postText TEXT, FOREIGN KEY (userId) REFERENCES connect_user(userId) ON DELETE CASCADE);

create table post_comment(postId INT NOT NULL, commentText TEXT, FOREIGN KEY (postId) REFERENCES post(postId) ON DELETE CASCADE)

CREATE TABLE ACTIVITIES (
                            ACTID SERIAL primary key,
                            CATEGORYID int NOT NULL,
                            STATUSID int NOT NULL,
                            START_TIME timestamp NOT NULL,
                            END_TIME timestamp,
                            ISRECURRING bool NOT NULL
);


CREATE TABLE LIKES (
                       POSTID int NOT NULL,
                       LIKEDBY int NOT NULL,
                       PRIMARY KEY (POSTID, LIKEDBY)
);

CREATE TABLE ATTACHMENTS (
                             ATTACHMENTID SERIAL primary key ,
                             POSTID int NOT NULL,
                             FILE bytea
);

ALTER TABLE public.post ADD CONSTRAINT post_actid_fkey FOREIGN KEY (activityId) REFERENCES activities(actid) ON DELETE CASCADE;
ALTER TABLE public.attachments ADD CONSTRAINT attachments_postId_fkey FOREIGN KEY (postId) REFERENCES post(postId) ON DELETE CASCADE;

ALTER TABLE public.likes ADD CONSTRAINT likes_postId_fkey FOREIGN KEY (postId) REFERENCES post(postId) ON DELETE CASCADE;
ALTER TABLE public.likes ADD CONSTRAINT likes_user_fkey FOREIGN KEY (likedby) REFERENCES connect_user(userId) ON DELETE CASCADE;