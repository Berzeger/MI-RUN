using System;
using System.IO;

namespace paa5
{
	class OutputWriter
	{
		public static void PrintOutput(string output, StreamWriter file)
		{
			Console.WriteLine(output);
			file.WriteLine(output);
			file.Flush();
		}
	}
}
