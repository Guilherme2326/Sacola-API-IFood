INSERT INTO RESTAURANTE (ID, CEP, COMPLEMENTO, NAME) VALUES
(1L, '0000001', 'Complemento Endereço Restaurante 1','Restaurante 1'),
(2L, '0000002', 'Complemento Endereço Restaurante 2','Restaurante 2');

INSERT INTO CLIENTE(ID, CEP, COMPLEMENTO, NOME) VALUES
(1L, '0000001', 'Complemento Endereço Cliente 1','Cliente 1');




INSERT INTO SACOLA(ID, FORMA_PAGAMENTO, FECHADA, VALOR_TOTAL, CLIENTE_ID) VALUES
(1L,0, false,0.0,1L);

INSERT  INTO produto (id, disponivel, nome, valor_unitario, restaurante_id)  VALUES
(1L, true, 'Produto 1', 5.0, 1L),
(2L, true, 'Produto 2', 6.0, 2L);
