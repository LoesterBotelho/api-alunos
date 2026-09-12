@echo off

set DOCKER_USER=loesterbotelho
set IMAGE_NAME=api-alunos
set TAG=1.0.0

echo.
echo Fazendo login no Docker Hub...
docker login

echo.
echo Construindo a imagem Docker para %DOCKER_USER%/%IMAGE_NAME%:%TAG%...
docker build -t %DOCKER_USER%/%IMAGE_NAME%:%TAG% .

echo.
echo Enviando a imagem para o Docker Hub...
docker push %DOCKER_USER%/%IMAGE_NAME%:%TAG%

echo.
echo Processo concluido com sucesso!
pause