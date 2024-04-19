CREATE TABLE role (
  role_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(45) NOT NULL
);

CREATE TABLE users_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  KEY user_fk_idx (user_id),
  KEY role_fk_idx (role_id),
  CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES role (role_id),
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES staff (staff_id)
);