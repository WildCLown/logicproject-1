#include <bits/stdc++.h>
using namespace std;
int isFNC(string frase);
int encode(char literal);
bool getClauses(string frase);
void printclauses();
string metod,frase;
fstream input,truetable,resolution;//variaveis para o imput e o output
int t,clausesQuantity;
vector <vector<int>> clauses;
int main(){
    input.open("Entrada.in");
    truetable.open("Tabela.out");
    resolution.open("Resolucao.out");
    //agora consigo usar input, truetable e resolution p/ input e output
    
    input>>t;//pega quantidade de casos testes
    while(t--){//roda t vezes
    
        input>>metod;//pega TT ou RE
        getline(input, frase);//pega o resto da linha

        if(metod=="TT"){
            //processar frase aqui

            truetable<<frase<<endl;
        }else{//RE
            //processar frase aqui
            resolution<<frase<<endl;
            clausesQuantity = isFNC(frase);//retorna quantidade de clausulas ou -1 se nao estiver na FNC
            if(clausesQuantity==-1){
                resolution<<"Não está na FNC.\n";
            }else{
                if(!getClauses(frase)){
                    resolution<<"Nem todas as cláusulas são de horn."<<endl;
                }else{
                    //solve here
                    printclauses();
                }
                
                
            }
        }
    }
    return 0;
}

int isFNC(string frase){
    int q=0;//quantidade de clausulas
    int lv=0;//significa o quao dentro da expressao original o algoritmo está
    for(int i=0;i<frase.size();i++){
        if(frase[i]==' '){
            //do nothing
        }else if(frase[i]=='('){
            lv++;
        }else if(frase[i]==')'){//conta uma clausula
            lv--; q++;
        }else if(lv==0 && frase[i]!='&'){//significa está fora das clausulas e o operador está errado
            return -1;
        }else if(lv>0 && frase[i]!='~' && frase[i]!='P' && frase[i]!='Q' && frase[i]!='R' && frase[i]!='S' && frase[i]!='v'){//significa que existe algo invalido dentro de clausulas
            return -1;
        }
    }
    return q;
}

int encode(char literal){
    if(literal=='P')
        return 1;
    if(literal =='Q')
        return 2;
    if(literal =='R')
        return 3;
    if(literal =='S')
        return 4;
}
bool getClauses(string frase){//pega clausulas, poe no vector e retorna false de alguma das clausulas nao for de horn
    char c;
    int contador;
    stringstream frasestream(frase);
    vector<int> clause;
    clauses.clear();
    for(int i=0;i<frase.size();i++){
        frasestream>>c;
        if(c=='('){//get clause
            clause.clear();
        }else if(c==')'){//close clause
            clauses.push_back(clause);
            clause.clear();
        }else{//get p q r s
            if(c=='~'){
                frasestream>>c;i++;
                clause.push_back(-1*encode(c));
            }else if(c!='v'){//if is p q r or s
                clause.push_back(encode(c));
            }
        }

    }
    for(int i=0;i<clausesQuantity;i++){
        contador=0;
        for(int j=0;j<clauses[i].size() && !clauses[i].empty();j++){
            if(clauses[i][j]>0)
                contador++;
        }
        if(contador>1)//tal clausla nao é de horn
            return false;
    }

return true;
}
void printclauses(){
    for(int i=0;i<clausesQuantity;i++){
        for(int j=0;j<clauses[i].size() && !clauses[i].empty();j++){
            cout<<clauses[i][j]<<" ";
            resolution<<clauses[i][j]<<" ";
        }
        cout<<endl;
        resolution<<endl;
    }cout<<endl;
    resolution<<endl;

}