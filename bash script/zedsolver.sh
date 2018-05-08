#!/bin/bash


##criando e/ou resetando saídas
touch Resolucao.out Tabela.out
echo "" > Resolucao.out
echo "" > Tabela.out

javac TrueTable.java
java TrueTable <Entrada.in>Tabela.out
##Tabela.out completa

g++ resolver.cpp -std=c++11
./a.out <Entrada.in>Resolucao.out
##Resolucao.out completa
