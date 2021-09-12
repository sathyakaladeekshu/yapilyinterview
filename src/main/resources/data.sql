DROP TABLE IF EXISTS KEY;

CREATE TABLE KEY (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  type VARCHAR(100) NOT NULL,
  public_key VARCHAR(250) NOT NULL,
  private_key VARCHAR(250) NOT NULL
);

INSERT INTO KEY (type,public_key, private_key) VALUES ('Marvel secret key','e37f291e4750395b1f5d2159d9d58167', '8d9d0f0c9ad36251921c83c416cd87223cd4ad3a');
INSERT INTO KEY (type,public_key, private_key) VALUES ('Google secret key','', 'AIzaSyC-DCrhO7tE22N_ajLNo8_KcrLwEELEfo4');