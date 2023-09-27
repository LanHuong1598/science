
drop table if exists role cascade;
create table role
(
    id                varchar(50) primary key,
    name              text                     not null,
    description       text,
    authorities       text                     not null,
    is_default        Boolean                  not null default false,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);

drop table if exists users cascade;
CREATE TABLE users
(
    id                serial primary key,
    password          text                     not null,
    username          varchar(50)              not null unique,
    email             text,
    phone             text,
    enabled           boolean default false,
    hidden            boolean default false,

    fullname          text                     not null,
    nationality       text,
    race              text,
    gender            varchar(5),
    birthdate         timestamp with time zone,
    address           text,
    district          text,
    city_province     text,
    country           text,

    degree            text,
    title             text,
    affiliationid     integer              not null references affiliation (id),
	authorid		varchar(50)       ,
	groupid         varchar(50),
    specialization    text,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer default 0,
    last_updated_time timestamp with time zone not null,
    last_updated_id   integer default 0,
    voided            boolean default false,
    voided_time       timestamp with time zone,
    voided_by         integer default 0,
    void_reason       text
);

drop table if exists detail_user_role cascade;
CREATE TABLE detail_user_role
(
    user_id integer     NOT NULL references users (id),
    role_id varchar(50) NOT NULL references role (id),
    CONSTRAINT detail_user_role_pkey PRIMARY KEY (user_id, role_id)
);


drop table if exists affiliation cascade;
create table affiliation
(
    id                serial primary key,
    name              text                     not null,
    description       text,
    contact_phone     varchar(20),
    contact_email     text,
    country           text,
    level             integer,
    parent_id         integer,
    priority          integer ,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer default 0,
    last_updated_time timestamp with time zone not null,
    last_updated_id   integer default 0,
    voided            boolean default false,
    voided_time       timestamp with time zone,
    voided_by         integer default 0,
    void_reason       text
);

drop table if exists source cascade;
create table source
(
    id                serial primary key,
    name              text                     not null,
    description       text,
    cite_score        float,
    sjr               float,
    snip              float,
    issn              varchar(50),
    publisher         text,
    subject_area      text,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer default 0,
    last_updated_time timestamp with time zone not null,
    last_updated_id   integer default 0,
    voided            boolean default false,
    voided_time       timestamp with time zone,
    voided_by         integer default 0,
    void_reason       text
);

drop table if exists major cascade;
CREATE TABLE major
(
    id                serial primary key,
    name              text                     not null,
    description       text,
    level             integer,
    parent_id         integer,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer default 0,
    last_updated_time timestamp with time zone not null,
    last_updated_id   integer default 0,
    voided            boolean default false,
    voided_time       timestamp with time zone,
    voided_by         integer default 0,
    void_reason       text
);

drop table if exists degree cascade;
CREATE TABLE degree
(
    id                serial primary key,
    name              text                     not null,
    description       text,
    type              varchar (50),

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer default 0,
    last_updated_time timestamp with time zone not null,
    last_updated_id   integer default 0,
    voided            boolean default false,
    voided_time       timestamp with time zone,
    voided_by         integer default 0,
    void_reason       text
);

drop table if exists research_group cascade;
create table research_group
(
    id                serial primary key,
    name              text                     not null,
    description       text,
    research_field    text,
    isi_number        integer ,
    scopus_number     integer ,
    other_number      integer ,
    national_number   integer ,
    invention_number  integer ,
    useful_solution_number integer,

    contact_phone     text,
    contact_email     text,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer default 0,
    last_updated_time timestamp with time zone not null,
    last_updated_id   integer default 0,
    voided            boolean default false,
    voided_time       timestamp with time zone,
    voided_by         integer default 0,
    void_reason       text
);


drop table if exists author cascade;
CREATE TABLE author
(
    id                serial primary key,
    fullname          text                     ,
    degree_id            integer,
    academic_rank_id     integer,
    gender            varchar(5),
    birthdate         timestamp with time zone,
    scientific_title  text,
    affiliation_id    integer ,
    major_id          integer ,
    orcid_id          text ,
    link_google_scholar  text,
    link_research_gate   text,
    depth_research       text,
    phone                text,
    email                text,

    isi_number        integer ,
    scopus_number     integer ,
    other_number      integer ,
    national_number   integer ,
    invention_number  integer ,
    useful_solution_number integer,

    is_mta             boolean default true,
    verified          boolean default false ,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer default 0,
    last_updated_time timestamp with time zone not null,
    last_updated_id   integer default 0,
    voided            boolean default false,
    voided_time       timestamp with time zone,
    voided_by         integer default 0,
    void_reason       text
);

