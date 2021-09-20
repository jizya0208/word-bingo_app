import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static int inputInteger(Scanner sc, Function<Integer, Boolean> isValid) {
        while (true) {
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                if (isValid.apply(value)) {
                    return value;
                }
            } else {
                sc.next();
            }
        }
    }

	public static void main(String[] args) {
		int bingo = 0;
		//標準入力でビンゴの行列数を決定
        Scanner sc = new Scanner(System.in);
        int size = inputInteger(sc, value ->  1000 >= value && value >= 3);
		//2次元配列の宣言
		String [][] sheetWords = new String[size][size];
		for (int n = 0; n < size; n++) {
			Scanner inputWord = new Scanner(System.in);
			String[] words = inputWord.nextLine().split(" ");
			for (int m = 0; m < size; m++) {
				sheetWords[n][m] = words[m];
			}
		}
 		//縦横斜めの列を抽出
		HashMap<Integer, ArrayList<String>> rowLines = new HashMap<>();
		HashMap<Integer, ArrayList<String>> colLines = new HashMap<>();
		ArrayList<String> rowWords = new ArrayList<>();
		ArrayList<String> colWords = new ArrayList<>();
		ArrayList<String> rowLine = new ArrayList<String>();
		ArrayList<String> colLine = new ArrayList<String>();
		ArrayList<String> backSlashLine = new ArrayList<String>();
		ArrayList<String> slashLine = new ArrayList<String>();
        for (int col = 0; col < size; col++) {
        	for (int row = 0; row < size; row++) {
        		colWords.add(sheetWords[row][col]);
        		rowWords.add(sheetWords[col][row]);
        		if (row == col) {
        			backSlashLine.add(sheetWords[row][col]);
        		}
        		if (row == size - col - 1) {
        			slashLine.add(sheetWords[row][col]);
        		}
        	}
            ArrayList<String> tempCol = new ArrayList<String>(colWords);
            ArrayList<String> tempRow = new ArrayList<String>(rowWords);
        	colLines.put(col, tempCol);
        	rowLines.put(col, tempRow);
        	colWords.clear();
        	rowWords.clear();
        }

		//ビンゴシートと突合する単語
        Scanner n = new Scanner(System.in);
        int N = inputInteger(n, value ->  value <= 2000);
		List<String> validWords = new ArrayList<String>();
		for (int i = 0; i < N; i++) {
			Scanner vw = new Scanner(System.in);
			String validWord = vw.next();
			if (validWord.length() > 100 || validWords.contains(vw)) {
				vw.next();
			}
			validWords.add(validWord);
		}
		//いずれかのビンゴ列の要素全てがヒットすればyesを返す
		if (validWords.containsAll(backSlashLine) || (validWords.containsAll(slashLine))) {
			System.out.println("yes");
			System.exit(0);
		}
		for (int i = 0; i < size; i++) {
			rowLine = rowLines.get(i);
			colLine = colLines.get(i);
			if (validWords.containsAll(rowLine) || validWords.containsAll(colLine)) {
				System.out.println("yes");
				System.exit(0);
			}
			rowLine.clear();
			colLine.clear();
		}
		System.out.println("no");
	}
}