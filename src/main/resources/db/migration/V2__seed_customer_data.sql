insert into customers (id, customer_number, first_name, last_name, date_of_birth, email, phone_number, status, risk_score, created_at, updated_at) values
    (1, 'C-100001', 'Mira', 'Holm', date '1984-03-12', 'mira.holm@example.com', '+47 555 01 101', 'ACTIVE', 18, timestamp '2024-01-10 09:00:00', timestamp '2024-01-10 09:00:00'),
    (2, 'C-100002', 'Jonas', 'Berg', date '1979-11-04', 'jonas.berg@example.com', '+47 555 01 102', 'ACTIVE', 42, timestamp '2024-01-11 10:00:00', timestamp '2024-01-11 10:00:00'),
    (3, 'C-100003', 'Sara', 'Nguyen', date '1992-06-22', 'sara.nguyen@example.com', '+47 555 01 103', 'PENDING_REVIEW', 71, timestamp '2024-01-12 11:00:00', timestamp '2024-01-12 11:00:00'),
    (4, 'C-100004', 'Emil', 'Karlsen', date '1988-09-17', 'emil.karlsen@example.com', '+47 555 01 104', 'ACTIVE', 9, timestamp '2024-01-13 12:00:00', timestamp '2024-01-13 12:00:00'),
    (5, 'C-100005', 'Nora', 'Larsen', date '1967-01-30', 'nora.larsen@example.com', '+47 555 01 105', 'SUSPENDED', 83, timestamp '2024-01-14 13:00:00', timestamp '2024-01-14 13:00:00'),
    (6, 'C-100006', 'Oskar', 'Eide', date '1995-12-01', 'oskar.eide@example.com', '+47 555 01 106', 'ACTIVE', 15, timestamp '2024-01-15 14:00:00', timestamp '2024-01-15 14:00:00'),
    (7, 'C-100007', 'Leah', 'Solberg', date '1990-02-08', 'leah.solberg@example.com', '+47 555 01 107', 'ACTIVE', 25, timestamp '2024-01-16 15:00:00', timestamp '2024-01-16 15:00:00'),
    (8, 'C-100008', 'Aksel', 'Dahl', date '1975-04-19', 'aksel.dahl@example.com', '+47 555 01 108', 'CLOSED', 51, timestamp '2024-01-17 16:00:00', timestamp '2024-01-17 16:00:00'),
    (9, 'C-100009', 'Ingrid', 'Moen', date '1981-07-14', 'ingrid.moen@example.com', '+47 555 01 109', 'ACTIVE', 37, timestamp '2024-01-18 17:00:00', timestamp '2024-01-18 17:00:00'),
    (10, 'C-100010', 'Felix', 'Vik', date '1999-10-03', 'felix.vik@example.com', '+47 555 01 110', 'PENDING_REVIEW', 64, timestamp '2024-01-19 18:00:00', timestamp '2024-01-19 18:00:00'),
    (11, 'C-100011', 'Ella', 'Ahmed', date '1986-05-26', 'ella.ahmed@example.com', '+47 555 01 111', 'ACTIVE', 22, timestamp '2024-01-20 08:30:00', timestamp '2024-01-20 08:30:00'),
    (12, 'C-100012', 'Magnus', 'Strand', date '1972-08-07', 'magnus.strand@example.com', '+47 555 01 112', 'ACTIVE', 48, timestamp '2024-01-21 09:30:00', timestamp '2024-01-21 09:30:00');

insert into addresses (customer_id, type, street_line_1, street_line_2, postal_code, city, country_code, primary_address) values
    (1, 'REGISTERED', 'Fjordgata 12', null, '5014', 'Bergen', 'NO', true),
    (1, 'BILLING', 'Postboks 101', null, '5800', 'Bergen', 'NO', false),
    (2, 'REGISTERED', 'Parkveien 8', 'Leilighet 3B', '0350', 'Oslo', 'NO', true),
    (3, 'REGISTERED', 'Skogsvingen 44', null, '7030', 'Trondheim', 'NO', true),
    (4, 'REGISTERED', 'Havnegata 6', null, '4006', 'Stavanger', 'NO', true),
    (5, 'REGISTERED', 'Markveien 21', null, '0554', 'Oslo', 'NO', true),
    (6, 'REGISTERED', 'Storgata 1', null, '9008', 'Tromso', 'NO', true),
    (7, 'REGISTERED', 'Elvebakken 17', null, '2609', 'Lillehammer', 'NO', true),
    (8, 'REGISTERED', 'Kystveien 90', null, '8006', 'Bodo', 'NO', true),
    (9, 'REGISTERED', 'Molleveien 5', null, '1610', 'Fredrikstad', 'NO', true),
    (10, 'REGISTERED', 'Solsiden 2', null, '2815', 'Gjovik', 'NO', true),
    (11, 'REGISTERED', 'Granlia 31', null, '3045', 'Drammen', 'NO', true),
    (12, 'REGISTERED', 'Bekkestien 7', null, '1410', 'Kolbotn', 'NO', true);

