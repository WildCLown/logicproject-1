import java.util.Arrays;
import java.util.Scanner;

public class TrueTable {
	public static void main(String[] args) {
		Scanner leia = new Scanner(System.in);
		int i = leia.nextInt();
		leia.nextLine();
		while(i != 0 ) {
			i--;
			String Command = leia.nextLine();
			String clause = Command.substring(3);
			if (Command.substring(0, 2).equals("TT")) {
				String[] Array = GenerateInterface(clause);
				
			} else {
			}			
		}
	}

	private static String[] GenerateInterface(String clause) {
		boolean PExist = false, QExist = false, RExist = false, SExist = false; // Checagem de existência de variáveis
		int[] Opensition = new int[clause.length()];
		String[] ClauseComplexity = new String[clause.length()];
		int k = 0;
		for (int i = 0, j = 0; i < clause.length(); i++) {
			if (clause.charAt(i) == 'P') {
				PExist = true;
			} else if (clause.charAt(i) == 'Q') {
				QExist = true;
			} else if (clause.charAt(i) == 'R') {
				RExist = true;
			} else if (clause.charAt(i) == 'S') {
				SExist = true;
			} else if (clause.charAt(i) == '(') {
				Opensition[j] = i;
				j++;
			} else if (clause.charAt(i) == ')') {
				j--;//Volta uma posição para checar algo que ja foi preenchido, e  coleta seu número para criar uma parte da String
				ClauseComplexity[k] = clause.substring(Opensition[j] + 1, i);
				k++;
			}
		}
		String[] ClauseComplexityOrder = new String[k];
		for (int i = 0; i < k; i++) {// Passar de um array gigante para um array que cabe somente as clausulas que
										// possuo.
			ClauseComplexityOrder[i] = ClauseComplexity[i];
		}
		if (PExist) {
			System.out.print("P ");
		}
		if (QExist) {
			System.out.print("Q ");
		}
		if (RExist) {
			System.out.print("R ");
		}
		if (SExist) {
			System.out.print("S ");
		}
		System.out.print("| ");
		quicksort(ClauseComplexityOrder);
		for (int i = 0; i < ClauseComplexityOrder.length; i++) {
			if (i + 1 < ClauseComplexityOrder.length) {
				System.out.print(ClauseComplexityOrder[i] + "  ");
			} else {
				System.out.print(ClauseComplexityOrder[i]);
			}
		}
		System.out.println();
		return ClauseComplexityOrder;
	}

	// Métodos Já ensinados em sala.
	public static void quicksort(String[] array) {
		sort(array, 0, array.length - 1);
	}

	public static void sort(String[] array, int beg, int end) {
		if (beg < end) {
			int j = separar(array, beg, end);
			sort(array, beg, j - 1);
			sort(array, j + 1, end);
		}
	}

	private static int separar(String[] array, int beg, int end) {
		int i = beg, j = end;
		while (i < j) {
			while (i < end && array[i].length() <= array[beg].length())
				i++;
			while (j > beg && array[j].length() >= array[beg].length())
				j--;
			if (i < j) {
				trocar(array, i, j);
				i++;
				j--;
			}
		}
		trocar(array, beg, j);
		return j;
	}

	private static void trocar(String[] array, int i, int j) {
		String aux = array[i];
		array[i] = array[j];
		array[j] = aux;

	}
}
