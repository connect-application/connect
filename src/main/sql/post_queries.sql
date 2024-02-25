create table connect_user(userId serial PRIMARY KEY)--placeholder for now, actual table to be created by Joban

create table post(postId serial PRIMARY KEY, userId INT NOT NULL, activityId INT, postText TEXT, FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE);

create table post_comment(postId INT NOT NULL, commentText TEXT, FOREIGN KEY (postId) REFERENCES post(postId) ON DELETE CASCADE)

CREATE TABLE ACTIVITIES (
                            postid SERIAL primary key,
                            categoryid int NOT NULL,
                            statusid int NOT NULL,
                            starttime timestamp NOT NULL,
                            endtime timestamp,
                            isrecurring bool NOT NULL
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
ALTER TABLE public.activities ADD CONSTRAINT activities_postId_fkey FOREIGN KEY (postId) REFERENCES post(postId) ON DELETE CASCADE;

ALTER TABLE public.post ADD CONSTRAINT post_postid_fkey FOREIGN KEY (postId) REFERENCES activities(postId) ON DELETE CASCADE;
ALTER TABLE public.attachments ADD CONSTRAINT attachments_postId_fkey FOREIGN KEY (postId) REFERENCES post(postId) ON DELETE CASCADE;

ALTER TABLE public.likes ADD CONSTRAINT likes_postId_fkey FOREIGN KEY (postId) REFERENCES post(postId) ON DELETE CASCADE;
ALTER TABLE public.likes ADD CONSTRAINT likes_user_fkey FOREIGN KEY (likedby) REFERENCES connect_user(userId) ON DELETE CASCADE;

ALTER TABLE post_comment ADD COLUMN commentId SERIAL PRIMARY KEY;
ALTER TABLE post ADD COLUMN createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE post ADD COLUMN isPublic BOOLEAN DEFAULT TRUE;
ALTER TABLE post DROP COLUMN activityId;

ALTER TABLE post_comment ADD COLUMN userId INT NOT NULL,
    ADD CONSTRAINT fk_userId FOREIGN KEY (userId) REFERENCES users(id);