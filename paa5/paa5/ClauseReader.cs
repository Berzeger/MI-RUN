using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace paa5
{
	class ClauseReader
	{
		private string path;
		private readonly IList<string> files;
		private StreamReader fileContent;
		private StreamWriter outputFile;
		private double clauseToFormulaRatio;
		private int clauseCount;
		private int clausesNumber;
		private int clauseLimit;

		public ClauseReader(double ratio)
		{
			Console.WriteLine("Type in a path with problems.");
			//path = Console.ReadLine();
			path = @"C:\dev\paa\5\";
			files = new List<string>();
			files = Directory.GetFiles(path, "*.cnf").ToList();
			Console.WriteLine("Got " + files.Count + " files.");
			clauseCount = 0;
			clauseToFormulaRatio = ratio;
		}

		public void SetOutputFile(StreamWriter of)
		{
			outputFile = of;
		}

		public string GetPath()
		{
			return path;
		}

		public int GetVariablesCount()
		{
			if (PrepareReader() == "end")
			{
				return -1;
			}
			string line;

			do
			{
				line = fileContent.ReadLine();
			} while (line[0] != 'p');

			string[] split = line.Split(' ');
			clausesNumber = int.Parse(split[4]);
			int literals = int.Parse(split[2]);
			clauseLimit = (int)(clauseToFormulaRatio*literals);
			return literals;
		}

		public int[] GetWeights()
		{
			if (PrepareReader() == "end")
			{
				return null;
			}
			string line;

			do
			{
				line = fileContent.ReadLine();
			} while (line[0] != 'w');

			string[] split = line.Split(' ');
			int[] weights = new int[split.Length - 1]; // -1 because we need not to count the 'w' symbol

			for (int i = 1; i < split.Length; i++)
			{
				weights[i - 1] = int.Parse(split[i]);
			}

			return weights;
		}

		public string ReadNextClause()
		{
			if (clauseCount < clauseLimit)
			{
				string prepared = PrepareReader();
				if (prepared == "ok")
				{
					clauseCount++;
					var line = fileContent.ReadLine();
					if (line[0] == ' ')
					{
						line = line.Remove(0, 1);
					}
					return line;
				}
				else if (prepared == "end")
				{
					return "end";
				}
				else
				{
					return prepared;
				}
			}
			else
			{
				fileContent.Close();
				fileContent = null;
				clauseCount = 0;
				return PrepareReader();
			}
		}

		private string PrepareReader()
		{
			if (fileContent != null && (fileContent.Peek() == -1 || fileContent.Peek() == '%'))
			{
				fileContent.Close();
				fileContent = null;
			}

			if (fileContent == null)
			{
				clauseCount = 0;
				if (files.Count > 0)
				{
					//OutputWriter.PrintOutput("----- " + files[0] + " -----", outputFile);
					fileContent = new StreamReader(files[0]);
					string ret = files[0];
					files.RemoveAt(0);
					return "----- " + ret + " -----";
				}
				else
				{
					return "end"; // end of the file
				}
			}

			return "ok"; // you can read from the file
		}

		// For testing purposes
		public void PrintFilesNames()
		{
			Console.WriteLine("Printing files in " + path);
			Console.WriteLine("-------------------------------------");

			foreach (string file in files)
			{
				Console.WriteLine(file);
			}
		}
	}
}
