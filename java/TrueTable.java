import java.util.Arrays;
import java.util.Scanner;

public class TrueTable {
	public static void main(String[] args) {
		Scanner leia = new Scanner(System.in);
		int i = leia.nextInt();
		leia.nextLine();
		for (int j = 0; j < i; j++) {
			String Command = leia.nextLine();
			String clause = Command.substring(3);
			if (Command.substring(0, 2).equals("TT")) {
				System.out.println("Problema #" + (j + 1));
				String[] Array = GenerateInterface(clause);
				if (Array.length == 1) {
					System.out.println("0 |");
					System.out.println("1 |");
					System.out.println("Sim, é satisfatível.");
				} else {
					boolean[] PQRS = checkClauses(Array);
					doSolve(PQRS, Array);
				}
				System.out.println();
			} else {
			}
		}
	}

	private static boolean[] checkClauses(String[] array) { // Método utilizado para saber quais variáveis possuo,
															// devolve um array de boolean que diz quais variáveis
															// atômicas possui
		boolean control = true; // variável de controle para não checar variáveis não atômicas
		boolean[] PQRS = new boolean[4];
		int index = 0;
		while (control) {
			if (array[index].length() == 1) {
				if (array[index].equals("P")) {
					PQRS[0] = true;
				} else if (array[index].equals("Q")) {
					PQRS[1] = true;
				} else if (array[index].equals("R")) {
					PQRS[2] = true;
				} else if (array[index].equals("S")) {
					PQRS[3] = true;
				}
				index++;
			} else {
				control = false;
			}
		}
		return PQRS;
	}

	private static String[] GenerateInterface(String clause) {
		boolean PExist = false, QExist = false, RExist = false, SExist = false; // Checagem de existência de variáveis
		int[] Opensition = new int[clause.length()];
		String[] ClauseComplexity = new String[clause.length()];
		int k = 0, control = 0;
		for (int i = 0, j = 0; i < clause.length(); i++) {
			if (clause.charAt(i) == 'P' && !PExist) { // Esse and seria para ele entrar nessa condição somente uma vez,
														// fazendo com que o control não seja somado multiplas vezes
				PExist = true;
				control++;
			} else if (clause.charAt(i) == 'Q' && !QExist) {
				QExist = true;
				control++;
			} else if (clause.charAt(i) == 'R' && !RExist) {
				RExist = true;
				control++;
			} else if (clause.charAt(i) == 'S' && !SExist) {
				SExist = true;
				control++;
			} else if (clause.charAt(i) == '(') {
				Opensition[j] = i;
				j++;
			} else if (clause.charAt(i) == ')') {
				j--;// Volta uma posição para checar algo que ja foi preenchido, e coleta seu número
					// para criar uma parte da String
				ClauseComplexity[k] = clause.substring(Opensition[j] + 1, i);
				k++;
			}
		}
		String[] ClauseComplexityOrder = new String[k + control];
		for (int i = 0; i < k; i++) {// Passar de um array gigante para um array que cabe somente as clausulas que
										// possuo.
			ClauseComplexityOrder[i] = ClauseComplexity[i];
		}
		if (PExist) {
			System.out.print("P ");
			ClauseComplexityOrder[k + control - 1] = "P";
			control--;
		}
		if (QExist) {
			System.out.print("Q ");
			ClauseComplexityOrder[k + control - 1] = "Q";
			control--;
		}
		if (RExist) {
			System.out.print("R ");
			ClauseComplexityOrder[k + control - 1] = "R";
			control--;
		}
		if (SExist) {
			System.out.print("S ");
			ClauseComplexityOrder[k + control - 1] = "S";
			control--;
		}
		System.out.print("| ");
		quicksort(ClauseComplexityOrder);
		for (int i = 0; i < ClauseComplexityOrder.length; i++) {
			if (ClauseComplexityOrder[i].length() > 1) {
				if (i + 1 < ClauseComplexityOrder.length) {
					System.out.print(ClauseComplexityOrder[i] + "  ");
				} else {
					System.out.print(ClauseComplexityOrder[i]);
				}
			}
		}
		System.out.println();
		return ClauseComplexityOrder;
	}

	private static void doSolve(boolean[] well, String[] Clause) {
		int i = 0;
		int[] ClauseAnswer = new int[Clause.length];
		for (int j = 0; j < well.length; j++) {// neste passo, é checado a existência de variáveis atômicas
			if (well[j]) {
				i++;
			}
		}
		boolean check[] = new boolean[i];
		int k = (int) Math.pow(2, i); // o total de vezes que devo checar, para ter todas as opções
		boolean sat = false;
		for (int j = 0; j < k; j++) {
			for (int pei = 0; pei < i; pei++) { // somente dar as possibilidades, apresentando na interface
				if (check[pei]) {
					System.out.print("1");
				} else {
					System.out.print("0");
				}
				if (pei < i - 1) {
					System.out.print(" ");
				} else {
					System.out.print(" | ");
				}
			}
			int satAnswer = 0;
			for (int index = check.length; index < Clause.length; index++) {
				satAnswer = answerSolve(check, Clause[index], well, ClauseAnswer, index, Clause);
				ClauseAnswer[index] = satAnswer;
				if (index < Clause.length - 1) {
					System.out.print("  ");
				} else {
					if (satAnswer == 1) {
						sat = true;
					}
				}

			}
			System.out.println();
			if (j != k - 1) { // realizar a mudança de variáveis, e também um controle para caso todas estejam
								// true
				for (int p = 0; p < i; p++) {
					if (check[(i - 1) - p] == true) {
						check[(i - 1) - p] = false;
					} else if (check[(i - 1) - p] == false) {
						check[(i - 1) - p] = true;
						break;
					}
				}
			} else {
				if (sat) {
					System.out.println("Sim, é satisfatível.");
				} else {
					System.out.println("Não, não é satisfatível.");
				}
			}
		}

	}

