import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
//////////////test class for creating 2 outputs from one input
public class IOtemplate{

    public static void main(String[] args){
      try{

        FileReader input = new FileReader("Entrada.in");
        BufferedReader inp = new BufferedReader(input);
        
        FileWriter tableout = new FileWriter("Tabela.out",true);
        BufferedWriter table = new BufferedWriter(tableout);
        
        FileWriter resolout = new FileWriter("Reslucao.out",true);
        BufferedWriter resol = new BufferedWriter(resolout);
        
        String line;
        line= inp.readLine();
        int t = Integer.parseInt(line);
        
        for(int i = 0; i <t;i++ ){
            line= inp.readLine();//working
            System.out.println(line);//working
            if(line.charAt(0)=='R'){//not working
                resol.write(line);
                resol.newLine();
            }
            else if(line.charAt(0)=='T'){//not working
                table.write(line);
                table.newLine();
            }
        }
      }catch(IOException e){
        e.printStackTrace();    
      }
        
    }
}