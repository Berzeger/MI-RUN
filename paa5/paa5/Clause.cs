using System.Linq;
using System.Text;

namespace paa5
{
	public class Clause
	{
		private Literal[] literals;

		public int GetWeight(State state)
		{
			return literals.Sum(x => state[x.Index - 1] ? x.Weight : 0);
		}

		public Clause(int index1, int weight1, bool negative1, int index2, int weight2, bool negative2, int index3, int weight3, bool negative3)
		{
			literals = new Literal[3];
			literals[0] = new Literal(index1, weight1, negative1);
			literals[1] = new Literal(index2, weight2, negative2);
			literals[2] = new Literal(index3, weight3, negative3);
		}

		public bool Satisfied(State state)
		{
			for (int i = 0; i < literals.Length; i++)
			{
				bool rank = state[literals[i].Index - 1];

				if ((rank && !literals[i].Negative) ||
					(!rank && literals[i].Negative))
				{
					return true; // we only care for one satisfied literal
				}
			}

			return false;
		}

		public override string ToString()
		{
			StringBuilder sb = new StringBuilder();
			sb.Append('(');
			sb.Append(literals[0]);
			sb.Append(" + ");
			sb.Append(literals[1]);
			sb.Append(" + ");
			sb.Append(literals[2]);
			sb.Append(')');
			return sb.ToString();
		}
	}
}
