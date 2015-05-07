using System.Text;

namespace paa5
{
	public class Literal
	{
		public bool Negative { get; private set; }

		public int Index { get; private set; }

		public int Weight { get; private set; }

		public Literal(int index, int weight, bool negative)
		{
			this.Index = index;
			this.Weight = weight;
			this.Negative = negative;
		}

		public override string ToString()
		{
			StringBuilder sb = new StringBuilder();
			if (Negative)
			{
				sb.Append('-');
			}
			sb.Append(Index);
			return sb.ToString();
		}
	}
}
