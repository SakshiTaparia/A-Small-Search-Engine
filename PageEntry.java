import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.Scanner;


class PageEntry
{
	public String name;
	public PageIndex index = new PageIndex();
	public double wordcount = 0.0;
	public AVLtree_PageEntry PageAVLtree = new AVLtree_PageEntry();

// Constructor method
	PageEntry (String name) throws Exception
	{

// The argument is the name of the document
		this.name = name;
		File page = new File("webpages/"+ name);
		Scanner scanPage = new Scanner(page);

		String EditedLine;
		Integer PositionCount = 1;

// Creating the page Index corresponding to the file
		while(scanPage.hasNextLine())
			{
				EditedLine = this.changeline(scanPage.nextLine());

				String LineWords[] = EditedLine.split(" ");
				
				for(int j = 0; j< LineWords.length ; j++)
				{
					if(LineWords[j].equals(""))
					{}

					else if (LineWords[j].equals("a") || LineWords[j].equals("an") || LineWords[j].equals("the") || LineWords[j].equals("they") || 
						LineWords[j].equals("they") || LineWords[j].equals("this") || LineWords[j].equals("for") || LineWords[j].equals("is") || 
						LineWords[j].equals("are") || LineWords[j].equals("was") || LineWords[j].equals("of") || LineWords[j].equals("or") || 
						LineWords[j].equals("and") || LineWords[j].equals("does") || LineWords[j].equals("will") || LineWords[j].equals("whose"))
					{
						PositionCount ++;
					}

					else
					{
						Position position = new Position(this, PositionCount);
						wordcount ++;
						index.addPositionForWord(LineWords[j], position);
						PageAVLtree.insert( LineWords[j], PositionCount);
						PositionCount ++;
					}
				}
			}

		scanPage.close();
	}

// Replace the plurals with their singular forms and remove the punctuations
	public String changeline(String line)
	{
		line = line.toLowerCase();
		line = line.replaceAll("[^a-zA-Z_0-9@%\\+\\$\\^\\&\\*/\\|]", " ");
		line = line.trim();

		line = line.replaceAll("structures", "structure");
		line = line.replaceAll("stacks", "stack");
		line = line.replaceAll("applications", "application");

		return line;
	}

// Used in term frequency
	public double NumberOfWordsInPage()
	{
		return wordcount;
	}

	PageIndex getPageIndex()
	{
		return index;
	}

	public double NumberOfPhraseOccurences (String str[])
	{
		double answer = 0.0;

		Myset <Position> PositionOfFirstWord = index.WordListOfAPage.GetMemberWord(str[0]).GetPositionsInPage(this);
		MyLinkedListNode <Position> head = PositionOfFirstWord.set.gethead();

		Integer i;

		while (head != null)
		{
			AVLtreeNode_PageEntry FirstNode = PageAVLtree.FindElement(head.getelement().wordIndex); 
			for (i = 1; i < str.length; i++)
			{
				if (PageAVLtree.GetInOrderSuccessor(FirstNode).word .equals(str[i]))
					FirstNode = PageAVLtree.GetInOrderSuccessor(FirstNode);

				else 
					break;
			}

			if (i == str.length)
			answer ++;

			head = head.getnext();
		}

		return answer;
	}

	double getRelevanceOfPage(String[] str, boolean doTheseWordsRepresentAPhrase, InvertedPageIndex I)
	{
		double answer = 0.0;

		if (doTheseWordsRepresentAPhrase == false)
		{
			for (Integer i=0; i<str.length; i++)
			{
				if (index.WordListOfAPage.GetMemberWord(str[i]) == null)
				{}

			    else
				answer = answer + (index.WordListOfAPage.GetMemberWord(str[i])).getTermFrequency(this)*(I.InverseDocumentFrequency(str[i]));
			}

			return answer;
		}

		else
		{
			double stringlength = (double) (str.length - 1);
			double PhraseFrequency = this.NumberOfPhraseOccurences(str)/(wordcount - stringlength*this.NumberOfPhraseOccurences(str));
			answer = PhraseFrequency*(I.InverseDocumentFrequencyForPhrase(str));
			return answer;
		}
	}
}