insert into accounts (customer_id, account_number, type, status, balance, opened_date) values
    (1, 'ACC-200001', 'CHECKING', 'ACTIVE', 12450.50, date '2019-03-14'),
    (1, 'ACC-200101', 'SAVINGS', 'ACTIVE', 88000.00, date '2020-06-01'),
    (2, 'ACC-200002', 'CHECKING', 'ACTIVE', 2430.12, date '2018-10-22'),
    (3, 'ACC-200003', 'CHECKING', 'BLOCKED', 50.00, date '2023-05-12'),
    (4, 'ACC-200004', 'CREDIT', 'ACTIVE', -1500.00, date '2021-09-10'),
    (5, 'ACC-200005', 'CHECKING', 'BLOCKED', 19.90, date '2016-01-03'),
    (6, 'ACC-200006', 'SAVINGS', 'ACTIVE', 34000.00, date '2022-12-09'),
    (7, 'ACC-200007', 'CHECKING', 'ACTIVE', 780.40, date '2020-02-29'),
    (8, 'ACC-200008', 'CHECKING', 'CLOSED', 0.00, date '2017-07-07'),
    (9, 'ACC-200009', 'CHECKING', 'ACTIVE', 6550.00, date '2019-11-23'),
    (10, 'ACC-200010', 'CREDIT', 'ACTIVE', -340.10, date '2024-01-02'),
    (11, 'ACC-200011', 'SAVINGS', 'ACTIVE', 120000.00, date '2015-04-11'),
    (12, 'ACC-200012', 'CHECKING', 'ACTIVE', 305.25, date '2014-08-18');

insert into consents (customer_id, category, granted, captured_at, channel) values
    (1, 'MARKETING', true, timestamp '2024-01-10 09:05:00', 'web'),
    (1, 'DIGITAL_COMMUNICATION', true, timestamp '2024-01-10 09:05:00', 'web'),
    (2, 'MARKETING', false, timestamp '2024-01-11 10:10:00', 'phone'),
    (3, 'THIRD_PARTY_SHARING', false, timestamp '2024-01-12 11:15:00', 'branch'),
    (4, 'PROFILING', true, timestamp '2024-01-13 12:20:00', 'web'),
    (5, 'DIGITAL_COMMUNICATION', true, timestamp '2024-01-14 13:25:00', 'web'),
    (6, 'MARKETING', true, timestamp '2024-01-15 14:30:00', 'web'),
    (7, 'DIGITAL_COMMUNICATION', true, timestamp '2024-01-16 15:35:00', 'phone'),
    (8, 'MARKETING', false, timestamp '2024-01-17 16:40:00', 'branch'),
    (9, 'PROFILING', false, timestamp '2024-01-18 17:45:00', 'web'),
    (10, 'THIRD_PARTY_SHARING', false, timestamp '2024-01-19 18:50:00', 'web'),
    (11, 'MARKETING', true, timestamp '2024-01-20 08:35:00', 'phone'),
    (12, 'DIGITAL_COMMUNICATION', true, timestamp '2024-01-21 09:35:00', 'web');

insert into support_cases (customer_id, case_number, title, description, status, priority, opened_at, updated_at) values
    (1, 'CASE-300001', 'Address confirmation', 'Customer reported that billing address did not match the registered address.', 'OPEN', 2, timestamp '2024-02-01 09:00:00', timestamp '2024-02-01 09:00:00'),
    (3, 'CASE-300002', 'Account review', 'Automated review flagged inconsistent account activity.', 'WAITING_FOR_CUSTOMER', 4, timestamp '2024-02-02 10:00:00', timestamp '2024-02-03 10:00:00'),
    (5, 'CASE-300003', 'Suspension appeal', 'Customer requested explanation of account suspension.', 'OPEN', 5, timestamp '2024-02-04 11:00:00', timestamp '2024-02-04 11:00:00'),
    (7, 'CASE-300004', 'Consent update', 'Customer asked to update digital communication preferences.', 'RESOLVED', 1, timestamp '2024-02-05 12:00:00', timestamp '2024-02-05 15:00:00'),
    (10, 'CASE-300005', 'New customer review', 'Manual review requested before activating additional products.', 'OPEN', 3, timestamp '2024-02-06 13:00:00', timestamp '2024-02-06 13:00:00');

insert into audit_events (customer_number, event_type, description, created_at, created_by) values
    ('C-100001', 'CUSTOMER_CREATED', 'Customer profile created from onboarding flow.', timestamp '2024-01-10 09:00:00', 'system'),
    ('C-100003', 'CUSTOMER_REVIEWED', 'Customer placed in pending review after account activity check.', timestamp '2024-01-12 12:00:00', 'review-batch'),
    ('C-100005', 'CUSTOMER_SUSPENDED', 'Customer suspended after failed verification.', timestamp '2024-01-14 14:00:00', 'risk-service'),
    ('C-100008', 'CUSTOMER_CLOSED', 'Customer relationship closed by request.', timestamp '2024-01-17 17:00:00', 'operator'),
    ('C-100010', 'CUSTOMER_REVIEWED', 'New customer pending review before account activation.', timestamp '2024-01-19 19:00:00', 'review-batch');
