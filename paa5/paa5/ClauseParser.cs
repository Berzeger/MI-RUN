using System;

namespace paa5
{
	class ClauseParser
	{
		private int[] weights;

		public ClauseParser(int[] weights)
		{
			this.weights = weights;
		}

		public Clause Parse (string clause)
		{
			string[] clauseParts = clause.Split(' ');
			int index1 = int.Parse(clauseParts[0]);
			int index2 = int.Parse(clauseParts[1]);
			int index3 = int.Parse(clauseParts[2]);

			return new Clause(Math.Abs(index1), weights[Math.Abs(index1) - 1], index1 < 0 ? true : false,
							  Math.Abs(index2), weights[Math.Abs(index2) - 1], index2 < 0 ? true : false,
							  Math.Abs(index3), weights[Math.Abs(index3) - 1], index3 < 0 ? true : false);
		} 
	}
}
