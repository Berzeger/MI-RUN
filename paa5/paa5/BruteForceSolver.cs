namespace paa5
{
	public class BruteForceSolver : ISolver
	{
		private int bestWeight;
		private State bestState;
		private int varCount;
		private int[] weights;
		private Formula formula;

		public BruteForceSolver(int varCount, int [] weights, Formula formula)
		{
			bestWeight = 0;
			bestState = null;
			this.weights = weights;
			this.varCount = varCount;
			this.formula = formula;
		}

		public State Solve()
		{
			SolveRecursive(new State(varCount, 0), 0);

			return bestState;
		}

		public void SolveRecursive(State state, int i)
		{
			if (formula.Satisfied(state) && bestWeight < state.CalculateWeight(weights))
			{
				bestState = state;
				bestWeight = state.Weight;
			}

			if (i < varCount)
			{
				// Without the value
				SolveRecursive(state, i + 1);

				if (bestWeight < state.CalculateWeight(weights) + PotentialWeight(i))
				{
					// With it :)
					State newState = new State(state, i, weights);
					SolveRecursive(newState, i + 1);
				}
			}
		}

		private int PotentialWeight(int i)
		{
			int sum = 0;
			for (int j = i; j < weights.Length; j ++)
			{
				sum += weights[j];
			}

			return sum;
		}
		
		public void UpdateFormula(Formula formula)
		{
			this.formula = formula;
		}
	}
}
