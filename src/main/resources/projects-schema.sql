DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS project;

CREATE TABLE project (
  project_id INT AUTO_INCREMENT NOT NULL,
  project_name VARCHAR(128) NOT NULL,
  estimated_hours decimal(7,2),
  actual_hours decimal(7,2),
  difficulty int,
  notes text,
  PRIMARY KEY (project_id)
);

CREATE TABLE material (
  material_id INT AUTO_INCREMENT NOT NULL,
  project_id INT  NOT NULL,
  material_name varchar(128),
  num_required int,
  cost decimal(7,2),  
  PRIMARY KEY (material_id),
  FOREIGN KEY (project_id) references project (project_id) ON DELETE CASCADE
);

CREATE TABLE step (
  step_id INT AUTO_INCREMENT NOT NULL,
  project_id INT  NOT NULL,
  step_text text,
  step_order int,
 PRIMARY KEY (step_id),
 FOREIGN KEY (project_id) references project (project_id) ON DELETE CASCADE
);

CREATE TABLE category(
category_id int AUTO_INCREMENT NOT NULL,
category_name varchar(128),
PRIMARY KEY (category_id)
);

CREATE TABLE project_category (
    project_id INT AUTO_INCREMENT NOT NULL,
    category_id INT NOT NULL UNIQUE KEY,
    FOREIGN KEY (category_id) references category (category_id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) references project (project_id),
     UNIQUE KEY (project_id, category_id)
)

