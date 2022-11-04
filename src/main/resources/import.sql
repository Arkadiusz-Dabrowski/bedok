INSERT INTO equipment (code, name) VALUES ('IRON', 'żelazko') ON CONFLICT DO NOTHING;
INSERT INTO equipment (code, name) VALUES ('HOOVER', 'odkurzacz') ON CONFLICT DO NOTHING;
INSERT INTO equipment (code, name) VALUES ('BATHROOM', 'łazienka') ON CONFLICT DO NOTHING;
INSERT INTO equipment (code, name) VALUES ('KITCHEN', 'kuchnia') ON CONFLICT DO NOTHING;
INSERT INTO equipment (code, name) VALUES ('BALCONY', 'balkon') ON CONFLICT DO NOTHING;
INSERT INTO equipment (code, name) VALUES ('TV', 'telewizor') ON CONFLICT DO NOTHING;
INSERT INTO equipment (code, name) VALUES ('RADIO', 'radio') ON CONFLICT DO NOTHING;
INSERT INTO equipment (code, name) VALUES ('DESK', 'biurko') ON CONFLICT DO NOTHING;
INSERT INTO equipment (code, name) VALUES ('BATH', 'wanna') ON CONFLICT DO NOTHING;
INSERT INTO equipment (code, name) VALUES ('FRIDGE', 'lodówka') ON CONFLICT DO NOTHING;
INSERT INTO equipment (code, name) VALUES ('WARDROBE', 'szafa') ON CONFLICT DO NOTHING;
INSERT INTO equipment (code, name) VALUES ('WIFI', 'internet') ON CONFLICT DO NOTHING;
INSERT INTO payment_type (code, name) VALUES ('CACHE', 'gotówka') ON CONFLICT DO NOTHING;
INSERT INTO payment_type (code, name) VALUES ('BLIK', 'blik') ON CONFLICT DO NOTHING;
INSERT INTO payment_type (code, name) VALUES ('CARD', 'karta') ON CONFLICT DO NOTHING;


