#include <bits/stdc++.h>
using namespace std;

int isFNC(string frase);
int encode(char literal);
bool getClauses(string frase);
void printclauses();
bool solveHorn();

////////////////////////////variaveis globais//////////////////////////
string metod,frase;
fstream input,resolution;//variaveis para o input e o output
int t,clausesQuantity,problema;
vector <vector<int>> clauses;
/////////////////////////////////////////////////////////////////////////////

int main(){
    input.open("Entrada.in");
    resolution.open("Resolucao.out");
    
    
    input>>t;//pega quantidade de casos testes
    for(problema=1;problema<=t;problema++){//roda t vezes
        
        input>>metod;//pega TT ou RE
        getline(input, frase);//pega expressao na linha
        if(metod=="TT"){
            //faz nada, pois será feito via TrueTable.java

        }else{//RE
            
            resolution<<"Problema #"<<problema<<endl;
            clausesQuantity = isFNC(frase);//retorna quantidade de clausulas ou -1 se nao estiver na FNC
            if(clausesQuantity==-1){
                resolution<<"Não está na FNC.\n\n";
                
            }else{
                if(!getClauses(frase)){
                    resolution<<"Nem todas as cláusulas são de Horn.\n\n";
                    
                }else{
                   
                    //printclauses();//debug line
                    if(solveHorn()){
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


//////////////////////////////funcoes para resolucao//////////////////////
int isFNC(string frase){
    //retorna a quantidade de clausulas ou -1 no caso de nao estar na FNC
    
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


int encode(char literal){//codifica as literais
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
        frasestream>>c;//ignora espacos
        if(c=='('){//abre clausula
            clause.clear();
        }else if(c==')'){//fecha clausula
            clauses.push_back(clause);
            clause.clear();
        }else{//pega p q r s
            if(c=='~'){
                frasestream>>c;i++;
                clause.push_back(-1*encode(c));
            }else if(c!='v'){//significa que é uma das literais
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



bool solveHorn(){
    queue <int> units;//guarda literais de clausulas unitarias
    bool sat=true,erased;
    for(int i=0;i<clauses.size();i++){
        if(clauses[i].size()==1){
            units.push(clauses[i][0]);
        }
    }//clausulas unitarias iniciais guardadas

    while(!units.empty() && sat){
        
        int u = units.front();//literal de clausula unitaria
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
}



void printclauses(){//print in console and in Resolucao.out
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
