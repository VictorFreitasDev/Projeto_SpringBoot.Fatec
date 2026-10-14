-- V4__senha_cliente_em_bcrypt.sql
-- Migra a senha da cliente de teste para BCrypt (Aula 14)
UPDATE clientes
SET senha_hash = '$2a$10$zvHbJjdqzHjFZ13jX./nfeh5Ti58TW8k5hJQFLFY.sobYCOWUMPXa'
WHERE email = 'maria@email.com';

