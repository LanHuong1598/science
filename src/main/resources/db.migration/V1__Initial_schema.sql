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
    parent_id         text,
    level             text,

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
    degree_id            text,
    academic_rank_id     text,
    gender            varchar(5),
    birthdate         timestamp with time zone,
    scientific_title  text,
    affiliation_id    integer references affiliation (id),
    major_id          integer references major (id),
    orcid_id          varchar (50),
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
    source_id         integer                  not null references source (id),
    doi               text,
    publisher         text,
    publication_index integer ,
    publish_date      varchar (10),
    language_id       integer              not null references language (id),
    title             text    not null,
    abstract_text     text    not null,
    classification_id integer not null        not null references classification (id),
    document_type     integer  not null       not null references document_type (id),

    group_id          integer not null  references research_group(id),
    major_id          integer not null  references major(id),
    specialization_id integer not null  references major(id),
    cited_number      integer default 0,
    keyword           text,

    mta_jounal_id     integer references mta_journal(id),

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
    source_id         integer                  not null references source (id),
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
    name              text,
    description       text,
    application_no    varchar (50),
    filling_date      varchar (10),
    publication_no    varchar (50),
    publication_date  varchar (50),
    ptc_registration_date varchar (50),
    ptc_expired_date  varchar(30),
    major_id          integer not null  references major(id),
    type              varchar(50) not null ,

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
    author_id         integer                  not null references author (id),

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