drop table if exists email cascade;
create table email
(
    id                serial primary key,
    name              text,
    description       text,
    author_id         integer             not null references author (id),

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);

drop table if exists group_member cascade;
create table group_member
(
    id                serial primary key,
    group_id          integer              not null references research_group (id),
    author_id         integer                  not null references author (id),
    role_id           integer,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);

drop table if exists language cascade;
create table language
(
    id                serial primary key,
    name              text,
    description       text,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);

drop table if exists classification cascade;
create table classification
(
    id                serial primary key,
    name              text,
    description       text,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);

drop table if exists document_type cascade;
create table document_type
(
    id                serial primary key,
    name              text,
    description       text,
    priority          integer ,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);

drop table if exists document cascade;
create table document
(
    id                serial primary key,
    name              text,
    description       text,
    source_id         text,
    doi               text,
    publisher         text,
    publication_index text,
    publish_date      varchar (10),
    language_id       integer           ,
    title             text   ,
    abstract_text     text    ,
    classification_id integer      ,
    document_type     integer      ,

    group_id          integer ,
    major_id          integer ,
    specialization_id integer,
    cited_number      integer default 0,
    keyword           text,
    link              text,

    mta_jounal_id     text ,


    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0,
    voided            boolean default false,
    voided_time       timestamp with time zone,
    voided_by         integer default 0,
    void_reason       text
);

drop table if exists document_member cascade;
create table document_member
(
    id                serial primary key,
    document_id       integer             not null references document (id),
    author_id         integer                  not null references author (id),

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);

drop table if exists attachment cascade;
create table attachment
(
    id                serial primary key,
    document_id       integer                  NOT NULL references document (id),
    content_type      text,
    content_size_kb   integer,
    type              integer ,
    url               text,
    issn              varchar (50),
    doi               varchar (50),
    name              text,
    description       text,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer default 0,
    last_updated_time timestamp with time zone not null,
    last_updated_id   integer default 0
);

drop table if exists cited cascade;
create table cited
(
    id                serial primary key,
    document_id       integer                  NOT NULL references document (id),
    content_type      text,
    content_size_kb   integer,
    url               text,
    issn              varchar (50),
    doi               varchar (50),
    name              text,
    description       text,
    source_id         integer                  ,
    publisher         text,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer default 0,
    last_updated_time timestamp with time zone not null,
    last_updated_id   integer default 0
);


drop table if exists invention cascade;
create table invention
(
    id                serial primary key,
    name              text,     -- ten shtt
    description       text,
    application_no    varchar (50), -- so đơn
    filling_date      varchar (10), -- ngay cong bo đươn
    decided_id        text,         -- quyet dinh cap bang
    publication_no    varchar (50), -- so quyet dinh bang
    publication_date  varchar (50), -- ngay cap bang
    ptc_registration_date varchar (50),     -- ngay cong bo bang
    ptc_expired_date  varchar(30),          -- ngay  het hieu luc
    ipc_id            text,                 -- ma IPC
    major_id          integer ,
    type              varchar(50) ,  -- kieu cong bo
    source            text ,    -- noi cap bang doc quyen
    link              text, -- lien ket CSDL
    owner             text, -- chu don
    owner_address           text, -- dia chi chu don
    group_id          integer , -- ma nhom ncm

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0,
    voided            boolean default false,
    voided_time       timestamp with time zone,
    voided_by         integer default 0,
    void_reason       text
);

drop table if exists attachment_invention cascade;
create table attachment_invention
(
    id                serial primary key,
    invention_id       integer                  NOT NULL references invention (id),
    content_type      text,
    content_size_kb   integer,
    type              integer ,
    url               text,
    issn              varchar (50),
    doi               varchar (50),
    name              text,
    description       text,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer default 0,
    last_updated_time timestamp with time zone not null,
    last_updated_id   integer default 0
);

