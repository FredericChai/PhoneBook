package ECB18S1;

import java.util.*;

/**
 * 
 * This class is a tool class to sort the given phone book list consist of
 * personal detail class using Most Significant Digit First(MSD) algorithm and
 * insertion sort algorithm.
 * 
 * @author chaizhizhi
 */

public class QuerySort {
	private static String alphabet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
	private static PersonalDetail[] aux;
	private static int R = alphabet.length();

	/**
	 * Sort the list according to name.
	 * 
	 * @param query
	 *            The list consist of personalDetail object
	 */
	public static void MSDsort(ArrayList<PersonalDetail> query) {
		int N = query.size();
		aux = new PersonalDetail[N];
		sort(query, 0, N - 1, 0);
	}

	/**
	 * 
	 * Get the index of the given letter in the string.
	 * 
	 * @param s
	 *            The string field of personalDetail object
	 * @param d
	 *            The index required to get the word of that field
	 * @return The index of the specific letter from given string.
	 */
	private static int charAt(String s, int d) {
		if (d < s.length())
			return alphabet.indexOf(s.charAt(d));
		else
			return -1;
	}

	/**
	 * 
	 * Main body of MSD algorithm.
	 * 
	 * @param query
	 *            The personalDetail list to sort
	 * @param lo
	 *            The start index
	 * @param hi
	 *            The end index
	 * @param d
	 *            Arguments for iteration
	 */
	public static void sort(ArrayList<PersonalDetail> query, int lo, int hi, int d) {
		// exit of the recursive
		if (hi <= lo)
			return;
		int count[] = new int[R + 2];
		// Count the frequency of this elements
		for (int i = lo; i <= hi; i++)
			count[charAt(query.get(i).getName(), d) + 2]++;
		// Convert the frequency into index
		for (int i = 0; i < R + 1; i++)
			count[i + 1] += count[i];
		// Classified all the elements
		for (int i = lo; i <= hi; i++)
			aux[count[charAt(query.get(i).getName(), d) + 1]++] = query.get(i);
		// Rewrite the temporary sort results into previous array
		for (int i = lo; i <= hi; i++)
			query.set(i, aux[i - lo]);
		// Recursive
		for (int i = 0; i < R; i++)
			sort(query, lo + count[i], lo + count[i + 1] - 1, d + 1);
	}

	/**
	 * 
	 * Check whether date_A is bigger than date_B.
	 * 
	 * @param date_A
	 *            date wait to compare
	 * @param date_B
	 *            date wait to compare
	 * @return Whether date_A is bigger than date_B
	 */
	private static boolean dateCompare(String date_A, String date_B) {
		// Use .split("-") because the dates are separated by a dash
		String[] dateA = date_A.split("-");
		String[] dateB = date_B.split("-");
		// Reverse their order to compare them
		String A = dateA[2] + dateA[1] + dateA[0];
		String B = dateB[2] + dateB[1] + dateB[0];
		if (A.compareTo(B) == -1) {
			return false;
		}
		return true;
	}

   /**插入排序算法
    */
	public static void insertSort(ArrayList<PersonalDetail> arr) {
		//排序下标，根据个人信息的
		int j;
		// t用于存储找到比现在的大的直
		PersonalDetail t;
		for (int i = 1; i < arr.size(); i++) {
			//datecompare用于对比前面是否比后面的大
			if (dateCompare(arr.get(i - 1).getBirthday(), arr.get(i).getBirthday())) {
				t = arr.get(i);
				for (j = i - 1; j >= 0 && dateCompare(arr.get(j).getBirthday(), t.getBirthday()); j--) {
					arr.set(j + 1, arr.get(j));
				}
				arr.set(j + 1, t);
			}
		}
	}

}
