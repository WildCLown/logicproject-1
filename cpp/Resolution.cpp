#include <bits/stdc++.h>
using namespace std;

string metod,frase;
fstream input,truetable,resolution;//variaveis para o imput e o output
int t;
int main(){
    input.open("Entrada.in");
    truetable.open("Tabela.out");
    resolution.open("Resolucao.out");
    //agora consigo usar input, truetable e resolution p/ input e output
    
    input>>t;//pega quantidade de casos testes
    while(t--){//roda t vezes
    
        input>>metod;//pega TT ou RE
        getline(input, frase);//pega o resto da linha

        if(metod=="TT"){//printar na Tabela.out
            //processar frase aqui
            truetable<<frase<<endl;
        }else{//printar na Resolucao.out
            //processar frase aqui
            resolution<<frase<<endl;
        }
    }





    return 0;
}