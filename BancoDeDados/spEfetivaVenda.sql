create procedure loja.spEfetivaVenda @pIdCliente int, @pQtasParcelas int, @pidFormaPagamento int
as
begin
	Declare @IdItemCesta int,		-- vari�veis usadas para
			@IdCliente int,			-- armazenar os valores lidos
			@DataVenda Datetime,	-- do cursor (das tabelas)
			@QuantComprada int,
			@IdProduto int,
			@QuantEstoque int,
			@precoUnitario Money,
			@idVenda int,
			@ValorTotal Money,
			@numParcela int,
			@valorParcela Money,
			@DataVencimento DateTime,
			@erro int,
			@numItem int

	-- cursor que busca e armazena os registros da cesta de compras do cliente
	-- cujo idCliente foi passado como par�metro
	Declare cCestaCompra Cursor for
		Select
			CC.idItemCesta, V.idCliente, V.dataVenda,
			CC.quantComprada, P.idProduto, P.quantEstoque, P.precoUnitario
		from
			Loja.Cesta CC Inner JOIN Loja.Produto P 
			on P.idProduto = CC.idProduto
				inner join loja.Venda V
				on cc.idVenda = v.idVenda
		where
			idCliente = @pIdCliente
		order by
			dataVenda
	set @DataVenda = GetDate() -- data atual, para, junto ao Id_Cliente,
-- identificarmos esta venda, pois o IdVenda dessa 
-- tabela n�o � devolvido ap�s o Insert
	Begin Tran
	 Insert
		into loja.Venda (IdCliente, DataVenda, idFormaPagamento, QuantasParcelas)
		values (@pIdCliente, @DataVenda, @pidFormaPagamento, @pQtasParcelas)
	-- busca o IdVenda do registro rec�m-inserido no insert acima
	Select @idVenda = idVenda 
		from loja.Venda 
		where idCliente = @pIdCliente and  -- usamos estas vari�veis para
			  dataVenda = @DataVenda       -- buscar o IdVenda rec�m-gravado
	Open cCestaCompra -- abre o cursor e traz os dados resultantes do where
	Fetch cCestaCompra -- l� um registro e armazena os campos nas vari�veis
		into @IdItemCesta, @IdCliente, @DataVenda, @QuantComprada, @IdProduto,
		@QuantEstoque, @precoUnitario 
	 set @ValorTotal = 0
	 set @numItem = 0
	 set @erro = 0
	 while @@Fetch_Status = 0 -- @@Fetch_Status � fun��o que devolve o resultado
	 begin                    -- do Fetch anterior; 0 indica que foi lido um registro
                              -- processa o registro atual
                              -- inclui um registro na tabela ItemVenda desta venda, com o produto atual
		set @NumItem += 1
		if @quantComprada <= @QuantEstoque -- estoque atende o pedido totalmente
		begin
			Insert into Loja.ItemVenda (IdVenda, NumItem, DataLiberacao, 
									idProduto, quantComprada) 
			values (@IdVenda, @NumItem, @DataVenda, 
					@idProduto, @quantComprada)
			Update Loja.Produto set quantEstoque = @QuantEstoque - @QuantComprada
			Where idProduto = @idProduto
			Set @ValorTotal += @quantComprada * @precoUnitario
		end
		else -- lote de estoque atual n�o atende totalmente o pedido
		begin
			Rollback Tran	-- desfaz as atualiza��es ocorridas no BD desde o begin tran
			set @erro = 1
			Break			-- sai do while, pois deu erro dentro da transa��o
		end
		Fetch cCestaCompra -- l� novo registro da cesta e armazena os campos nas vari�veis
		into @IdItemCesta, @IdCliente, @DataVenda, @QuantComprada, @IdProduto,
			@QuantEstoque, @precoUnitario
	End
	close cCestaCompra -- fecha o cursor
	Deallocate cCestaCompra
	if @erro = 0 -- n�o houve erros
	begin
		set @ValorParcela = @ValorTotal / @pQtasParcelas
		set @NumParcela = 1
		set @DataVencimento = @DataVenda
		while @NumParcela <= @pQtasParcelas 
		begin
			 Insert into Loja.Parcela 
			(IdVenda, QualParcela, DataVencimento, ValorPrevisto)
			 values (@IdVenda, @NumParcela, @DataVencimento, @ValorParcela)
			 set @NumParcela += 1
			 set @DataVencimento += 30 -- 30 dias depois vence outra parcela
		end
		Delete from Loja.Cesta 
		Where idCliente = @pIdCliente -- apaga a cesta desse cliente
		
		Commit Tran  -- registra no BD, definitivamente, todas as transa��es ocorridas
	end
End
