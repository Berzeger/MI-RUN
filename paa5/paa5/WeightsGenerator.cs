using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace paa5
{
	class WeightsGenerator
	{
		private static IList<string> files;

		private static Random rand;

		public static void Generate(string path)
		{
			rand = new Random();
			files = new List<string>();
			files = Directory.GetFiles(path, "*.cnf").ToList();
			Console.WriteLine("Got " + files.Count + " files.");
			Generate();
		}

		private static void Generate()
		{
			while (files.Count > 0)
			{
				var text = File.ReadAllText(files[0]);

				string[] lines = text.Split(new string[] { "\r\n", "\n" }, StringSplitOptions.None);
				int varCount = 0;
				int index = 0;

				for (int i = 0; i < lines.Length; i++)
				{
					if (lines[i].StartsWith("p cnf"))
					{
						var symbols = lines[i].Split(' ');
						varCount = int.Parse(symbols[2]);
						index = i;
						break;
					}					
				}

				if (varCount != 0)
				{
					var list = lines.ToList();
					var weights = new List<int>();

					for (int i = 0; i < varCount; i++)
					{
						weights.Add(rand.Next(10));
					}

					StringBuilder sb = new StringBuilder();
					sb.Append("w ");

					for (int i = 0; i < varCount; i++)
					{
						sb.Append(weights.ElementAt(i));
						if (i < varCount - 1)
						{
							sb.Append(" ");
						}
					}

					list.Insert(index + 1, sb.ToString());
					var arr = list.ToArray();
					string result = String.Join(System.Environment.NewLine, arr);
					File.WriteAllText(files[0], result);
				}

				files.RemoveAt(0);
			}
		}
	}
}
