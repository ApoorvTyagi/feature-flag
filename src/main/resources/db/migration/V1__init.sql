-- Create clients table
CREATE TABLE clients (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create feature_flags table
CREATE TABLE feature_flags (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create client_feature_flags table
CREATE TABLE client_feature_flags (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  client_id BIGINT,
  feature_flag_id BIGINT,
  status BOOLEAN NOT NULL,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE(client_id, feature_flag_id),
  FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
  FOREIGN KEY (feature_flag_id) REFERENCES feature_flags(id) ON DELETE CASCADE
);

-- Create feature_flag_dependencies table
CREATE TABLE feature_flag_dependencies (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  parent_flag_id BIGINT,
  child_flag_id BIGINT,
  UNIQUE(parent_flag_id, child_flag_id),
  FOREIGN KEY (parent_flag_id) REFERENCES feature_flags(id),
  FOREIGN KEY (child_flag_id) REFERENCES feature_flags(id)
);
