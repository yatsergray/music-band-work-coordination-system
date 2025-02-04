create table chat_access_roles
(
    id   uuid         not null,
    name varchar(255) not null unique,
    type varchar(255) not null unique check (type in ('OWNER', 'ADMIN', 'MEMBER')),
    primary key (id)
);
create table chat_user_access_roles
(
    id                  uuid not null,
    chat_access_role_id uuid not null,
    chat_id             uuid not null,
    user_id             uuid not null,
    primary key (id)
);
create table chats
(
    id            uuid         not null,
    music_band_id uuid         not null,
    name          varchar(255) not null,
    primary key (id)
);
create table event_categories
(
    id   uuid         not null,
    name varchar(255) not null unique,
    type varchar(255) not null unique check (type in ('MUSICIANS_REHEARSAL', 'VOCALISTS_REHEARSAL', 'DRESS_REHEARSAL',
                                                      'ROUGH_REHEARSAL', 'PERFORMANCE')),
    primary key (id)
);
create table event_songs
(
    id              uuid    not null,
    sequence_number integer not null,
    event_id        uuid    not null,
    song_id         uuid,
    primary key (id)
);
create table event_users
(
    id                      uuid not null,
    event_id                uuid not null,
    participation_status_id uuid not null,
    stage_role_id           uuid not null,
    user_id                 uuid,
    primary key (id)
);
create table events
(
    id                uuid    not null,
    date              date    not null,
    end_time          time(6) not null,
    start_time        time(6) not null,
    event_category_id uuid    not null,
    music_band_id     uuid    not null,
    primary key (id)
);
create table invitations
(
    id                      uuid         not null,
    music_band_id           uuid         not null,
    participation_status_id uuid         not null,
    token                   uuid         not null unique,
    email                   varchar(255) not null,
    primary key (id)
);
create table messages
(
    id      uuid         not null,
    date    date         not null,
    edited  boolean,
    time    time(6)      not null,
    chat_id uuid         not null,
    user_id uuid,
    text    varchar(255) not null,
    primary key (id)
);
create table music_band_access_roles
(
    id   uuid         not null,
    name varchar(255) not null unique,
    type varchar(255) not null unique check (type in ('OWNER', 'ADMIN', 'MEMBER')),
    primary key (id)
);
create table music_band_user_access_roles
(
    id                        uuid not null,
    music_band_access_role_id uuid not null,
    music_band_id             uuid not null,
    user_id                   uuid not null,
    primary key (id)
);
create table music_band_user_stage_roles
(
    id            uuid not null,
    music_band_id uuid not null,
    stage_role_id uuid not null,
    user_id       uuid not null,
    primary key (id)
);
create table music_bands
(
    id   uuid         not null,
    name varchar(255) not null,
    primary key (id)
);
create table participation_statuses
(
    id   uuid         not null,
    name varchar(255) not null unique,
    type varchar(255) not null unique check (type in ('ACCEPTED', 'PENDING', 'REJECTED')),
    primary key (id)
);
create table roles
(
    id   uuid         not null,
    name varchar(255) not null unique,
    type varchar(255) not null unique check (type in ('ADMIN', 'USER')),
    primary key (id)
);
create table songs
(
    id            uuid         not null,
    music_band_id uuid         not null,
    artist_name   varchar(255) not null,
    name          varchar(255) not null unique,
    youtube_id    varchar(255) not null,
    primary key (id)
);
create table stage_roles
(
    id   uuid         not null,
    name varchar(255) not null unique,
    type varchar(255) not null unique check (type in
                                             ('VOCAL', 'PIANO', 'ACOUSTIC_GUITAR', 'ELECTRIC_GUITAR', 'BASS_GUITAR',
                                              'DRUMS')),
    primary key (id)
);
create table user_roles
(
    role_id uuid not null,
    user_id uuid not null,
    primary key (role_id, user_id)
);
create table users
(
    id         uuid         not null,
    email      varchar(255) not null unique,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    password   varchar(255) not null,
    primary key (id)
);
