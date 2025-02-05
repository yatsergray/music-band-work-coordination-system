INSERT INTO chat_access_roles (id, name, type)
VALUES ('550e8400-e29b-41d4-a716-446655440001', 'Owner', 'OWNER'),
       ('550e8400-e29b-41d4-a716-446655440002', 'Admin', 'ADMIN'),
       ('550e8400-e29b-41d4-a716-446655440003', 'Member', 'MEMBER');

INSERT INTO event_categories (id, name, type)
VALUES ('550e8400-e29b-41d4-a716-446655440004', 'Musicians rehearsal', 'MUSICIANS_REHEARSAL'),
       ('550e8400-e29b-41d4-a716-446655440005', 'Vocalists rehearsal', 'VOCALISTS_REHEARSAL'),
       ('550e8400-e29b-41d4-a716-446655440006', 'Dress rehearsal', 'DRESS_REHEARSAL'),
       ('550e8400-e29b-41d4-a716-446655440007', 'Rough rehearsal', 'ROUGH_REHEARSAL'),
       ('550e8400-e29b-41d4-a716-446655440008', 'Performance', 'PERFORMANCE');

INSERT INTO music_band_access_roles (id, name, type)
VALUES ('550e8400-e29b-41d4-a716-446655440009', 'Owner', 'OWNER'),
       ('550e8400-e29b-41d4-a716-446655440010', 'Admin', 'ADMIN'),
       ('550e8400-e29b-41d4-a716-446655440011', 'Member', 'MEMBER');

INSERT INTO participation_statuses (id, name, type)
VALUES ('550e8400-e29b-41d4-a716-446655440012', 'Accepted', 'ACCEPTED'),
       ('550e8400-e29b-41d4-a716-446655440013', 'Pending', 'PENDING'),
       ('550e8400-e29b-41d4-a716-446655440014', 'Rejected', 'REJECTED');

INSERT INTO roles (id, name, type)
VALUES ('550e8400-e29b-41d4-a716-446655440015', 'Admin', 'ADMIN'),
       ('550e8400-e29b-41d4-a716-446655440016', 'User', 'USER');

INSERT INTO stage_roles (id, name, type)
VALUES ('550e8400-e29b-41d4-a716-446655440017', 'Vocal', 'VOCAL'),
       ('550e8400-e29b-41d4-a716-446655440018', 'Piano', 'PIANO'),
       ('550e8400-e29b-41d4-a716-446655440019', 'Acoustic guitar', 'ACOUSTIC_GUITAR'),
       ('550e8400-e29b-41d4-a716-446655440020', 'Electric guitar', 'ELECTRIC_GUITAR'),
       ('550e8400-e29b-41d4-a716-446655440021', 'Bass guitar', 'BASS_GUITAR'),
       ('550e8400-e29b-41d4-a716-446655440022', 'Drums', 'DRUMS');
