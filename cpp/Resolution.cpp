#include <bits/stdc++.h>
using namespace std;
//resolution functions and global variables/////////////////
int isFNC(string frase);
int encode(char literal);
bool getClauses(string frase,int clausesQuantity,vector <vector<int>> clauses);
void printclauses(fstream resolution, int clausesQuantity,vector <vector<int>> clauses);
bool solveHorn(vector <vector<int>> clauses);
/////////////////////////////////////////////////////////////////////////////
int main(){
    vector <vector<int>> clauses;
    int t,clausesQuantity,problema=0;
    string metod,frase;
    fstream input,truetable,resolution;//variaveis para o input e o output
    input.open("Entrada.in");
    truetable.open("Tabela.out");
    resolution.open("Resolucao.out");
    //agora consigo usar input, truetable e resolution p/ input e output
    
    input>>t;//pega quantidade de casos testes
    while(t--){//roda t vezes
        problema++;
        input>>metod;//pega TT ou RE
        getline(input, frase);//pega o resto da linha

        if(metod=="TT"){
            //processar frase aqui
            truetable<<"Problema #"<<problema<<endl;
            truetable<<frase<<endl;
        }else{//RE
            //processar frase aqui
            resolution<<"Problema #"<<problema<<endl;
            clausesQuantity = isFNC(frase);//retorna quantidade de clausulas ou -1 se nao estiver na FNC
            if(clausesQuantity==-1){
                resolution<<"Não está na FNC.\n\n";
            }else{
                if(!getClauses(frase,clausesQuantity,clauses)){
                    resolution<<"Nem todas as cláusulas são de Horn.\n\n";
                }else{
                    //solve here
                    //printclauses();
                    if(solveHorn(clauses)){
                       resolution<<"Sim, é satisfatível.\n\n"; 
                    }else{
                        resolution<<"Não, não é satisfatível.\n\n";
                    }
                    
                }
                
                
            }
        }
    }
    return 0;
}



//////////////////////////////resolution functions//////////////////////
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
}//////////ok

int encode(char literal){
    if(literal=='P')
        return 1;
    if(literal =='Q')
        return 2;
    if(literal =='R')
        return 3;
    if(literal =='S')
        return 4;
}//////////ok

bool getClauses(string frase,int clausesQuantity,vector <vector<int>> clauses){//pega clausulas, poe no vector e retorna false de alguma das clausulas nao for de horn
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
}///////////////ok
bool solveHorn(vector <vector<int>> clauses){
    queue <int> units;//guarda indices de clausulas unitarias
    bool sat=true,erased;
    for(int i=0;i<clauses.size();i++){
        if(clauses[i].size()==1){
            units.push(clauses[i][0]);
        }
    }//clausulas unitarias iniciais guardadas

    while(!units.empty() && sat){
        
        int u = units.front();//variavel de clausula unitaria
        units.pop();
        //if(problema==49)//choose problem to debug
        //printclauses();//for debug purposes
        for(int i=0;i<clauses.size() && sat;i++){
            if(clauses[i].size()!=1 || (clauses[i].size()==1 && clauses[i][0]!=u)){//significa que está olhando uma clausula diferente da clausula de onde u foi copiada
                erased=false;
                for(int j=0;j<clauses[i].size() && !erased && sat;j++){
                    if(clauses[i][j]==u){//se encontrar o proprio u dentro de outra clausula
                        clauses.erase(clauses.begin()+i);//apaga clausula cujo indice é i
                        erased=true;//para o loop
                        i=0;
                    }else if(clauses[i][j]==-u && clauses[i].size()>1){
                        clauses[i].erase(clauses[i].begin()+j);//apaga variavel negada
                        j=0;
                        if(clauses[i].size()==1){//virou clausula unitaria
                            units.push(clauses[i][0]);//coloca clausula unitaria na fila
                        }

                    }else if(clauses[i][j]==-u){//quer dizer q existem as clausulas (u)&(-u)
                        sat=false;//para o loop
                    }
                }
            }
        }
    }
    if(clauses.empty())
        sat=false;

    return sat;


}//////////////////////////////ok

void printclauses(fstream resolution,int clausesQuantity,vector <vector<int>> clauses){//print in console and in Resolucao.out
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
/////////////////////////////////////////////////////////////////////