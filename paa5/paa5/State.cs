using System;
using System.Text;

namespace paa5
{
	public class State
	{
		public bool[] Ranks { get; private set; }

		public int Weight { get; set; }

		public bool this[int key]
		{
			get { return Ranks[key]; }
			set { Ranks[key] = value; }
		}

		public State(int varCount, int method)
		{
			Ranks = new bool[varCount];

			switch (method)
			{
				case 0:
					break; // bool default value is already false
				case 1:
					for (int i = 0; i < Ranks.Length; i++)
					{
						Ranks[i] = true;
					}
					break;
				case 2:
					Random rand = new Random();
					for (int i = 0; i < Ranks.Length; i++)
					{
						Ranks[i] = rand.Next(0, 2) == 1; // true if 1, false if 0
					}
					break;
			}
		}

		public int CalculateWeight(int[] weights)
		{
			int sum = 0;
			
			for (int i = 0; i < Ranks.Length; i++)
			{
				if (Ranks[i]) sum += weights[i];
			}

			Weight = sum;
			return sum;
		}

		public State(State otherState)
			: this(otherState.Ranks.Length, 0)
		{
			Array.Copy(otherState.Ranks, Ranks, otherState.Ranks.Length);
			Weight = otherState.Weight;
		}

		public State(State otherState, int index) 
			: this(otherState)
		{
			Ranks[index] = !Ranks[index]; // Invert the bit
		}

		public State(State otherState, int index, int[] weights)
			: this(otherState, index)
		{
			if (Ranks[index])
			{
				Weight += weights[index];
			}
			else
			{
				Weight -= weights[index];
			}
		}

		//public State()

		public override string ToString()
		{
			StringBuilder sb = new StringBuilder();
			sb.Append("{");

			for (int i = 0; i < Ranks.Length - 1; i++)
			{
				sb.Append(Ranks[i] ? 1 : 0);
				sb.Append(", ");
			}
			sb.Append(Ranks[Ranks.Length - 1] ? 1 : 0);
			sb.Append("} weight = ");
			sb.Append(Weight);

			return sb.ToString();
		}
	}
}
