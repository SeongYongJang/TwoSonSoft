package com.twosonsoft.opensource.comp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 문자열 비교 : 최장 공통 문자열 추출 알고리즘
 */
final class UtilSuffixAutomaton
{
	State[] st;
	int size, last, lastp;

	void buildSA(String s)
	{
		int n = s.length();
		st = new State[Math.max(2, 2 * n - 1)];
		st[0] = new State();
		st[0].link = -1;
		st[0].endpos = -1;
		last = 0;
		size = 1;
		// st[] insert

		for (char x : s.toCharArray())
			saExtend(x);
		int bb = 0;
		for (State x : st)
			if (x != null)
				bb++;
		for (int i = 1; i < size; i++)
			st[st[i].link].ilink.add(i);
	}

	void saExtend(char c)
	{
		int nlast = size++;
		st[nlast] = new State();
		st[nlast].length = st[last].length + 1;
		st[nlast].endpos = st[last].length;
		int p;
		for (p = last; p != -1 && st[p].next[c] == -1; p = st[p].link)
			st[p].next[c] = nlast;

		if (p == -1)
			st[nlast].link = 0;

		else
		{ // 중복된 문자 찾기
			int q = st[p].next[c];
			if (st[p].length + 1 == st[q].length)
				st[nlast].link = q;
			else
			{
				int clone = size++;
				st[clone] = new State();
				st[clone].length = st[p].length + 1;
				st[clone].next = st[q].next.clone();
				st[clone].link = st[q].link;
				for (; p != -1 && st[p].next[c] != -1 && st[p].next[c] == q; p = st[p].link)
				{
					st[p].next[c] = clone;
				}
				st[q].link = clone;
				st[nlast].link = clone;
				st[clone].endpos = -1;
			}
		}
		last = nlast;
	}

	public String lcs(String a, String b)
	{
		buildSA(a);
		int p = 0;
		lastp = 0;
		int len = 0;
		int best = 0;
		int bestpos = -1;

		for (int i = 0; i < b.length(); ++i)
		{
			char cur = b.charAt(i);
			if (st[p].next[cur] == -1)
			{
				for (; p != -1 && st[p].next[cur] == -1; p = st[p].link)
				{
				}
				if (p == -1)
				{
					p = 0;
					len = 0;
					continue;
				}
				len = st[p].length;
			}
			++len;

			p = st[p].next[cur];
			if (best < len)
			{
				best = len;
				bestpos = i;
				lastp = p;
			}
		}
		return b.substring(bestpos - best + 1, bestpos + 1);
	}

	static class State
	{
		int length, link, endpos;
		int[] next = new int[256];
		{
			Arrays.fill(next, -1);
		}
		List<Integer> ilink = new ArrayList<Integer>(0);
	};
}