drop table if exists invention_member cascade;
create table invention_member
(
    id                serial primary key,
    invention_id      integer              not null references invention (id),
    author_id         integer,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);

drop table if exists mta_journal_type cascade;
create table mta_journal_type
(
    id                serial primary key,
    name              text,
    description       text,
    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);

drop table if exists mta_journal cascade;
create table mta_journal
(
    id                serial primary key,
    name              text,
    description       text,
    index             integer ,
    publish_date      varchar (10),
    type              integer references mta_journal_type(id),
    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);



drop table if exists document_replica cascade;
create table document_replica
(
    id                serial primary key,
    name              text,
    description       text,
    source_id         text,
    doi               text,
    publisher         text,
    publication_index text ,
    publish_date      varchar (10),
    language_id       integer           ,
    title             text   ,
    abstract_text     text    ,
    classification_id integer      ,
    document_type     integer      ,

    group_id          integer ,
    major_id          integer ,
    specialization_id integer,
    cited_number      integer default 0,
    keyword           text,

    mta_jounal_id     text ,

    nganh_id          integer ,
    chuyennganh_id    integer ,


    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);


drop table if exists document_member_replica cascade;
create table document_member_replica
(
    id                serial primary key,
    document_id       integer            ,
    author_id         integer            ,
    affiliation_id    integer            ,

    uuid              varchar(50)              not null,
    created_time      timestamp with time zone not null,
    creator_id        integer                           default 0,
    last_updated_time timestamp with time zone,
    last_updated_id   integer                           default 0
);
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


insert into role (id, name, authorities, uuid, created_time, last_updated_time) values
('sysadmin', 'sysadmin', 'SYSADMIN', uuid_generate_v4(), current_timestamp, current_timestamp),
('ROLE_TKHOA','Trưởng khoa','DOCUMENT_GET,USER_GET,GROUP_GET,AUTHOR_GET,INVENTION_GET, AFFILIATION_GET, CLASSIFICATION_GET,DOCUMENTTYPE_GET,LANGUAGE_GET,DEGREE_GET,MAJOR_GET,CITED_GET,ROLE_GET', uuid_generate_v4(), current_timestamp, current_timestamp),
('ROLE_TBM','Trưởng bộ môn','DOCUMENT_GET,USER_GET,GROUP_GET,AUTHOR_GET,INVENTION_GET, AFFILIATION_GET, CLASSIFICATION_GET,DOCUMENTTYPE_GET,LANGUAGE_GET,DEGREE_GET,MAJOR_GET,CITED_GET,ROLE_GET', uuid_generate_v4(), current_timestamp, current_timestamp),
('tkcap2','TK cấp 2','DOCUMENT_GET,USER_GET,GROUP_GET,AUTHOR_GET,INVENTION_GET, AFFILIATION_GET, CLASSIFICATION_GET,DOCUMENTTYPE_GET,LANGUAGE_GET,DEGREE_GET,MAJOR_GET,CITED_GET,ROLE_GET', uuid_generate_v4(), current_timestamp, current_timestamp);



-- update DB affiliation


-- update DB degree

update author
set degree_id = degree_id + 1
where degree_id <= 5

update author
set degree_id = 1
where degree_id = 11

-- update getAcademicRanks

update author
set degree_id = 7
where degree_id = 10

-- update degree
delete from degree  where  id = 6 or id = 7;
update degree set id = id + 100;
update degree set id = id -100 + 1 where id <= 105;
update degree set id = 1 where id = 111;
update degree set id = 7 where id = 110;
update degree set id = id -100  where id >= 108 and id <= 109;

--
update affiliation set priority = id;
update affiliation set priority = 0 where id >= 108 and id >= 248;

--
update document
set publish_date = CONCAT(publish_date, '-01-01')
where length(publish_date) = 4;
--

update document
set publish_date = CONCAT(publish_date, '-01')
where length(publish_date) <=7

--
update document
set publish_date = concat(LEFT(publish_date,length(publish_date)-2),'-0',  RIGHT(publish_date, 1))
where LEFT(RIGHT( publish_date, 3),1) != '-'

--
update document
set publish_date = concat(LEFT(publish_date,4),'-0', SUBSTRING(publish_date, 6,1), RIGHT(publish_date, 3))
where LEFT(RIGHT( publish_date, 6),1) != '-'
--