	private static int answerSolve(boolean[] check, String Clause, boolean[] vars, int[] ClauseAnswer, int index,
			String[] Clauses) {
		int P = 0;
		int Q = 1;
		int R = 2;
		int S = 3;
		for (int i = 0; i < vars.length; i++) {
			if (!vars[i]) {
				if (i == 0) {
					Q--;
					R--;
					S--;
				} else if (i == 1) {
					R--;
					S--;
				} else if (i == 2) {
					S--;
				}
			}
		}
		int size = Clause.length() - 1;
		int answer = 0;

		if (Clause.contains("(")) {
			boolean controlled = false;
			while (!controlled) {
				Clause = changeClause(Clause, ClauseAnswer, index - 1, Clauses);
				if (Clause.length() <= 5) {
					controlled = true;
				}
			}
		}
		if (Clause.length() == 2) {
			if (Clause.charAt(1) == 'P') {
				answer = not(check[P]);
			} else if (Clause.charAt(1) == 'Q') {
				answer = not(check[Q]);
			} else if (Clause.charAt(1) == 'R') {
				answer = not(check[R]);
			} else if (Clause.charAt(1) == 'S') {
				answer = not(check[S]);
			} else if (Clause.charAt(1) == '0') {
				answer = 1;
			} else if (Clause.charAt(1) == '1') {
				answer = 0;
			}
		}
		if (Clause.length() == 5) {
			if (Clause.charAt(2) == '&') { // para poder checar sua devida operação, em uma string de lenght 5, seu
											// operador está no 2, então é possível associar sua devida operação
				if (Clause.charAt(0) == '1' && Clause.charAt(4) == '1') {
					answer = 1;
				} else if (Clause.charAt(0) == '1' || Clause.charAt(4) == '1') {
					if (Clause.charAt(0) == 'P' || Clause.charAt(4) == 'P') {
						answer = and(check[P], true);
					} else if (Clause.charAt(0) == 'Q' || Clause.charAt(4) == 'Q') {
						answer = and(check[Q], true);
					} else if ((Clause.charAt(0) == 'R' || Clause.charAt(4) == 'R')) {
						answer = and(check[R], true);
					} else if (Clause.charAt(0) == 'S' || Clause.charAt(4) == 'S') {
						answer = and(check[S], true);
					}
				} else if (Clause.charAt(0) == '0' || Clause.charAt(4) == '0') {
					answer = 0;
				} else {
					int first = 0, second = 0; // Para poder associar a seu devido index em check, e realizar o metodo
					if (Clause.charAt(0) == 'P') {
						first = P;
					} else if (Clause.charAt(0) == 'Q') {
						first = Q;
					} else if (Clause.charAt(0) == 'R') {
						first = R;
					} else if (Clause.charAt(0) == 'S') {
						first = S;
					}
					if (Clause.charAt(4) == 'P') {
						second = P;
					} else if (Clause.charAt(4) == 'Q') {
						second = Q;
					} else if (Clause.charAt(4) == 'R') {
						second = R;
					} else if (Clause.charAt(4) == 'S') {
						second = S;
					}
					answer = and(check[first], check[second]);
				}
			} else if (Clause.charAt(2) == 'v') {
				if (Clause.charAt(0) == '1' || Clause.charAt(4) == '1') {
					answer = 1;
				} else if (Clause.charAt(0) == '0' && Clause.charAt(4) == '0') {
					answer = 0;
				} else if (Clause.charAt(0) == '0' || Clause.charAt(4) == '0') {
					if (Clause.charAt(0) == 'P' || Clause.charAt(4) == 'P') {
						answer = or(check[P], false);
					} else if (Clause.charAt(0) == 'Q' || Clause.charAt(4) == 'Q') {
						answer = or(check[Q], false);
					} else if ((Clause.charAt(0) == 'R' || Clause.charAt(4) == 'R')) {
						answer = or(check[R], false);
					} else if (Clause.charAt(0) == 'S' || Clause.charAt(4) == 'S') {
						answer = or(check[S], false);
					}
				} else {
					int first = 0, second = 0; // Para poder associar a seu devido index em check, e realizar o metodo
					if (Clause.charAt(0) == 'P') {
						first = P;
					} else if (Clause.charAt(0) == 'Q') {
						first = Q;
					} else if (Clause.charAt(0) == 'R') {
						first = R;
					} else if (Clause.charAt(0) == 'S') {
						first = S;
					}
					if (Clause.charAt(4) == 'P') {
						second = P;
					} else if (Clause.charAt(4) == 'Q') {
						second = Q;
					} else if (Clause.charAt(4) == 'R') {
						second = R;
					} else if (Clause.charAt(4) == 'S') {
						second = S;
					}
					answer = or(check[first], check[second]);
				}
			} else if (Clause.charAt(2) == '>') {
				if (Clause.charAt(0) == '0' || Clause.charAt(4) == '1') {
					answer = 1;
				} else if (Clause.charAt(0) == '1') {
					if (Clause.charAt(4) == 'P') {
						answer = implication(true, check[P]);
					} else if (Clause.charAt(4) == 'Q') {
						answer = implication(true, check[Q]);
					} else if (Clause.charAt(4) == 'R') {
						answer = implication(true, check[R]);
					} else if (Clause.charAt(4) == 'S') {
						answer = implication(true, check[S]);
					} else if (Clause.charAt(4) == '1') {
						answer = 1;
					}

				} else if (Clause.charAt(4) == '0') {
					if (Clause.charAt(0) == 'P') {
						answer = implication(check[P], false);
					} else if (Clause.charAt(0) == 'Q') {
						answer = implication(check[Q], false);
					} else if (Clause.charAt(0) == 'R') {
						answer = implication(check[R], false);
					} else if (Clause.charAt(0) == 'S') {
						answer = implication(check[S], false);
					}

				} else {
					int first = 0, second = 0; // Para poder associar a seu devido index em check, e realizar o metodo
					if (Clause.charAt(0) == 'P') {
						first = P;
					} else if (Clause.charAt(0) == 'Q') {
						first = Q;
					} else if (Clause.charAt(0) == 'R') {
						first = R;
					} else if (Clause.charAt(0) == 'S') {
						first = S;
					}
					if (Clause.charAt(4) == 'P') {
						second = P;
					} else if (Clause.charAt(4) == 'Q') {
						second = Q;
					} else if (Clause.charAt(4) == 'R') {
						second = R;
					} else if (Clause.charAt(4) == 'S') {
						second = S;
					}
					answer = implication(check[first], check[second]);
				}
			} else if (Clause.charAt(2) == '<') {
				if ((Clause.charAt(0) == '0' && Clause.charAt(4) == '0')
						|| (Clause.charAt(0) == '1' && Clause.charAt(4) == '1')) {
					answer = 1;
				} else if (Clause.charAt(0) == '0' || Clause.charAt(4) == '0') {
					if (Clause.charAt(0) == 'P' || Clause.charAt(4) == 'P') {
						answer = equivalent(check[P], false);
					} else if (Clause.charAt(0) == 'Q' || Clause.charAt(4) == 'Q') {
						answer = equivalent(check[Q], false);
					} else if ((Clause.charAt(0) == 'R' || Clause.charAt(4) == 'R')) {
						answer = equivalent(check[R], false);
					} else if (Clause.charAt(0) == 'S' || Clause.charAt(4) == 'S') {
						answer = equivalent(check[S], false);
					}
				} else if (Clause.charAt(0) == '1' || Clause.charAt(4) == '1') {
					if (Clause.charAt(0) == 'P' || Clause.charAt(4) == 'P') {
						answer = equivalent(check[P], true);
					} else if (Clause.charAt(0) == 'Q' || Clause.charAt(4) == 'Q') {
						answer = equivalent(check[Q], true);
					} else if ((Clause.charAt(0) == 'R' || Clause.charAt(4) == 'R')) {
						answer = equivalent(check[R], true);
					} else if (Clause.charAt(0) == 'S' || Clause.charAt(4) == 'S') {
						answer = equivalent(check[S], true);
					}
				} else {
					answer = 0;
				}
			}
		}

		for (int j = 0; j < size; j++) {
			System.out.print(" ");
		}
		System.out.print(answer);
		return answer;
	}

	private static String changeClause(String clause, int[] clauseAnswer, int i, String[] Clauses) {
		String theClause = "";
		String aux = "";
		boolean found = false;
		for (int j = i; j >= 0 && !found; j--) {
			if (clause.contains("(" + Clauses[j] + ")")) {
				aux = "(" + Clauses[j] + ")";
				theClause = clause.replace(aux, Integer.toString(clauseAnswer[j]));
				found = true;
			}
		}
		return theClause;
	}

	private static int not(boolean var) {
		if (var) {
			return 0;
		} else {
			return 1;
		}
	}

	private static int or(boolean var1, boolean var2) {
		if (var1 || var2) {
			return 1;
		} else {
			return 0;
		}
	}

	private static int and(boolean var1, boolean var2) {
		if (var1 && var2) {
			return 1;
		} else {
			return 0;
		}
	}

	private static int implication(boolean var1, boolean var2) {
		if (var2 || var1 == var2) {
			return 1;
		} else {
			return 0;
		}
	}

	private static int equivalent(boolean var1, boolean var2) {
		if (var1 == var2) {
			return 1;
		} else {
			return 0;
		}
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
