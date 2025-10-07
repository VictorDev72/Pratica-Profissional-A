create table daroca.cliente
(
	idCliente int primary key identity not null,
	prenome varchar(20) not null,
	sobrenome varchar(30) not null,
	nascimento date not null,
	email varchar(50) unique not null,
	celular varchar(14) not null,
	cpf varchar(15) not null,
	tipo varchar(10) not null,
	logradouro varchar(50) not null,
	numero varchar(6) not null,
	complemento varchar(20) not null,
	cep varchar(8) not null,
	senha varchar(MAX) not null
)
