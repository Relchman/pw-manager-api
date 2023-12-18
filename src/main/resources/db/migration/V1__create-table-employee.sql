-- db.migration/V1__create-table-employee.sql

CREATE TABLE employee (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          nome VARCHAR(255) NOT NULL,
                          senha VARCHAR(255) NOT NULL,
                          id_superior INT,
                          UNIQUE (nome),
                          FOREIGN KEY (id_superior) REFERENCES employee(id)
);

-- Adicionando uma coluna para armazenar a senha criptografada
ALTER TABLE employee ADD COLUMN senha_hash VARCHAR(255);

-- Adicionando uma restrição para garantir que senha_hash seja única
ALTER TABLE employee ADD CONSTRAINT unique_senha_hash UNIQUE (senha_hash);
