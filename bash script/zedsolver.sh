#!/bin/bash


##criando e/ou resetando saídas
touch Resolucao.out Tabela.out
echo "" > Resolucao.out
echo "" > Tabela.out

g++ resolver.cpp -std=c++11
./a.out
##Resolucao.out completa

javac TrueTable.java
java TrueTable <Entrada.in>Tabela.out
##Tabela.out completa
