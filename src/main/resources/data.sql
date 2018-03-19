insert into Autor (id, nome) values
	(1,'Marcio Katsumi'),
	(2,'Marcelo Mikio'),
	(3,'Eduardo Katsuki');
	
insert into Cliente (id_cliente, nome, endereco, data_nascimento, observacao) values
	(1,'João da Silva','Rua do joao','2018-01-01','Teste'),
	(2,'Maria da Silva','Rua do maria','2018-01-01','Teste'),
	(3,'Laura da Silva','Rua do laura','2018-01-01','Teste');
	
insert into Livro (id, nome, quantidade, quantidade_paginas, isbn, autor_id) values
	(1,'PHP','50','200','teste','1'),
	(2,'JAVA','50','200','teste','1'),
	(3,'C#','50','200','teste','1');
	
insert into Emprestimo (id, data_emprestimo, data_devolucao, cliente_id_cliente, livro_id) values
	(1,'2018-01-01','2019-01-01',1,1),
	(2,'2025-01-01','2026-01-01',2,2),
	(3,'2036-01-01','2040-01-01',3,3);
	
insert into Usuario (id, username, email, password) values
	(1,'katsumi','katsumi@email.com.br','$2a$10$ZTZuJZIEZnXRJmZKMFHw0uW0gfSk.qNXtPj43wHMKolqHNk37p0lG'),
	(2,'vittoria','vittoria@email.com.br','$2a$10$ZTZuJZIEZnXRJmZKMFHw0uW0gfSk.qNXtPj43wHMKolqHNk37p0lG');

insert into Role (id, role, usuario_id) values
	(1,'ADMIN',1);	
	
insert into Review (id, avaliacao, comentario, livro_id, usuario_id) values
	(1,'2','Teste', 1, 1),
	(2,'5','Teste 2',2, 1),
	(3,'5','Teste 3',2, 1);	