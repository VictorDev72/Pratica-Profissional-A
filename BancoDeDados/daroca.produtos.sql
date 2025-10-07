CREATE TABLE daroca.produtos(
id INT IDENTITY (1, 1) NOT NULL,
nome VARCHAR (30) NULL,
imagem VARCHAR (MAX) NULL,
valor REAL NULL,
descricao VARCHAR (50) NULL,
categoria INT,
PRIMARY KEY CLUSTERED (id ASC)
);




Insert into daroca.produtos (nome, imagem, valor, descricao, categoria) values
('banana','https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTR5DLe0gGoopO6qrcRDmZ9V5htPJ4kg0fTZTSf0Popm2HuJEBEQyrh_pEH95m0aomZQY2NdYgpyg9gKxFpTazEklbB4naZSWOiODzXqD-FHA', 7.00, 'Banana nanica', 1)
Insert into daroca.produtos (nome, imagem, valor, descricao, categoria) values
('maca','maca.jpg', 7.00, 'Maçã fuji', 1)
Insert into daroca.produtos (nome, imagem, valor, descricao, categoria) values
('alface','alface.jpg', 7.00, 'Alface crespa', 3)


SELECT * FROM daroca.produtos
