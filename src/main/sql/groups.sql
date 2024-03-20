CREATE TABLE connect_groups (
                                       groupid serial4 NOT NULL,
                                       groupname varchar(20) NOT NULL,
                                       categoryid int4 NOT NULL,
                                       groupcode varchar(10) NOT NULL,
                                       groupowner int4 NOT NULL,
                                       CONSTRAINT connect_groups_pkey PRIMARY KEY (groupid)
);

CREATE TABLE group_members (
                                      userid int4 NOT NULL,
                                      groupid int4 NOT NULL,
                                      CONSTRAINT groupmember_pkey PRIMARY KEY (userid, groupid)
);

ALTER TABLE connect_groups  ADD CONSTRAINT groups_user_fkey FOREIGN KEY (groupowner) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE group_members ADD CONSTRAINT member_user_fkey FOREIGN KEY (userid) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE group_members ADD CONSTRAINT member_group_fkey FOREIGN KEY (groupid) REFERENCES connect_groups(groupid) ON DELETE CASCADE;