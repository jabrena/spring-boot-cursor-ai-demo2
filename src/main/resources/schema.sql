-- Create a simple table for testing
CREATE TABLE IF NOT EXISTS sample_data (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create actor table
CREATE TABLE IF NOT EXISTS actor (
  actor_id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Insert some sample data
INSERT INTO actor (first_name, last_name) VALUES
  ('Tom', 'Hanks'),
  ('Meryl', 'Streep'),
  ('Leonardo', 'DiCaprio')
ON CONFLICT (actor_id) DO NOTHING; 