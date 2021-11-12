package com.motamedi.blocks_problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class MainClass {

	public static int a;
	public static int b;
	public static String command;
	public static int numberOfBlocks;
	public static Stack<Integer> blocks[];
	public static int position[];

	public static void main(String[] args) throws IOException {

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			numberOfBlocks = Integer.parseInt(input.readLine());

			blocks = new Stack[numberOfBlocks];
			position = new int[numberOfBlocks];

			for (int i = 0; i < numberOfBlocks; i++) {
				blocks[i] = new Stack<Integer>();
				blocks[i].push(i);
				position[i] = i;
			}

			command = "";
			while (!(command = input.readLine()).equals("quit")) {

				StringTokenizer token = new StringTokenizer(command);
				String verb = token.nextToken();
				a = Integer.parseInt(token.nextToken());
				String preposition = token.nextToken();
				b = Integer.parseInt(token.nextToken());

				if (a == b || position[a] == position[b])
					continue;

				if (verb.equals("move")) {
					if (preposition.equals("onto")) {
						moveOnto(a, b);
					} else if (preposition.equals("over")) {
						moveOver(a, b);
					}
				} else if (verb.equals("pile")) {
					if (preposition.equals("onto")) {
						pileOnto(a, b);
					} else if (preposition.equals("over")) {
						pileOver(a, b);
					}
				}
			}

			for (int i = 0; i < blocks.length; i++)
				System.out.println(printBlockStatus(i));
			
		} catch (Exception e) {
			System.out.println("Invalid Command");
		}
	}

	public static void moveOnto(int first, int second) {
		clearUp(second);
		moveOver(first, second);
	}

	public static void moveOver(int first, int second) {
		clearUp(first);
		blocks[position[second]].push(blocks[position[first]].pop());
		position[first] = position[second];
	}

	public static void pileOnto(int first, int second) {
		clearUp(second);
		pileOver(first, second);
	}

	public static void pileOver(int first, int second) {
		Stack<Integer> pile = new Stack<Integer>();
		while (blocks[position[first]].peek() != first) {
			pile.push(blocks[position[first]].pop());
		}
		pile.push(blocks[position[first]].pop());
		while (!pile.isEmpty()) {
			int temp = pile.pop();
			blocks[position[second]].push(temp);
			position[temp] = position[second];
		}
	}

	public static void clearUp(int block) {
		while (blocks[position[block]].peek() != block) {
			init(blocks[position[block]].pop());
		}
	}

	public static void init(int block) {
		while (!blocks[block].isEmpty()) {
			init(blocks[block].pop());
		}
		blocks[block].push(block);
		position[block] = block;
	}

	public static String printBlockStatus(int index) {
		String blockStatus = "";
		while (!blocks[index].isEmpty())
			blockStatus = " " + blocks[index].pop() + blockStatus;
		blockStatus = index + ":" + blockStatus;
		return blockStatus;
	}

}
