using System.IO;

namespace paa5
{
	class GnuplotOutput
	{
		public static void Output(long iteration, double value, StreamWriter file)
		{
			file.WriteLine(iteration + " " + value);
			file.Flush();
		}
	}
}
