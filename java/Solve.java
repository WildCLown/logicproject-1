public class Solve {
	public static void main(String[] args) {
		boolean[] well = { true, false, true, true };
		String[] Clause = { "~Q", "~Q < S" };
		doSolve(well, Clause);
	}

	private static void doSolve(boolean[] well, String[] Clause) {
		int i = 0;
		boolean PExist = false, QExist = false, RExist = false, SExist = false;
		for (int j = 0; j < well.length; j++) {
			if (well[j]) {
				i++;
				if (j == 0) {
					PExist = true;
				} else if (j == 1) {
					QExist = true;
				} else if (j == 2) {
					RExist = true;
				} else if (j == 3) {
					SExist = true;
				}
			}
		}
		boolean check[] = new boolean[i];
		int k = (int) Math.pow(2, i);
		for (int j = 0; j < k; j++) {
			System.out.println(Arrays.toString(check));
			if(j != k-1) {
				for (int p = 0; p < i; p++) {
					if (check[(i - 1) - p] == true) {
						check[(i - 1) - p] = false;
					} else if (check[(i - 1) - p] == false) {
						check[(i - 1) - p] = true;
						break;
					}
				}
			}
		}
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
}
