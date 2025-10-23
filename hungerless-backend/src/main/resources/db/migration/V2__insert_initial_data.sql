-- Inserir Endereço para o Admin
INSERT INTO enderecos (logradouro, numero, bairro, cidade, estado, cep, latitude, longitude)
VALUES ('Rua Principal', '100', 'Centro', 'São Paulo', 'SP', '01000-000', -23.5505, -46.6333);

-- Inserir Contato para o Admin
INSERT INTO contatos (telefone, celular, email_alternativo)
VALUES ('1133334444', '11988887777', 'admin.alt@hungerless.com');

-- Inserir Usuário Admin (Senha: admin123 - BCrypt)
INSERT INTO usuarios (nome, email, senha, tipo, documento, endereco_id, contato_id)
VALUES ('Administrador Geral', 'admin@hungerless.com', '$2a$10$wE99q0H7.O6p0D.y1P8r.u9J.g8g.l4v.y0.X4.j7.N8.g3.t5.c2', 'ADMIN', '00000000000', 1, 1);

-- Inserir Categorias Iniciais
INSERT INTO categorias (nome, descricao) VALUES
('Frutas', 'Frutas frescas, secas ou congeladas.'),
('Vegetais', 'Legumes, verduras e hortaliças.'),
('Laticínios', 'Leite, queijos, iogurtes e derivados.'),
('Grãos e Cereais', 'Arroz, feijão, lentilha, milho, etc.'),
('Pães e Massas', 'Pães, bolos, macarrão e afins.'),
('Outros', 'Itens diversos não listados.');
